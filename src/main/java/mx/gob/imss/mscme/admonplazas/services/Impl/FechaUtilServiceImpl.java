/**
 * 
 */
package mx.gob.imss.mscme.admonplazas.services.Impl;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import mx.gob.imss.mscme.admonplazas.services.FechaUtilService;

/**
 * 
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class FechaUtilServiceImpl implements FechaUtilService {

	@Override
	public Date convertirCadenaFecha(String fecha, String formato) {
		try {
			SimpleDateFormat formatoFecha = new SimpleDateFormat(formato);
			return formatoFecha.parse(fecha);
		} catch (Exception e) {
			log.error("error convertirCadenaFecha", e);
			return null;
		}
	}

	@Override
	public String convertirFechaCadena(Date fecha, String formato) {
		String salida = "";
		if (StringUtils.isEmpty(formato)) {
			salida = "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);
			salida = sdf.format(fecha);
		}
		return salida;
	}

	@Override
	public String convertLocalDateTimeToString(LocalDateTime fecha, String formato) {
		if (fecha == null) {
	        return "";
		}
		DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern(formato);
		String salida = "";
		if (StringUtils.isEmpty(formato)) {
			salida = "";
		} else {
			salida = fecha.format(customFormatter);
		}
		return salida;
	}

	@Override
	public String convertLocalDateTimeToString(LocalDateTime fecha, String formato, Locale locale) {
	    if (fecha == null) {
	        return "";
	    }
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato, locale);
	    String resultado = fecha.format(formatter);

	    return resultado;
	}

}
