package exception;

public abstract class StockException extends Exception {
    String message = "parent message";
    public String getMessage(){
        return this.message;
    }
}
