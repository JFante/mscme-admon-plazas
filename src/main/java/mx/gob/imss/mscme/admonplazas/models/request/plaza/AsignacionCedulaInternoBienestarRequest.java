package mx.gob.imss.mscme.admonplazas.models.request.plaza;

import java.io.Serializable;

import lombok.Data;
@Data
public class AsignacionCedulaInternoBienestarRequest implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7084402989327316351L;
	/* ================== IMÁGENES ================== */
    private String imgImss;
    private String imgSindicato;

    /* ================== GENERALES ================== */
    private String estado;
    private String folio;
    private String lugar;
    private String fecha;
    private String dependencia;

    /* ================== PLAZA ================== */
    private String confianza;
    private String base;
    private String def;
    private String nodef;
    private String marca;
    private String descMarca;
    private String claveCategoria;
    private String nombreCategoria;
    private String sueldoMensual;

    /* ================== ADMINISTRATIVOS ================== */
    private String claveDepartamental;
    private String tipoPlaza;
    private String numeroPlaza;
    private String areaResponsabilidad;
    private String descAreaResponsabilidad;
    private String claveTurno;
    private String descTurno;
    private String claveHorario;
    private String descHorario;

    /* ================== CONCEPTOS ================== */
    private String marca012;
    private String marca014;
    private String marca023;
    private String marca054;
    private String marca063;
    private String descansos;
    private String sexoM;
    private String sexoF;

    /* ================== ÚLTIMO OCUPANTE (DUO) ================== */
    private String matriculaDUO;
    private String nombreDUO;
    private String marcaBajaDUO;
    private String descMotivoBajaDUO;
    private String fechaBajaDUO;

    /* ================== TITULAR PLAZA ================== */
    private String matriculaTitular;
    private String nombreTitular;
    private String marcaBajaTitular;
    private String descMotivoBajaTitular;
    private String fechaBajaTitular;
    private String fechaProbReanudTitular;

    /* ================== CANDIDATO ================== */
    private String matriculaCandidato;
    private String nombreCandidato;
    private String motivoNominacion;
    private String rfcCandidato;
    private String claveUnicaRegistro;
    private String numeroAfiliacion;
    private String unidadAtencionMedica;
    private String nombreAdscripcionProcedenciaCandidato;
    private String claveDepartamentalCandidato;

    private String confianzaCandidato;
    private String baseCandidato;
    private String interinoCandidato;
    private String temporalCandidato;
    private String sustitutoCandidato;
    private String nuevoIngresoCandidato;

    private String claveCategoriaCandidato;
    private String nombreCategoriaCandidato;
    private String sueldoMensualCandidato;

    /* ================== ESPECIALIDAD ================== */
    private String numeroPlazaCandidato;
    private String claveEspecialidadCandidato;
    private String nombreEspecialidadCandidato;

    /* ================== TITULAR DESOCUPA (TPD) ================== */
    private String matriculaTPD;
    private String nombreTPD;
    private String marcaBajaTPD;
    private String descMotivoBajaTPD;
    private String fechaBajaTPD;
    private String fechaProbReanudTPD;

    /* ================== FECHAS AUTORIZACIÓN ================== */
    private String dia;
    private String mes;
    private String year;


}