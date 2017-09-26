package mx.com.amx.unotv.uno.crn.cleaner.proceso;

import mx.com.amx.unotv.uno.crn.cleaner.bo.ProcesoBO;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;


public class Proceso {
	
	private static Logger LOG = Logger.getLogger(Proceso.class);
	
	@Value("${${ambiente}.ruta.properties}")
	private String rutaProperties = "";
	
	private ProcesoBO procesoBO;
	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		LOG.info("***Entrando al contexto****") ;
	}
	
	/**
	 * 
	 * */
	public void depuraNotas() {
		try {
			//Genera Procesos
			procesoBO = new ProcesoBO();
			procesoBO.depuraNotas(rutaProperties);
			
		} catch (Exception e) {
			LOG.info("Exception", e);
		}
	}
	
	/**
	 * Asigna el valor de procesoBO.
	 * @param procesoBO valor de procesoBO.
	 */
	@Autowired
	public void setProcesoBO(ProcesoBO procesoBO) {
		this.procesoBO = procesoBO;
	}
	
}
