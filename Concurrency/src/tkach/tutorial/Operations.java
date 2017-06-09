package tkach.tutorial;

public class Operations {
	public static void main(String[] args) {
		final Account a = new Account(1000, "Account_1");
		final Account b = new Account(2000, "Account_2");
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				transfer(a, b, 500);
				
			}
		}).start();
		
		transfer(b, a, 300);
	}

	protected static void transfer(Account a, Account b, int amount) throws InsufficientFundsException {
		if (a.getBalance() < amount) {
			throw new InsufficientFundsException("Not enought funds to transfer");
		}
		
		synchronized (a) {
			synchronized (b) {
				a.withdraw(amount);
				b.deposit(amount);
			}
		}
		
		System.out.println("The transfer is successful.\n"
				+ "Account status after transfer operation:\n"
				+ a + " = " + a.getBalance() + ", "
				+ b + " = " + b.getBalance() + "\n");
		
	}

}
