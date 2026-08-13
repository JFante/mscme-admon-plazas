package mx.gob.imss.mscme.admonplazas.models.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CMEC_PLAZA_LAYOUT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlazaLayout extends AuditoriaBase implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 862644544405063897L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PLAZA")
    private Long idPlaza;

    // --- Datos de Identificación y Ubicación ---

    @Column(name = "NUM_PLAZA")
    private Integer numPlaza;

    @Column(name = "CVE_OOAD")
    private Long cveOoad;

    @Column(name = "DESC_OOAD", length = 80)
    private String descOoad;

    @Column(name = "CVE_ZONA")
    private Integer cveZona;

    @Column(name = "DESC_ZONA", length = 80)
    private String descZona;

    // Nota: Se recomienda renombrar "CLASIFICACIÓN" a "clasificacion" en el campo
    @Column(name = "CLASIFICACIÓN", length = 20)
    private String clasificacion;

    @Column(name = "CVE_UNIDAD", length = 16)
    private String cveUnidad;

    @Column(name = "DESC_UNIDAD", length = 50)
    private String descUnidad;

    @Column(name = "CVE_DEPARTAMENTO", length = 16)
    private String cveDepartamento;

    @Column(name = "DESC_DEPARTAMENTO", length = 50)
    private String descDepartamento;

    // --- Datos de Puesto y Categoría ---

    @Column(name = "CVE_PUESTO", length = 16)
    private String cvePuesto;

    @Column(name = "DESC_PUESTO", length = 40)
    private String descPuesto;

    @Column(name = "CVE_CATEGORIA", length = 16)
    private String cveCategoria;

    @Column(name = "DESC_CATEGORIA", length = 40)
    private String descCategoria;

    @Column(name = "CVE_AREA_RESPONSABILIDAD", length = 10)
    private String cveAreaResponsabilidad;

    @Column(name = "DESC_AREA_RESPONSABILIDAD", length = 80)
    private String descAreaResponsabilidad;

    // --- Horarios y Tipo de Plaza ---

    @Column(name = "CVE_TURNO")
    private Integer cveTurno;

    @Column(name = "DESC_TURNO", length = 40)
    private String descTurno;

    @Column(name = "CVE_HORARIO", length = 10)
    private String cveHorario;

    @Column(name = "DESC_HORARIO", length = 80)
    private String descHorario;

    @Column(name = "CVE_TIPO_PLAZA", length = 2)
    private String cveTipoPlaza;

    @Column(name = "DESC_TIPO_PLAZA", length = 80)
    private String descTipoPlaza;

    @Column(name = "CVE_MARCA_OCUPACIÓN")
    private Integer cveMarcaOcupacion;

    @Column(name = "DESC_MARCA_OCUPACION", length = 80)
    private String descMarcaOcupacion;

    @Column(name = "DES_REGIMEN", length = 20)
    private String desRegimen;

    @Column(name = "REF_DIRECCION_UNIDAD", length = 150)
    private String refDireccionUnidad;

    @Column(name = "IND_HOSPITAL_NUEVO")
    private Integer indHospitalNuevo;

    // --- Datos Económicos y Beneficios ---

    // NUMBER(10,2) se mapea a BigDecimal para precisión
    @Column(name = "REF_SUELDO_MENSUAL_BRUTO", precision = 10, scale = 2)
    private BigDecimal refSueldoMensualBruto;

    @Column(name = "REF_SUELDO_MENSUAL_NETO", precision = 10, scale = 2)
    private BigDecimal refSueldoMensualNeto;

    @Column(name = "IND_ACCESO_CREDITO")
    private Integer indAccesoCredito;

    @Column(name = "REF_CRED_HIPOTECARIO_IMPORTE", precision = 12, scale = 2)
    private BigDecimal refCredHipotecarioImporte;

    @Column(name = "REF_CRED_AUTOMOTRIZ_IMPORTE", precision = 12, scale = 2)
    private BigDecimal refCredAutomotrizImporte;

    @Column(name = "REF_CRED_HIPOTECARIO_QUINCENAL", precision = 12, scale = 2)
    private BigDecimal refCredHipotecarioQuincenal;

    @Column(name = "REF_CRED_AUTOMOTRIZ_QUINCENAL", precision = 12, scale = 2)
    private BigDecimal refCredAutomotrizQuincenal;

    // NUMBER(11,3)
    @Column(name = "REF_BONO_DIFICIL_COBERTURA", precision = 11, scale = 3)
    private BigDecimal refBonoDificilCobertura;

    // NUMBER(5,2)
    @Column(name = "REF_ALTO_COSTO_VIDA", precision = 5, scale = 2)
    private BigDecimal refAltoCostoVida;
    
    @Column(name = "ID_CONVOCATORIA")
    private Long idConvocatoria;
    
    @Column(name = "ID_ESTATUS_PLAZA")
    private Long idEstatusPlaza;


}