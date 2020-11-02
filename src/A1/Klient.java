package A1;

import java.util.concurrent.TimeUnit;

public class Klient extends Thread {
	
	Barman b1;
	
	Klient(Barman b1)
	{
		this.b1=b1;
	}

	@Override
	public void run() {
		for (int i = 0; i < 5; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Wystarczy.");
		b1.cancel();

	}
}
