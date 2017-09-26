package mx.com.amx.unotv.uno.crn.cleaner.bo;

import mx.com.amx.unotv.uno.crn.cleaner.dto.CategoriaDTO;
import mx.com.amx.unotv.uno.crn.cleaner.dto.ParametrosDTO;
import mx.com.amx.unotv.uno.crn.cleaner.dto.ResponseCategoriaDTO;
import mx.com.amx.unotv.uno.crn.cleaner.utils.ObtenerProperties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Qualifier("procesoBO")
public class ProcesoBO {
	
	private final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	ParametrosDTO parametrosDTO;
	
	private RestTemplate restTemplate;
	HttpHeaders headers =  new HttpHeaders();
	
	private String metodoGetCategoria = "get/categoria";
	private String metodoDeleteNota = "delete/nota/{categoria}/{rowNumber}";
	
	public ProcesoBO() {
		LOG.debug("Inicializa constructor ProcesoBO");
		try {
			restTemplate = new RestTemplate();
			ClientHttpRequestFactory factory = restTemplate.getRequestFactory();
			
			if ( factory instanceof SimpleClientHttpRequestFactory)
			{
				((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 15 * 1000 );
				((SimpleClientHttpRequestFactory) factory).setReadTimeout( 15 * 1000 );
				LOG.info("Inicializando rest template");
			}
			else if ( factory instanceof HttpComponentsClientHttpRequestFactory)
			{
				((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 15 * 1000);
				((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 15 * 1000);
				LOG.info("Inicializando rest template");
			}
			
			restTemplate.setRequestFactory( factory );
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			
		} catch (Exception e) {
			LOG.error("Error en constructor GeneraComponentesBO:" + e.getMessage());
		}
	}
	
	public void depuraNotas(String rutaProperties){
		//Inicializa parametros
		try {

			ObtenerProperties op = new ObtenerProperties();
			parametrosDTO = op.obtenerPropiedades(rutaProperties);
		} catch (Exception e) {
			LOG.error("Error en generaComponentes.properties:" + e.getMessage());
		}
		
		
		ResponseCategoriaDTO responseCategoriaDTO = new ResponseCategoriaDTO();
		
		HttpEntity<String> entity = new HttpEntity<String>("",headers);
		responseCategoriaDTO = restTemplate.postForObject(parametrosDTO.getRutaWS() + metodoGetCategoria, entity, ResponseCategoriaDTO.class);
		
		if(responseCategoriaDTO.getLista() != null && !responseCategoriaDTO.getLista().isEmpty()){
			for(CategoriaDTO categoriaDTO : responseCategoriaDTO.getLista()){
				if(!categoriaDTO.getFiRegistros().equals("") && !categoriaDTO.getFiRegistros().equals("0")){
					int numRegistrosDepurados = restTemplate.postForObject(parametrosDTO.getRutaWS() + metodoDeleteNota.replace("{categoria}", categoriaDTO.getFcIdCategoria()).replace("{rowNumber}", categoriaDTO.getFiRegistros()), entity, Integer.class);
					
					LOG.debug("seccion:" + categoriaDTO.getFcIdSeccion() + " categoria:" + categoriaDTO.getFcIdCategoria() + " registrosXCategoria:" + categoriaDTO.getFiRegistros() + " depuraron:" + numRegistrosDepurados );
				}
				else{
					LOG.debug("seccion:" + categoriaDTO.getFcIdSeccion() + " categoria:" + categoriaDTO.getFcIdCategoria() + " registrosXCategoria:" + categoriaDTO.getFiRegistros() + " depuraron:" + "0" );
				}
				
			}
		}
	}

}
