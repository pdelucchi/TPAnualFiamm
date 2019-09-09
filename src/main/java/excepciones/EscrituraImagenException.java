package excepciones;

public class EscrituraImagenException extends RuntimeException {
    public EscrituraImagenException(String mensaje) {
        super(mensaje);
    }
}