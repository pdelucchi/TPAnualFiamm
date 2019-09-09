package excepciones;

public class CalificacionInvalidaException extends RuntimeException { 
    public CalificacionInvalidaException(String msg){
        super(msg);
    }
}