package mx.gob.imss.mscme.admonplazas.models.request;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlazaRequest {

    private Long idPlaza;
    private Integer numPlaza;
    private Long cveOoad;
    private String descOoad;
    private Integer cveZona;
    private String descZona;
    private String clasificacion;
    private String cveUnidad;
    private String descUnidad;
    private String cveDepartamento;
    private String descDepartamento;
    private String cvePuesto;
    private String descPuesto;
    private String cveCategoria;
    private String descCategoria;
    private String cveAreaResponsabilidad;
    private String descAreaResponsabilidad;
    private Integer cveTurno;
    private String descTurno;
    private String cveHorario;
    private String descHorario;
    private String cveTipoPlaza;
    private String descTipoPlaza;
    private Integer cveMarcaOcupacion;
    private String descMarcaOcupacion;
    private String descRegimen;
    private String refDireccionUnidad;
    private Integer indHospitalNuevo;
    private BigDecimal refSueldoMensualBruto;
    private BigDecimal refSueldoMensualNeto;
    private Integer indAccesoCredito;
    private BigDecimal refCredHipotecarioImporte;
    private BigDecimal refCredAutomotrizImporte;
    private BigDecimal refCredHipotecarioQuincenal;
    private BigDecimal refCredAutomotrizQuincenal;
    private BigDecimal refBonoDificilCobertura;
    private BigDecimal refAltoCostoVida;
    private Long idConvocatoria;
    private Long idEstatusPlaza;
    private String origenPlaza;
    private String desObservaciones;
}
