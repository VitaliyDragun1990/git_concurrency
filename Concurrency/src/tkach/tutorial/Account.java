package tkach.tutorial;

public class Account {
	
	private int balance;
	private String accountName;
	
	public Account(int initialBalance, String accountName) {
		this.balance = initialBalance;
		this.accountName = accountName;
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

}
