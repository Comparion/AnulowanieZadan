package A1;

import java.util.concurrent.TimeUnit;

public class Barman extends Thread{
	volatile static boolean canceled;


	
	public void cancel() {
		System.out.println("Rozumiem, �e Pan rezygnuje z dalszych kolejek.");
		canceled = true;
	}

	@Override
	public void run() {
		System.out.println("Prosz� powiedzie� kiedy wystarczy kolejnych drink�w.");
		try {
			while (!canceled) {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Prosz� bardzo oto Pa�ski drink.");
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (InterruptedException e) {
			System.out.println("INTERRUPT " + System.nanoTime() + " " + isInterrupted());
		}
		System.out.println("Dobrej nocy.");
	}
}
