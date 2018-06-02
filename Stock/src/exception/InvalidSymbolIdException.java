package exception;
public class InvalidSymbolIdException extends Exception{
    public String message = "Invalid Symbol";

    public String getMessage(){
        return this.message;
    }
}