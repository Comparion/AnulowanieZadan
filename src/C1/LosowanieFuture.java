package C1;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


public class LosowanieFuture {
	
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		Future<?> future1 = executor.submit(new MechanizmLosowania("Mechanizm1"));
		Future<?> future2 = executor.submit(new MechanizmLosowania("Mechanizm2"));
		TimeUnit.SECONDS.sleep(3);
		future1.cancel(true);
		TimeUnit.SECONDS.sleep(6);
		future2.cancel(true);
		executor.shutdown();
	}
}

class MechanizmLosowania extends Thread {
	
	private String nazwa;
	
	MechanizmLosowania(String nazwa)
	{
		this.nazwa = nazwa;
	}
	

	@Override
	public void run() {
		Random generator = new Random();
		int wylosowana=-1;
		System.out.println("Start losowania, nazwa: " + nazwa);
		try {
			while (true) {
				wylosowana = generator.nextInt(11);
				System.out.println(nazwa+ " wylosoa³ liczbe: "+ wylosowana);
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (InterruptedException e) {
			System.out.println("przerwano " + nazwa);
		}
	}
}
