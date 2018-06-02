package exception;
public class RepeatedIdException extends StockException{
	String message = "id is repeated" ;

    public String getMessage(){
        return this.message;
    }
}