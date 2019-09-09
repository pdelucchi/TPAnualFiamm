package excepciones;

public class ErrorEnvioMailException extends RuntimeException {
    public ErrorEnvioMailException(String mensaje) {
        super(mensaje);
    }
}
