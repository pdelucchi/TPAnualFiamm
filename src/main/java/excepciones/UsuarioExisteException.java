package excepciones;

public class UsuarioExisteException extends RuntimeException {
    public UsuarioExisteException(String mensaje) {
        super(mensaje);
    }
}
