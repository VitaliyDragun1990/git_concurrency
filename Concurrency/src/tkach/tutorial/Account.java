package tkach.tutorial;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
	
	private int balance;
	private String accountName;
	private Lock lock;
	
	public Account(int initialBalance, String accountName) {
		this.balance = initialBalance;
		this.accountName = accountName;
		lock = new ReentrantLock();
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
