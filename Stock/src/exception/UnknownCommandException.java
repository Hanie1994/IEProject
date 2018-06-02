package exception;
public class UnknownCommandException extends Exception{
    public String message = "Unknown Command";

    public String getMessage(){
        return this.message;
    }
}