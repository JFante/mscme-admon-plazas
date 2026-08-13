package mx.gob.imss.mscme.admonplazas.models.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CMET_SUSTITUCION")
public class Sustitucion extends AuditoriaBase implements Serializable {
	private static final long serialVersionUID = -3785405244882754950L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_SUSTITUCION", nullable = false)
	private Long id;

	@Column(name = "CVE_OOAD")
	private String cveOoad;

	@Column(name = "DES_OOAD")
	private String desOoad;

	@Column(name = "CVE_ZONA")
	private String cveZona;

	@Column(name = "DES_ZONA")
	private String desZona;

	@Column(name = "CVE_ESPECIALIDAD")
	private String cveEspecialidad;

	@Column(name = "DES_ESPECIALIDAD")
	private String desEspecialidad;

}