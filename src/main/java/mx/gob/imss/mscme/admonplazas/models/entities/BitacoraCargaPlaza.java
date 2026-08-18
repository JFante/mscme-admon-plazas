package mx.gob.imss.mscme.admonplazas.models.entities;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CMET_BITACORA_CARGA_PLAZA")
public class BitacoraCargaPlaza extends AuditoriaBase implements Serializable {
    @Serial
    private static final long serialVersionUID = 196994887755361748L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BITACORA_CARGA_PLAZA", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CONTROL_CARGA_PLAZA", nullable = false)
    private ControlCargaPlaza idControlCargaPlaza;

    @Column(name = "NUM_FILA_ARCHIVO")
    private Long numFilaArchivo;

    @Column(name = "NUM_PLAZA")
    private Long numPlaza;

    @Column(name = "CVE_OOAD", length = 20)
    private String cveOoad;

    @Column(name = "IND_VALIDO")
    private Boolean indValido;

    @Column(name = "DES_ERROR", length = 4000)
    private String desError;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PLAZA_LAYOUT")
    private PlazaLayout idPlazaLayout;

}