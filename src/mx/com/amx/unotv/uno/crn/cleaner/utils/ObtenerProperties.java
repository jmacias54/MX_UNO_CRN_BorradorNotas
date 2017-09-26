package mx.com.amx.unotv.uno.crn.cleaner.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import mx.com.amx.unotv.uno.crn.cleaner.dto.ParametrosDTO;

import org.apache.log4j.Logger;

public class ObtenerProperties {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());

	public ParametrosDTO obtenerPropiedades(String rutaProperties) {
		ParametrosDTO parametrosDTO = new ParametrosDTO();		 
		try {
			
			/**[INI] Version local**/
			/*
			Properties prop = new Properties();
			prop.load(this.getClass().getResourceAsStream("/ApplicationResources.properties"));
			*/
			/**[FIN] Version local**/
			
			
			/**[INI] Version productiva**/
			/*
			Properties propTemp = new Properties();
			propTemp.load(this.getClass().getResourceAsStream("/ApplicationResources.properties"));
			
			Properties prop = new Properties();
			String rutaProperties = propTemp.getProperty("rutaProperties");
			prop.load(new FileInputStream(new File(rutaProperties)));
			*/
			/**[FIN] Version productiva**/
			
			/**[INI] Version productiva placeholder**/
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File(rutaProperties)));
			/**[FIN] Version productiva placeholder**/
			
			parametrosDTO.setRutaWS(prop.getProperty("rutaWS") == null ? "" : prop.getProperty("rutaWS"));
			
		} catch (Exception e) {
			parametrosDTO = new ParametrosDTO();
			logger.error("No se encontro el Archivo de propiedades: ", e);			
		}
		return parametrosDTO;
    }
}
