package C2;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;


public class PisarzICzytelnikRWL {
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
	final private ReadLock readLock;
	final private WriteLock writeLock;
	
	Buffer() {
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		readLock = lock.readLock();
		writeLock = lock.writeLock();
	}
	
	private int zmienna=0;
	void get(String name) throws InterruptedException {
		System.out.format("%12s %26s %3s %20s\n", name, "Przed zablokowaniem",zmienna,System.nanoTime());
		readLock.lock();
		System.out.format("%12s %26s %3s %20s\n",name,"Po zablokowaniu",zmienna,System.nanoTime());

		try {
			TimeUnit.SECONDS.sleep(1);
		} finally {
			readLock.unlock();
			System.out.format("%12s %26s %3s %20s\n",name,"Po odblokowaniu",zmienna,System.nanoTime());
		}
	}

	void set(String name) throws InterruptedException {
		System.out.format("%12s %26s %3s %20s\n",name,"Przed zablokowaniem",zmienna,System.nanoTime());
		writeLock.lock();
		zmienna++;
		System.out.format("%12s %26s %3s %20s\n",name,"Po zablokowaniu",zmienna,System.nanoTime());
		try {
			TimeUnit.SECONDS.sleep(3);
		} finally {
			writeLock.unlock();
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
