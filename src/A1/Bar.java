package A1;


public class Bar {
	public static void main(String[] args) throws InterruptedException {
		
		Barman b1 = new Barman();
		b1.start();
		Klient k1 = new Klient(b1);
		k1.start();

	}
}
