package B1;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Losowanie {
	
	static int SZUKANA = 10;// liczba od 1 do 10
	
	public static void main(String[] args) throws InterruptedException {
		MechanizmLosowania thread = new MechanizmLosowania(SZUKANA);
		thread.start();
	}
}

class MechanizmLosowania extends Thread {
	
	private int szukana;
	
	MechanizmLosowania(int szukana)
	{
		this.szukana = szukana;
	}
	
	public void cancel() {
		System.out.println("Znaleziono szukana liczbe");
		interrupt();
	}

	@Override
	public void run() {
		Random generator = new Random();
		int wylosowana;
		System.out.println("Start losowania, poszkiwana liczba to: " + szukana);
		try {
			while (!isInterrupted()) {
				wylosowana = generator.nextInt(11);
				System.out.println("wylosowana liczba to: "+ wylosowana);
				if(szukana == wylosowana)
					cancel();
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (InterruptedException e) {
			System.out.println("przerwano ");
		}
		System.out.println("Koniec programu");
	}
}

