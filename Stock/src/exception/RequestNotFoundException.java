package exception;

public class RequestNotFoundException extends Exception{
	String message = "request not found";
    public String getMessage(){
        return this.message;
    }
}
