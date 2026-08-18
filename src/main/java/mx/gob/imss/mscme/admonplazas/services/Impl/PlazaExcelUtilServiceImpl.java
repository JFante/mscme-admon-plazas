package mx.gob.imss.mscme.admonplazas.services.Impl;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.services.PlazaExcelUtilService;
import mx.gob.imss.mscme.admonplazas.utils.ColumnasLayoutPlaza;

@Service
public class PlazaExcelUtilServiceImpl implements PlazaExcelUtilService {

    @Override
    public boolean esFilaVacia(Row fila) {
        for (Cell celda : fila) {
            if (StringUtils.hasText(obtenerValorTexto(celda))) {
                return false;
            }
        }
        return true;
    }

    @Override
	public void asignarValor(PlazaLayout plaza, String encabezado, Cell celda) {
		switch (encabezado) {
		case ColumnasLayoutPlaza.NUM_PLAZA -> plaza.setNumPlaza(obtenerValorEntero(celda));
		case ColumnasLayoutPlaza.CVE_OOAD -> plaza.setCveOoad(obtenerValorLong(celda));
		case ColumnasLayoutPlaza.DESC_OOAD -> plaza.setDescOoad(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_ZONA -> plaza.setCveZona(obtenerValorEntero(celda));
		case ColumnasLayoutPlaza.DESC_ZONA -> plaza.setDescZona(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CLASIFICACION, ColumnasLayoutPlaza.CLASIFICACION_SIN_ACENTO ->plaza.setClasificacion(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_UNIDAD -> plaza.setCveUnidad(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.DESC_UNIDAD -> plaza.setDescUnidad(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_DEPARTAMENTO -> plaza.setCveDepartamento(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.DESC_DEPARTAMENTO -> plaza.setDescDepartamento(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_PUESTO -> plaza.setCvePuesto(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.DESC_PUESTO -> plaza.setDescPuesto(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_CATEGORIA -> plaza.setCveCategoria(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.DESC_CATEGORIA -> plaza.setDescCategoria(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_AREA_RESPONSABILIDAD -> plaza.setCveAreaResponsabilidad(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.DESC_AREA_RESPONSABILIDAD ->plaza.setDescAreaResponsabilidad(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_TURNO -> plaza.setCveTurno(obtenerValorEntero(celda));
		case ColumnasLayoutPlaza.DESC_TURNO -> plaza.setDescTurno(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_HORARIO -> plaza.setCveHorario(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.DESC_HORARIO -> plaza.setDescHorario(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_TIPO_PLAZA -> plaza.setCveTipoPlaza(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.DESC_TIPO_PLAZA -> plaza.setDescTipoPlaza(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.CVE_MARCA_OCUPACION, ColumnasLayoutPlaza.CVE_MARCA_OCUPACION_SIN_ACENTO ->plaza.setCveMarcaOcupacion(obtenerValorEntero(celda));
		case ColumnasLayoutPlaza.DESC_MARCA_OCUPACION -> plaza.setDescMarcaOcupacion(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.DES_REGIMEN -> plaza.setDesRegimen(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.REF_DIRECCION_UNIDAD -> plaza.setRefDireccionUnidad(obtenerValorTexto(celda));
		case ColumnasLayoutPlaza.IND_HOSPITAL_NUEVO -> plaza.setIndHospitalNuevo(obtenerValorEntero(celda));
		case ColumnasLayoutPlaza.REF_SUELDO_MENSUAL_BRUTO -> plaza.setRefSueldoMensualBruto(obtenerValorDecimal(celda));
		case ColumnasLayoutPlaza.REF_SUELDO_MENSUAL_NETO -> plaza.setRefSueldoMensualNeto(obtenerValorDecimal(celda));
		case ColumnasLayoutPlaza.IND_ACCESO_CREDITO -> plaza.setIndAccesoCredito(obtenerValorEntero(celda));
		case ColumnasLayoutPlaza.REF_CRED_HIPOTECARIO_IMPORTE ->
			plaza.setRefCredHipotecarioImporte(obtenerValorDecimal(celda));
		case ColumnasLayoutPlaza.REF_CRED_AUTOMOTRIZ_IMPORTE ->
			plaza.setRefCredAutomotrizImporte(obtenerValorDecimal(celda));
		case ColumnasLayoutPlaza.REF_CRED_HIPOTECARIO_QUINCENAL ->
			plaza.setRefCredHipotecarioQuincenal(obtenerValorDecimal(celda));
		case ColumnasLayoutPlaza.REF_CRED_AUTOMOTRIZ_QUINCENAL ->
			plaza.setRefCredAutomotrizQuincenal(obtenerValorDecimal(celda));
		case ColumnasLayoutPlaza.REF_BONO_DIFICIL_COBERTURA ->
			plaza.setRefBonoDificilCobertura(obtenerValorDecimal(celda));
		case ColumnasLayoutPlaza.REF_ALTO_COSTO_VIDA -> plaza.setRefAltoCostoVida(obtenerValorDecimal(celda));
		default -> {
			// columna no reconocida, se ignora
		}
		}
	}

    @Override
    public String obtenerValorTexto(Cell celda) {
        if (celda == null) {
            return null;
        }
        return switch (celda.getCellType()) {
            case STRING -> celda.getStringCellValue();
            case NUMERIC -> {
                double valor = celda.getNumericCellValue();
                yield valor == Math.floor(valor) ? String.valueOf((long) valor) : String.valueOf(valor);
            }
            case BOOLEAN -> String.valueOf(celda.getBooleanCellValue());
            case FORMULA -> celda.getCellFormula();
            default -> null;
        };
    }

    @Override
    public Integer obtenerValorEntero(Cell celda) {
        String valor = obtenerValorTexto(celda);
        if (!StringUtils.hasText(valor)) {
            return null;
        }
        try {
            return (int) Double.parseDouble(valor.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public Long obtenerValorLong(Cell celda) {
        String valor = obtenerValorTexto(celda);
        if (!StringUtils.hasText(valor)) {
            return null;
        }
        try {
            return (long) Double.parseDouble(valor.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    @Override
    public BigDecimal obtenerValorDecimal(Cell celda) {
        String valor = obtenerValorTexto(celda);
        if (!StringUtils.hasText(valor)) {
            return null;
        }
        try {
            return new BigDecimal(valor.trim());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

}
