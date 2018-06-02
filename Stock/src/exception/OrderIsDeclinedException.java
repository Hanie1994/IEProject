package exception;
public class OrderIsDeclinedException extends Exception{
    public String message = "Order Is Declined";

    public String getMessage(){
        return this.message;
    }
}