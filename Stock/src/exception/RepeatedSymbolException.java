package exception;

public class RepeatedSymbolException extends Exception{
	String message = "repeated symbol";
    public String getMessage(){
        return this.message;
    }
}
