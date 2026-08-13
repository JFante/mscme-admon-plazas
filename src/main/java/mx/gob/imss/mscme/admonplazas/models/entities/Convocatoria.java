package mx.gob.imss.mscme.admonplazas.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CMEC_CONVOCATORIA")
public class Convocatoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONVOCATORIA")
    private Long idConvocatoria;

    @Column(name = "DES_CONVOCATORIA", nullable = false)
    private String descripcion;

    @Column(name = "FEC_INICIO", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "FEC_FIN", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "IND_ACTIVO")
    private Integer indActivo;

    @Column(name = "IND_PERMISO_SUSTITUCION")
    private Integer indPermisoSustitucion;   
    
}
