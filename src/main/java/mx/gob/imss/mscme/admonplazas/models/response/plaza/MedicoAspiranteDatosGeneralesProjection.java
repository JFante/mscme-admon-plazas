package mx.gob.imss.mscme.admonplazas.models.response.plaza;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "idUsuario", "refFotografia", "idPerfil", "nombreCompleto", "matriculaFolio", "matricula", "folio",
		"especialidades", "especialidadesClaves", "idEstatusValidacion", "estatusValidacion", "genero", "curp", "rfc","nss",
		"correo", "correoAdicional","idTipoConvocatoria","tipoConvocatoria" })
public interface MedicoAspiranteDatosGeneralesProjection {

	Long getIdUsuario();

	String getRefFotografia();

	String getNombreCompleto();

	Long getIdPerfil();

	String getMatriculaFolio();

	String getMatricula();

	String getFolio();

	String getEspecialidades();

	String getEspecialidadesClaves();

	Long getIdEstatusValidacion();

	String getEstatusValidacion();

	String getGenero();

	String getCurp();

	String getRfc();
	
	String getNss();

	String getCorreo();

	String getCorreoAdicional();

	Long getIdTipoConvocatoria();

	String getTipoConvocatoria();

}
