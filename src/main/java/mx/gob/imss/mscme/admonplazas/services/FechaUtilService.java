package mx.gob.imss.mscme.admonplazas.services;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

public interface FechaUtilService {

	Date convertirCadenaFecha(String fecha, String formato);

	String convertirFechaCadena(Date fecha, String formato);

	String convertLocalDateTimeToString(LocalDateTime fecha, String formato);
	
	String convertLocalDateTimeToString(LocalDateTime fecha, String formato, Locale locale);

}