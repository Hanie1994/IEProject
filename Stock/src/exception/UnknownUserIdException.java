package exception;
public class UnknownUserIdException extends Exception{
    public String message = "Unknown Use ID";

    public String getMessage(){
        return this.message;
    }
}