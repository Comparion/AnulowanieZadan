package D1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class PigulkaZTrucizna {
	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<Integer> syncQueue = new SynchronousQueue<>();
		Producent p1 = new Producent(syncQueue);
		Konsument k1 = new Konsument(syncQueue, p1);

		p1.start();
		k1.start();
	}
}

class Producent extends Thread {
	volatile static boolean canceled;
	private BlockingQueue<Integer> queue;

	Producent(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}

	public void cancel() {
		System.out.println("Sygnal od kosumenta o przerwaniu produkaccji.");
		canceled = true;
	}

	@Override
	public void run() {
		System.out.println("Zaczynam produkowac");
		int zmienna = 0;
		try {
			while (!canceled) {
				zmienna++;
				queue.put(zmienna);
				System.out.println("Wyprodukowano: " + zmienna);
				TimeUnit.SECONDS.sleep(1);
			}
		} catch (InterruptedException e) {
			System.out.println("przerwano " + System.nanoTime() + " " + isInterrupted());
		}
		System.out.println("Koniec produkacji.");
	}
}

class Konsument extends Thread {

	private BlockingQueue<Integer> queue;
	Producent p1;

	Konsument(BlockingQueue<Integer> queue, Producent p1) {
		this.queue = queue;
		this.p1 = p1;
	}

	@Override
	public void run() {
		int pobrano;
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(1);
				pobrano = queue.take();
				System.out.println("Pobieram: " + pobrano);
				if ( pobrano == 10) {
					p1.cancel();
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
