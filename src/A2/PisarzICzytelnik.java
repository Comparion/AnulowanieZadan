package A2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PisarzICzytelnik {
	final static int SIZE = 2;
	final static int LOOPS = 10;
	static AtomicInteger counter = new AtomicInteger(0);

	public static void main(String[] args) throws InterruptedException {

		ExecutorService e = Executors.newCachedThreadPool();
		for (int i = 0; i < SIZE; i++)
		e.execute(new Pisarz("Pisarz "+i));
		for (int i = 0; i < SIZE*2; i++)
			e.execute(new Czytelnik("Czytelnik "+i));
		e.shutdown();
		e.awaitTermination(1, TimeUnit.DAYS);
	}
}

class Pisarz implements Runnable {
	String name;
	Pisarz(String name)
	{
	this.name=name;	
	}
	@Override
	public void run() {
		int i = 0;
		while (i < PisarzICzytelnik.LOOPS) {
			++i;
			PisarzICzytelnik.counter.incrementAndGet();
			System.out.println(name + " zwiêkszam licznik o jeden" );
		}
		
	}
}

class Czytelnik implements Runnable {
	int licznik;
	String name;
	Czytelnik(String name)
	{
	this.name=name;	
	}
	@Override
	public void run() {
		int i = 0;
		while (i < PisarzICzytelnik.LOOPS) {
			++i;
			licznik = PisarzICzytelnik.counter.get();
			System.out.println(name +" odczytuje licznik "+licznik);
		}
		
	}
}