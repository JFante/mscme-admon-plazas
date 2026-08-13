/**
 * 
 */
package mx.gob.imss.mscme.admonplazas.mappers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import mx.gob.imss.mscme.admonplazas.models.entities.AsignacionMedico;
import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;
import mx.gob.imss.mscme.admonplazas.models.request.plaza.AsignacionCedulaInternoRequest;
import mx.gob.imss.mscme.admonplazas.models.response.plaza.MedicoAspiranteDatosGeneralesCedulaProjection;

/**
 * 
 */
@Mapper(componentModel = "spring", imports = { LocalDateTime.class, DateTimeFormatter.class })
public interface AsignacionPlazaCedulaInternoMapper {

	@Mappings({

			// Header
			@Mapping(target = "estado", expression = "java(buildOoad(asignacionMedico))"),
			@Mapping(target = "folio", source = "datosGenerales.matriculaFolio", qualifiedByName = "folioFormato"),
			@Mapping(target = "mesa", source = "datosGenerales.numMesa", defaultValue = ""),
			@Mapping(target = "lugar", constant = "Ciudad de México"),
			@Mapping(target = "fecha", expression = "java(obtenerFechaFormatoLargo())"),

			// DATOS DE LA PLAZA A OCUPAR
			@Mapping(target = "dependencia", source = "asignacionMedico.idPlazaLayout.descUnidad"),
			@Mapping(target = "contratatacion", constant = "BASE"),
			@Mapping(target = "tipocontratacion", constant = "NODEF"),


			@Mapping(target = "marca", source = "asignacionMedico.idPlazaLayout.cveMarcaOcupacion", qualifiedByName = "formatMarca"),
			@Mapping(target = "descmarca", source = "asignacionMedico.idPlazaLayout.descMarcaOcupacion"),

			@Mapping(target = "decmarcaocp", source = "asignacionMedico.idPlazaLayout.descMarcaOcupacion"),

			@Mapping(target = "cvecategoria", source = "asignacionMedico.idPlazaLayout.cveCategoria"),
			@Mapping(target = "nomcategoria", source = "asignacionMedico.idPlazaLayout.descCategoria"),
			//@Mapping(target = "sueldo", expression = "java(formatearSueldo(asignacionMedico.getIdPlazaLayout().getRefSueldoMensualBruto()))"),
			@Mapping(target = "sueldo", constant = "$12,844.20"),

			@Mapping(target = "cvedepartamental", source = "asignacionMedico.idPlazaLayout.cveDepartamento"),
			@Mapping(target = "cvedepartamental2", constant = ""),
			@Mapping(target = "tipoplaza", source = "asignacionMedico.idPlazaLayout.descTipoPlaza"),

			@Mapping(target = "numplaza", source = "asignacionMedico.idPlazaLayout.numPlaza"),
			@Mapping(target = "cveespecialidad", source = "asignacionMedico.idPlazaLayout.cveAreaResponsabilidad"),
			@Mapping(target = "nomespecialidad", source = "asignacionMedico.idPlazaLayout.descAreaResponsabilidad"),

			@Mapping(target = "cveturno", source = "asignacionMedico.idPlazaLayout.cveTurno"),
			@Mapping(target = "descturno", source = "asignacionMedico.idPlazaLayout.descTurno"),
			@Mapping(target = "cvehorario", source = "asignacionMedico.idPlazaLayout.cveHorario"),
			@Mapping(target = "dechorario", source = "asignacionMedico.idPlazaLayout.descHorario"),

			// sin definir aun
			@Mapping(target = "marcaconcepto", constant = ""),
			@Mapping(target = "diasdescanso", constant = "Los asigna la Unidad"),
			@Mapping(target = "sexo", source = "datosGenerales.genero", qualifiedByName = "generoReporte"),

			// DATOS DEL ÚLTIMO OCUPANTE
			@Mapping(target = "uomatricula", constant = ""), 
			@Mapping(target = "uonombre", constant = ""),
			@Mapping(target = "uomarcabaja", constant = ""), 
			@Mapping(target = "uomotivobaja", constant = ""),
			@Mapping(target = "uofechabaja", constant = ""),

			// DATOS DEL TITULAR DE LA PLAZA
			@Mapping(target = "tpmatricula", constant = ""), 
			@Mapping(target = "tpnombre", constant = ""),
			@Mapping(target = "tpmarcabaja", constant = ""), 
			@Mapping(target = "tpmotivobaja", constant = ""),
			@Mapping(target = "tpfechabaja", constant = ""), 
			@Mapping(target = "tpfechareanud", constant = ""),
			//

			// DATOS DEL CANDIDATO
			@Mapping(target = "camatricula", source = "datosGenerales.matriculaFolio"),
			@Mapping(target = "canombre", source = "datosGenerales.nombreCompleto"),
			@Mapping(target = "camotivo", constant = "NI"),
			@Mapping(target = "canomprocedencia", constant = ""),
			@Mapping(target = "cacvedepartamental", constant = ""),
			@Mapping(target = "caprocedencia", constant = "BASE"),

			@Mapping(target = "cacvecategoria", constant = ""),
			@Mapping(target = "canomcategoria", constant = ""),
			@Mapping(target = "casueldo", constant = ""),

			@Mapping(target = "canumplaza", constant = ""),
			@Mapping(target = "cacvesp", constant = ""),
			@Mapping(target = "canomespecialidad", constant = ""),

			// DATOS DEL TITULAR DE LA PLAZA QUE DESOCUPA
			@Mapping(target = "tpdmatricula", constant = ""),
			@Mapping(target = "tpdnombre", constant = ""),
			@Mapping(target = "tpdmarcabaja", constant = ""),
			@Mapping(target = "tpdmotivo", constant = ""),
			@Mapping(target = "tpdfechabaja", constant = ""), 
			@Mapping(target = "tpdfechareand", constant = ""),

			// DATOS COMPLEMENTARIOS
			@Mapping(target = "diaslaborados", constant = ""),
			@Mapping(target = "rfc", source = "datosGenerales.rfc"),
			@Mapping(target = "curp", source = "datosGenerales.curp"), 
			@Mapping(target = "afiliacion", source = "datosGenerales.nss"),
			@Mapping(target = "uamt", constant = ""),

			// AUTORIZACIÓN DE INICIO DE LABORES
			@Mapping(target = "diainicio", constant = "01"),
			@Mapping(target = "mesinicio", constant = "Marzo"),
			@Mapping(target = "yearinicio", constant = "2026"),
			@Mapping(target = "quincena", constant = "2026006"),
			@Mapping(target = "qr", expression = "java(generarTextoQr(datosGenerales, asignacionMedico))")
	})
	AsignacionCedulaInternoRequest toRequest(MedicoAspiranteDatosGeneralesCedulaProjection datosGenerales,
			AsignacionMedico asignacionMedico);

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

