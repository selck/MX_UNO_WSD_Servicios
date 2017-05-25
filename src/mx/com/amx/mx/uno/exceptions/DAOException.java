package mx.com.amx.mx.uno.exceptions;

public class DAOException extends Exception{

	
	private static final long serialVersionUID = 1L;

	public DAOException(String mensaje) {
        super(mensaje);
    }

	public DAOException(Throwable exception) {
        super(exception);
    }

    public DAOException(String mensaje, Throwable exception) {
        super(mensaje, exception);
    }
    
}
