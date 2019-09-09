package excepciones;

public class CategoriaInvalidaException extends RuntimeException { 
    public CategoriaInvalidaException(String msg){
        super(msg);
    }
}
