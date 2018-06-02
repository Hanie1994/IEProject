package exception;
public class MismatchedParametersException extends StockException{
	public String message = "parameter error";

    public String getMessage(){
        return this.message;
    }
}