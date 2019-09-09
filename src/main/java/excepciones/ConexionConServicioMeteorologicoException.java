package excepciones;

public class ConexionConServicioMeteorologicoException extends RuntimeException {
    public ConexionConServicioMeteorologicoException(String mensaje) {
        super(mensaje);
    }
}
