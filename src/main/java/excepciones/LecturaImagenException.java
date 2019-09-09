package excepciones;

public class LecturaImagenException extends RuntimeException {
    public LecturaImagenException(String mensaje) {
        super(mensaje);
    }
}