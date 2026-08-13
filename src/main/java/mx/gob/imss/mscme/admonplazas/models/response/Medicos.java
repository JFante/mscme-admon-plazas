package mx.gob.imss.mscme.admonplazas.models.response;

public interface Medicos {

	Long getIdParticipacion();

	String getNombre();

	String getApellidoPaterno();

	String getApellidoMaterno();

	String getMatricula();

	Integer getIdPerfil();

	String getEmail();

	String getCurp();

	Double getPromedio();

	Integer getIdEstatusVerificacion();

	String getSegmentoMedico();

	String getFolioMe();

	Integer getIdTipoConvocatoria();

	String getEspecialidad();

	String getLugar();

	String getSede();
}
