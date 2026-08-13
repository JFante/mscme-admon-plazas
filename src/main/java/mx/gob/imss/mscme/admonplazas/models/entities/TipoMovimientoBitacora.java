package mx.gob.imss.mscme.admonplazas.models.entities;

import java.io.Serializable;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CMEC_TIPO_MOVIMIENTO_BITACORA")
public class TipoMovimientoBitacora implements Serializable {
	
    private static final long serialVersionUID = 2320116168776698128L;
    @Id
    @Column(name = "ID_TIPO_MOVIMIENTO_BITACORA", nullable = false)
    private Long idTipoMovimientoBitacora;

    @Column(name = "DES_TIPO_MOVIMIENTO")
    private String desTipoMovimiento;

    @ColumnDefault("1")
    @Column(name = "IND_ACTIVO")
    private Boolean indActivo;


}