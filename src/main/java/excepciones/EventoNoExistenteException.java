package excepciones;

public class EventoNoExistenteException extends RuntimeException {
    public EventoNoExistenteException(String msg){
        super(msg);
    }
}
