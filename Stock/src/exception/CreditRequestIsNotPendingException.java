package exception;

public class CreditRequestIsNotPendingException extends Exception{
	String message = "can not modify a not pending credit request";
    public String getMessage(){
        return this.message;
    }
}