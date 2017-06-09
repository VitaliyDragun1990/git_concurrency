package tkach.tutorial;

public class InsufficientFundsException extends RuntimeException {
	
	public InsufficientFundsException(String msg) {
		super(msg);
	}
	
	public InsufficientFundsException() {
		this("Not enought funds on youe account.");
	}

}
