package excepciones;

public class NoExisteImagenCargadaException extends RuntimeException { 
    public NoExisteImagenCargadaException(String msg){
        super(msg);
    }
}