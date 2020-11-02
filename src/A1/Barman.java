package A1;

import java.util.concurrent.TimeUnit;

public class Barman extends Thread{
	volatile static boolean canceled;


	
	public void cancel() {
		System.out.println("Rozumiem, ¿e Pan rezygnuje z dalszych kolejek.");
		canceled = true;
	}

	@Override
	public void run() {
		System.out.println("Proszê powiedzieæ kiedy wystarczy kolejnych drinków.");
		try {
			while (!canceled) {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("Proszê bardzo oto Pañski drink.");
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (InterruptedException e) {
			System.out.println("INTERRUPT " + System.nanoTime() + " " + isInterrupted());
		}
		System.out.println("Dobrej nocy.");
	}
}
