/**
 * 
 */
package mx.gob.imss.mscme.admonplazas.mappers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import mx.gob.imss.mscme.admonplazas.models.entities.AsignacionMedico;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.request.plaza.AsignacionCedulaInternoBienestarRequest;
import mx.gob.imss.mscme.admonplazas.models.response.plaza.MedicoAspiranteDatosGeneralesCedulaProjection;

/**
 * 
 */
@Mapper(componentModel = "spring", imports = { LocalDateTime.class, DateTimeFormatter.class })
public interface AsignacionPlazaCedulaInternoBienestarMapper {

	@Mappings({

			// Header
			@Mapping(target = "estado", expression = "java(buildOoad(asignacionMedico))"),
			@Mapping(target = "folio", source = "datosGenerales.matriculaFolio", qualifiedByName = "folioFormato"),
			
			@Mapping(target = "lugar", constant = "Ciudad de México"),
			@Mapping(target = "fecha", expression = "java(obtenerFechaFormatoLargo())"),

			// DATOS DE LA PLAZA A OCUPAR
			@Mapping(target = "dependencia", source = "asignacionMedico.idPlazaLayout.descUnidad"),
			
			@Mapping(target = "confianza", constant = ""),
			@Mapping(target = "base", constant = "X"),
			@Mapping(target = "def", constant = ""),
			@Mapping(target = "nodef", constant = "X"),
			@Mapping(target = "marca", source = "asignacionMedico.idPlazaLayout.cveMarcaOcupacion", qualifiedByName = "formatMarca"),
			@Mapping(target = "descMarca", source = "asignacionMedico.idPlazaLayout.descMarcaOcupacion"),

			@Mapping(target = "claveCategoria", source = "asignacionMedico.idPlazaLayout.cveCategoria"),
			@Mapping(target = "nombreCategoria", source = "asignacionMedico.idPlazaLayout.descCategoria"),
			@Mapping(target = "sueldoMensual", constant = "$12,844.20"),
			
		
			@Mapping(target = "claveDepartamental", source = "asignacionMedico.idPlazaLayout.cveDepartamento"),
			@Mapping(target = "tipoPlaza", source = "asignacionMedico.idPlazaLayout.descTipoPlaza"),

			@Mapping(target = "numeroPlaza", source = "asignacionMedico.idPlazaLayout.numPlaza"),
			@Mapping(target = "areaResponsabilidad", source = "asignacionMedico.idPlazaLayout.cveAreaResponsabilidad"),
			@Mapping(target = "descAreaResponsabilidad", source = "asignacionMedico.idPlazaLayout.descAreaResponsabilidad"),

			@Mapping(target = "claveTurno", source = "asignacionMedico.idPlazaLayout.cveTurno"),
			@Mapping(target = "descTurno", source = "asignacionMedico.idPlazaLayout.descTurno"),
			@Mapping(target = "claveHorario", source = "asignacionMedico.idPlazaLayout.cveHorario"),
			@Mapping(target = "descHorario", source = "asignacionMedico.idPlazaLayout.descHorario"),
			
			
			
			// sin definir aun
			@Mapping(target = "marca012", constant = ""),
			@Mapping(target = "marca014", constant = ""),
			@Mapping(target = "marca023", constant = ""),
			@Mapping(target = "marca054", constant = ""),
			@Mapping(target = "marca063", constant = ""),
			
			@Mapping(target = "descansos", constant = "Los asigna la Unidad"),
			@Mapping(target = "sexoM", source = "datosGenerales.genero", qualifiedByName = "obtenerSexoMasculino"),
			@Mapping(target = "sexoF", source = "datosGenerales.genero", qualifiedByName = "obtenerSexoFemenino"),
			
			// DATOS DEL ÚLTIMO OCUPANTE
			@Mapping(target = "matriculaDUO", constant = ""), 
			@Mapping(target = "nombreDUO", constant = ""),
			@Mapping(target = "marcaBajaDUO", constant = ""), 
			@Mapping(target = "descMotivoBajaDUO", constant = ""),
			@Mapping(target = "fechaBajaDUO", constant = ""),
			
			// DATOS DEL TITULAR DE LA PLAZA
			@Mapping(target = "matriculaTitular", constant = ""), 
			@Mapping(target = "nombreTitular", constant = ""),
			@Mapping(target = "marcaBajaTitular", constant = ""), 
			@Mapping(target = "descMotivoBajaTitular", constant = ""),
			@Mapping(target = "fechaBajaTitular", constant = ""), 
			@Mapping(target = "fechaProbReanudTitular", constant = ""),
			
			// DATOS DEL CANDIDATO
			@Mapping(target = "matriculaCandidato", source = "datosGenerales.matriculaFolio"),
			@Mapping(target = "nombreCandidato", source = "datosGenerales.nombreCompleto"),
			@Mapping(target = "motivoNominacion", constant = "NI"),
			@Mapping(target = "rfcCandidato", source = "datosGenerales.rfc"),
			
			@Mapping(target = "nombreAdscripcionProcedenciaCandidato", constant = ""),
			@Mapping(target = "claveDepartamentalCandidato", source = "asignacionMedico.idPlazaLayout.cveDepartamento"),
			@Mapping(target = "claveUnicaRegistro", source = "datosGenerales.curp"),
			
			@Mapping(target = "confianzaCandidato", constant = ""),
			@Mapping(target = "baseCandidato", constant = "X"),
			@Mapping(target = "interinoCandidato", constant = ""),
			@Mapping(target = "temporalCandidato", constant = ""),
			@Mapping(target = "sustitutoCandidato", constant = ""),
			@Mapping(target = "nuevoIngresoCandidato", constant = ""),
			@Mapping(target = "numeroAfiliacion", source = "datosGenerales.nss"),
			@Mapping(target = "unidadAtencionMedica", constant = ""),
			
			@Mapping(target = "claveCategoriaCandidato", constant = ""),
			@Mapping(target = "nombreCategoriaCandidato", constant = ""),
			@Mapping(target = "sueldoMensualCandidato", constant = ""),
			
			@Mapping(target = "numeroPlazaCandidato", constant = ""),
			@Mapping(target = "claveEspecialidadCandidato", constant = ""),
			@Mapping(target = "nombreEspecialidadCandidato", constant = ""),

			// DATOS DEL TITULAR DE LA PLAZA QUE DESOCUPA
			@Mapping(target = "matriculaTPD", constant = ""),
			@Mapping(target = "nombreTPD", constant = ""),
			@Mapping(target = "marcaBajaTPD", constant = ""),
			@Mapping(target = "descMotivoBajaTPD", constant = ""),
			@Mapping(target = "fechaBajaTPD", constant = ""), 
			@Mapping(target = "fechaProbReanudTPD", constant = ""),

			
			// DATOS COMPLEMENTARIOS

			// AUTORIZACIÓN DE INICIO DE LABORES
			@Mapping(target = "dia", constant = "01"),
			@Mapping(target = "mes", constant = "Marzo"),
			@Mapping(target = "year", constant = "2026"),
			

	})
	AsignacionCedulaInternoBienestarRequest toRequest(MedicoAspiranteDatosGeneralesCedulaProjection datosGenerales,AsignacionMedico asignacionMedico);

