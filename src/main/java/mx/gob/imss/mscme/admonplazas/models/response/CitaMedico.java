package mx.gob.imss.mscme.admonplazas.models.response;

public interface CitaMedico {

	Long getIdParticipacion();

	String getNombre();

	String getApellidoPaterno();

	String getApellidoMaterno();

	String getMatricula();

	Integer getIdPerfil();

	String getEmail();

	String getCurp();

	Double getPromedio();

	String getFolioMe();

	String getEspecialidad();

	String getTipoContratacion();

	Long getIdTipoConvocatoria();

	Long getFechaConvocatoria();

	String getLugar();

	String getSede();

	Long getIdCitaMedica();

	String getFechaCita();

	String getHora();

	String getTurno();

	Long getMesa();
	
	String getUrl();


}
