package exception;

public class OrderIsLimitedException extends Exception{
	public String message = "Order Is Limited";

    public String getMessage(){
        return this.message;
    }
}
