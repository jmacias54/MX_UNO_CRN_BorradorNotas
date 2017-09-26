package mx.com.amx.unotv.uno.crn.cleaner.dto;

import java.util.List;

public class ResponseCategoriaDTO {
	
	
	private List<CategoriaDTO> lista;

	/**
	 * Obtiene el valor de lista.
	 * @return valor de lista.
	 */
	public List<CategoriaDTO> getLista() {
		return lista;
	}

	/**
	 * Asigna el valor de lista.
	 * @param lista valor de lista.
	 */
	public void setLista(List<CategoriaDTO> lista) {
		this.lista = lista;
	}
	
	
}