	/***
	 * 
	 * @param datosGenerales
	 * @param asignacionMedico
	 * @return
	 */
	@Named("generarTextoQr")
	default String generarTextoQr(MedicoAspiranteDatosGeneralesCedulaProjection datosGenerales,
			AsignacionMedico asignacionMedico) {

		if (datosGenerales == null && asignacionMedico == null) {
			return "";
		}

		String nombreCompleto = datosGenerales != null ? StringUtils.defaultString(datosGenerales.getNombreCompleto()): "";
		String matricula = datosGenerales != null ? StringUtils.defaultString(datosGenerales.getMatriculaFolio()) : "";

		String especialidad = "";
		if (asignacionMedico != null && asignacionMedico.getIdPlazaLayout() != null) {
			especialidad = StringUtils.defaultString(asignacionMedico.getIdPlazaLayout().getDescAreaResponsabilidad());
		}

		return String.join("|", nombreCompleto, matricula, especialidad);
	}
	
	@Named("folioFormato")
	default String buildFolio(String matriculaFolio) {
	    return "CNMBT/" + StringUtils.defaultString(matriculaFolio).trim();
	}

	@Named("formatMarca")
	default String formatMarca(String marca) {
	    if (marca == null) return null;
	    marca = marca.trim();
	    return marca.length() == 1 ? "0" + marca : marca;
	}
}
