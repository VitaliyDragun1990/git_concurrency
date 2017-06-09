package tkach.tutorial;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account2 {

	private int balance;
	private String accountName;
	private AtomicInteger failCounter;
	private Lock lock;
	
	public Account2(int initialBalance, String accountName) {
		this.balance = initialBalance;
		this.accountName = accountName;
		lock = new ReentrantLock();
		failCounter = new AtomicInteger(0);
	}
	
	public void incFailedTransferCount() {
		failCounter.incrementAndGet();
	}
	
	public int getFailedTransferCount() {
		return failCounter.get();
	}
	
	public void withdraw(int amount) {
		balance -= amount;
	}
	
	public void deposit(int amount) {
		balance += amount;
	}
	
	public int getBalance() {
		return balance;
	}
	
	public String toString() {
		return accountName;
	}
	
	public Lock getLock() {
		return lock;
	}

}
