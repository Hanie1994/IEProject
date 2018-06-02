package exception;
public class OrderIsQueuedException extends Exception{
    public String message = "Order Is Queued";

    public String getMessage(){
        return this.message;
    }
}
