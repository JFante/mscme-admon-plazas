package mx.gob.imss.mscme.admonplazas.models.response;

import lombok.Data;

import java.math.BigDecimal;

import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetallePlazaDTO {

    // --- Campos directos/catálogos ---
    private Long idPlaza;
    private String cveOoad;
    private String cvePuesto;
    private String cveUnidad;
    private BigDecimal refAltoCostoVida;
    private String especialidad;
    private String descCategoria;
    private String descRegimen;
    private String descTurno;
    private String descTipoPlaza;
    private String descMarcaOcupacion;
    private String umf;
    private Integer indHospitalNuevo; // Mapeado de IND_NUEVO_HOSPITAL
    private String ubicacion;
    private String descZona;
    private String direccion;
    private BigDecimal refSueldoMensualBruto;
    private BigDecimal refSueldoMensualNeto;
    private String descHorario;
    private String numPlaza;
    private String clasificacion;
    private String descOoad;
    private Integer creditos;
    private BigDecimal refBonoDificilCobertura; // Mapeado de REF_BONO_OFICIAL_COBERTURA

    // --- Campos de Crédito (derivados de las relaciones) ---
    private Integer indAccesoCredito;
    private BigDecimal refCredAutomotrizImporte;
    private BigDecimal descuentoQuincenalCreditoAutomotriz;
    private BigDecimal refCredHipotecarioImporte;
    private BigDecimal descuentoQuincenalCreditoHipotecario;

    private Boolean esFavorita;
    private Integer cveZona;
    private Long idEstatusPlaza;
    private String estatusPlaza;
    private Long idConvocatoria;
    private String origenPlaza;
    private String desObservaciones;
}
