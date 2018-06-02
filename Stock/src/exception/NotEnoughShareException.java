package exception;
public class NotEnoughShareException extends Exception{
    public String message = "Not Enough Share";

    public String getMessage(){
        return this.message;
    }
}