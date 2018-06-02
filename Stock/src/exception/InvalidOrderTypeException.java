package exception;
public class InvalidOrderTypeException extends Exception{
    public String message = "Invalid Order Type";

    public String getMessage(){
        return this.message;
    }
}