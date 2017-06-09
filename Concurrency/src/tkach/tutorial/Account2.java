package tkach.tutorial;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account2 {

	private int balance;
	private String accountName;
	private int failCounter;
	private Lock lock;
	
	public Account2(int initialBalance, String accountName) {
		this.balance = initialBalance;
		this.accountName = accountName;
		lock = new ReentrantLock();
	}
	
	public void incFailedTransferCount() {
		failCounter++;
	}
	
	public int getFailedTransferCount() {
		return failCounter;
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
