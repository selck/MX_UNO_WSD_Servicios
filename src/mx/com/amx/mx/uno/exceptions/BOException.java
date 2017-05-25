package mx.com.amx.mx.uno.exceptions;

public class BOException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public BOException(String mensaje) {
		super(mensaje);
	}
	public BOException(Throwable exception) {
        super(exception);
    }

    public BOException(String mensaje, Throwable exception) {
        super(mensaje, exception);
    }
}
