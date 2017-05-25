package mx.com.amx.mx.uno.feed.ws.dto;


public class RequestNotaDTO {
	
	private String idMagazine;
	private String idTipoSeccion;
	private String idSeccion;
	private String idCategoria;
	private String nomBandera;
	
	private int numRegistros;
	private int numRegistrosSubstring;
	
	private int numRegistrosFetch;

	/**
	 * Obtiene el valor de idMagazine.
	 * @return valor de idMagazine.
	 */
	public String getIdMagazine() {
		return idMagazine;
	}

	/**
	 * Asigna el valor de idMagazine.
	 * @param idMagazine valor de idMagazine.
	 */
	public void setIdMagazine(String idMagazine) {
		this.idMagazine = idMagazine;
	}

	/**
	 * Obtiene el valor de idTipoSeccion.
	 * @return valor de idTipoSeccion.
	 */
	public String getIdTipoSeccion() {
		return idTipoSeccion;
	}

	/**
	 * Asigna el valor de idTipoSeccion.
	 * @param idTipoSeccion valor de idTipoSeccion.
	 */
	public void setIdTipoSeccion(String idTipoSeccion) {
		this.idTipoSeccion = idTipoSeccion;
	}

	/**
	 * Obtiene el valor de idSeccion.
	 * @return valor de idSeccion.
	 */
	public String getIdSeccion() {
		return idSeccion;
	}

	/**
	 * Asigna el valor de idSeccion.
	 * @param idSeccion valor de idSeccion.
	 */
	public void setIdSeccion(String idSeccion) {
		this.idSeccion = idSeccion;
	}

	/**
	 * Obtiene el valor de idCategoria.
	 * @return valor de idCategoria.
	 */
	public String getIdCategoria() {
		return idCategoria;
	}

	/**
	 * Asigna el valor de idCategoria.
	 * @param idCategoria valor de idCategoria.
	 */
	public void setIdCategoria(String idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * Obtiene el valor de nomBandera.
	 * @return valor de nomBandera.
	 */
	public String getNomBandera() {
		return nomBandera;
	}

	/**
	 * Asigna el valor de nomBandera.
	 * @param nomBandera valor de nomBandera.
	 */
	public void setNomBandera(String nomBandera) {
		this.nomBandera = nomBandera;
	}

	/**
	 * Obtiene el valor de numRegistros.
	 * @return valor de numRegistros.
	 */
	public int getNumRegistros() {
		return numRegistros;
	}

	/**
	 * Asigna el valor de numRegistros.
	 * @param numRegistros valor de numRegistros.
	 */
	public void setNumRegistros(int numRegistros) {
		this.numRegistros = numRegistros;
	}

	/**
	 * Obtiene el valor de numRegistrosSubstring.
	 * @return valor de numRegistrosSubstring.
	 */
	public int getNumRegistrosSubstring() {
		return numRegistrosSubstring;
	}

	/**
	 * Asigna el valor de numRegistrosSubstring.
	 * @param numRegistrosSubstring valor de numRegistrosSubstring.
	 */
	public void setNumRegistrosSubstring(int numRegistrosSubstring) {
		this.numRegistrosSubstring = numRegistrosSubstring;
	}

	/**
	 * Obtiene el valor de numRegistrosFetch.
	 * @return valor de numRegistrosFetch.
	 */
	public int getNumRegistrosFetch() {
		return numRegistrosFetch;
	}

	/**
	 * Asigna el valor de numRegistrosFetch.
	 * @param numRegistrosFetch valor de numRegistrosFetch.
	 */
	public void setNumRegistrosFetch(int numRegistrosFetch) {
		this.numRegistrosFetch = numRegistrosFetch;
	}
	
}
