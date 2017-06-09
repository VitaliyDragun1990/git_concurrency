package tkach.tutorial;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Operations {
	public static void main(String[] args) throws InsufficientFundsException, InterruptedException {
		final Account a = new Account(1000, "Account_1");
		final Account b = new Account(2000, "Account_2");
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					transfer(a, b, 500);
				} catch (InsufficientFundsException | InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}).start();
		
		transfer(b, a, 300);
	}

	protected static void transfer(Account a, Account b, int amount) throws InsufficientFundsException,
	InterruptedException {
		if (a.getBalance() < amount) {
			throw new InsufficientFundsException("Not enought funds to transfer");
		}
		
		// Transfer with locks
		printLog(" INFO - Trying to lock ", a);
		if (a.getLock().tryLock(500, TimeUnit.MILLISECONDS)) {
			printLog(" INFO - Locked ", a);
			try {
				printLog(" INFO - Trying to lock ", b);
				if (b.getLock().tryLock(500, TimeUnit.MILLISECONDS)) {
					printLog(" INFO - Locked ", b);
					try {
						// Do transfer
						System.out.println("Process transfer operation...");
						a.withdraw(amount);
						b.deposit(amount);
						System.out.println("The transfer is successful.\n"
								+ "Account status after transfer operation:\n"
								+ a + " = " + a.getBalance() + ", "
								+ b + " = " + b.getBalance() + "\n");
					} finally {
						b.getLock().unlock();
					}
				} else {
					printLog(" ERROR - Cannot get a lock for ", b);
				}
			} finally {
				a.getLock().unlock();
				
			}
		} else {
			printLog(" ERROR - Cannot get a lock for ", a);
		}
		
//		printLog(" INFO - Trying to lock ", a);
//		synchronized (a) {
//			
//			printLog(" INFO - Locked ", a);
//			// Simulating deadlock
//			//Thread.sleep(1000);
//			printLog(" INFO - Trying to lock ", b);
//			
//			synchronized (b) {
//				
//				printLog(" INFO - Locked ", b);
//				a.withdraw(amount);
//				b.deposit(amount);
//			}
//		}
//		
//		System.out.println("The transfer is successful.\n"
//				+ "Account status after transfer operation:\n"
//				+ a + " = " + a.getBalance() + ", "
//				+ b + " = " + b.getBalance() + "\n");
		
	}
	
	protected static void printLog(String message, Account acc) {
		System.out.println(LocalDateTime.now() + " ["+Thread.currentThread().getName()+"]" + message + acc);
	}

}
