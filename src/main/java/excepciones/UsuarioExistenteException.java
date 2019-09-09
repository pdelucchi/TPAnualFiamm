package excepciones;

public class UsuarioExistenteException extends RuntimeException { 
    public UsuarioExistenteException(String msg){
        super(msg);
    }
}