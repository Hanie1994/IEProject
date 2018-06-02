package exception;

public class SymbolNotVerifiedException extends Exception{
	String message = "symbol not verified";
    public String getMessage(){
        return this.message;
    }
}
