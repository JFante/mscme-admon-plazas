package mx.gob.imss.mscme.admonplazas.models.response.plaza;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "idUsuario", "idPerfil", "indPerfilInterno", "nombreCompleto", "matriculaFolio", "matricula", "folio",
		"especialidades", "especialidadesClaves", "idEstatusValidacion", "estatusValidacion", "genero", "curp", "rfc","nss",
		"correo","tipoUsuario" ,"numMesa"})
public interface MedicoAspiranteDatosGeneralesCedulaProjection {

	Long getIdUsuario();

	String getNombreCompleto();

	Long getIdPerfil();

	Long getIndPerfilInterno();

	String getMatriculaFolio();

	String getMatricula();

	String getFolio();

	String getGenero();

	String getCurp();

	String getRfc();
	
	String getNss();

	String getCorreo();
	
	String getTipoUsuario();

	Long getNumMesa();

}
