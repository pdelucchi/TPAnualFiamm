package excepciones;

public class EventoExistenteException extends RuntimeException {
    public EventoExistenteException(String msg){
        super(msg);
    }
}
