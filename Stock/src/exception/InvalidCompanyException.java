package exception;

public class InvalidCompanyException extends Exception{
	String message = "invalid company";
    public String getMessage(){
        return this.message;
    }
}
