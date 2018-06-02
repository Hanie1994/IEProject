package exception;
public class NotEnoughCreditException extends Exception{
    public String message = "Not Enough Credit";

    public String getMessage(){
        return this.message;
    }
}