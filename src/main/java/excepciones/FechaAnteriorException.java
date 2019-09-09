package excepciones;

public class FechaAnteriorException extends RuntimeException {
    public FechaAnteriorException(String mensaje) {
        super(mensaje);
    }
}
