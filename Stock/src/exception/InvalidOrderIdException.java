package exception;
public class InvalidOrderIdException extends Exception{
    public String message = "Invalid Order oid";

    public String getMessage(){
        return this.message;
    }
}