	// =====================================================
	// ================== Helpers ==========================
	// =====================================================
	default String buildOoad(AsignacionMedico asignacionMedico) {
		if (asignacionMedico == null || asignacionMedico.getIdPlazaLayout() == null) {
			return "";
		}

		PlazaLayout p = asignacionMedico.getIdPlazaLayout();

		if (p.getCveOoad() == null || StringUtils.isBlank(p.getDescOoad())) {
			return "";
		}

		return String.format("%02d", p.getCveOoad()) + " - " + p.getDescOoad().trim();
	}

	// Fecha
	default String obtenerFechaAhora() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	/***
	 * 
	 * @return
	 */
	default String obtenerFechaFormatoLargo() {
		LocalDateTime now = LocalDateTime.now();

		Locale localeEs = Locale.forLanguageTag("es-ES");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", localeEs);

		String fecha = now.format(formatter);

		// Capitalizar el mes (ej: "mayo" -> "Mayo")
		return fecha.substring(0, 6) + fecha.substring(6, 7).toUpperCase() + fecha.substring(7);
	}

	// sueldo ver si sera bruto o neto
	default String formatearSueldo(BigDecimal monto) {
		if (monto == null) {
			return "$ 0.00";
		}

		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.of("es", "MX"));
		return formatter.format(monto);
	}

	@Named("generoReporte")
	default String generoReporte(String genero) {
		if (genero == null)
			return null;

		if (genero.equalsIgnoreCase("Hombre")) {
			return "M";
		} else if (genero.equalsIgnoreCase("Mujer")) {
			return "F";
		}
		return null;
	}

	@Named("obtenerDia")
	default String obtenerDia() {
		LocalDateTime now = LocalDateTime.now();
		return String.format("%02d", now.getDayOfMonth()); // 01, 02, 10
	}

	@Named("obtenerMes")
	default String obtenerMes() {
		LocalDateTime now = LocalDateTime.now();
		Locale localeEs = Locale.forLanguageTag("es-ES");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", localeEs);
		String mes = now.format(formatter); // marzo

		return mes.toUpperCase(); // MARZO
	}

	@Named("obtenerAnio")
	default String obtenerAnio() {
		LocalDateTime now = LocalDateTime.now();
		return String.valueOf(now.getYear()); // 2026
	}
	
	@Named("obtenerSexoMasculino")
	default String mapSexoM(String genero) {
		return "Hombre".equalsIgnoreCase(genero) ? "X" : "";
	}

	@Named("obtenerSexoFemenino")
	default String mapSexoF(String genero) {
		return "Mujer".equalsIgnoreCase(genero) ? "X" : "";
	}
	
	@Named("folioFormato")
	default String buildFolio(String matriculaFolio) {
	    String year = String.valueOf(Year.now().getValue());
	    return "CNMBT/COPLAMAR/" + year + "/" + StringUtils.defaultString(matriculaFolio).trim();
	}

	@Named("formatMarca")
	default String formatMarca(String marca) {
	    if (marca == null) return null;
	    marca = marca.trim();
	    return marca.length() == 1 ? "0" + marca : marca;
	}
}
