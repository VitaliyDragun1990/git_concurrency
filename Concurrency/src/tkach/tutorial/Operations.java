package tkach.tutorial;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class Operations {
	
	final static long waitTimeForLock = 500; // 500 millis
	
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
		boolean isSuccessTransfer = false;
		
		while (!isSuccessTransfer) {
			// Wait random amount of time between attempt to obtain a lock
			Thread.sleep((long) (Math.random()*500));
			// Transfer with locks
			printLog(" INFO - Trying to lock ", a);
			if (a.getLock().tryLock(waitTimeForLock, TimeUnit.MILLISECONDS)) {
				printLog(" INFO - Locked ", a);
				try {
					printLog(" INFO - Trying to lock ", b);
					if (b.getLock().tryLock(waitTimeForLock, TimeUnit.MILLISECONDS)) {
						printLog(" INFO - Locked ", b);
						try {
							// Do transfer
							System.out.println("Process transfer operation...");
							a.withdraw(amount);
							b.deposit(amount);
							System.out.println(
									"The transfer is successful.\n" + "Account status after transfer operation:\n" + a
											+ " = " + a.getBalance() + ", " + b + " = " + b.getBalance() + "\n");
							isSuccessTransfer = true;
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
		}
		
	}
	
	protected static void printLog(String message, Account acc) {
		System.out.println(LocalDateTime.now() + " ["+Thread.currentThread().getName()+"]" + message + acc);
	}

}
