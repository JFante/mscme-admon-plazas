package mx.gob.imss.mscme.admonplazas.enums;

import lombok.Getter;

@Getter
public enum TipoNotificacionEnum {

	CITA_BECADO("citaBecado", true), CITA_INTERNO_DRAFT("citaInternoDraft", true),
	CITA_INTERNO_MINIDRAFT("citaInternoMinidraft", false), CITA_EXTERNO_DRAFT("citaExternoDraft", true),
	CITA_EXTERNO_MINIDRAFT("citaExternoMinidraft", false);

	private final String tipoCorreo;
	private final Boolean requiereQr;

	TipoNotificacionEnum(String codigo, boolean llevaQr) {
		this.tipoCorreo = codigo;
		this.requiereQr = llevaQr;
	}
}
