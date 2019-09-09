package excepciones;

public class UsuarioNoExisteException extends RuntimeException {
    public UsuarioNoExisteException(String mensaje) {
        super(mensaje);
    }
}
