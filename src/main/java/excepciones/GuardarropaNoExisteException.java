package excepciones;

public class GuardarropaNoExisteException extends RuntimeException {
	public GuardarropaNoExisteException(String mensaje) {
		super(mensaje);
	}
}

