package B2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class PisarzICzytelnikLock {
	public static void main(String[] args) throws InterruptedException {
		final Buffer b = new Buffer();
		for (int i = 0; i < 2; i++) {
			TimeUnit.MILLISECONDS.sleep(500);
			new Czytelnik(b,"Czytelnik "+i).start();
		}
		TimeUnit.MILLISECONDS.sleep(500);
		new Pisarz(b,"Pisarz 1").start();
	}
}

class Buffer {
	private Lock lock = new ReentrantLock();
	private int zmienna=0;
	void get(String name) throws InterruptedException {
		System.out.format("%12s %26s %3s %20s\n", name, "Przed zablokowaniem",zmienna,System.nanoTime());
		lock.lock();
		System.out.format("%12s %26s %3s %20s\n",name,"Po zablokowaniu",zmienna,System.nanoTime());

		try {
			TimeUnit.SECONDS.sleep(1);
		} finally {
			lock.unlock();
			System.out.format("%12s %26s %3s %20s\n",name,"Po odblokowaniu",zmienna,System.nanoTime());
		}
	}

	void set(String name) throws InterruptedException {
		System.out.format("%12s %26s %3s %20s\n",name,"Przed zablokowaniem",zmienna,System.nanoTime());
		lock.lock();
		zmienna++;
		System.out.format("%12s %26s %3s %20s\n",name,"Po zablokowaniu",zmienna,System.nanoTime());
		try {
			TimeUnit.SECONDS.sleep(3);
		} finally {
			lock.unlock();
			System.out.format("%12s %26s %3s %20s\n",name,"Po odblokowaniu",zmienna,System.nanoTime());
		}
	}
}

class Czytelnik extends Thread {
	private Buffer b;
	String name;
	Czytelnik(Buffer b,String name) {
		this.b = b;
		this.name=name;
	}

	public void run() {
		int i = 0;
		try {
			while (i < 10)
			{
				b.get(name);
				i++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Pisarz extends Thread {
	private Buffer b;
	String name;
	Pisarz(Buffer b,String name) {
		this.b = b;
		this.name=name;
	}

	public void run() {
		int i = 0;
		try {
			while (i < 10)
			{
				b.set(name);
				i++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}


