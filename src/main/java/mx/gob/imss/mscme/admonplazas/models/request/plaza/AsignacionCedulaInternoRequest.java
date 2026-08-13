package mx.gob.imss.mscme.admonplazas.models.request.plaza;

import java.io.Serializable;

import lombok.Data;

@Data
public class AsignacionCedulaInternoRequest implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6532453206272972316L;
	private String estado;
    private String folio;
    private String mesa;
    private String lugar;
    private String fecha;
    private String dependencia;

    private String contratatacion;
    private String tipocontratacion;
    private String marca;
    private String descmarca;
    private String decmarcaocp;

    private String cvecategoria;
    private String nomcategoria;
    private String sueldo;

    private String cvedepartamental;
    private String cvedepartamental2;
    private String tipoplaza;
    private String numplaza;

    private String cveespecialidad;
    private String nomespecialidad;

    private String cveturno;
    private String descturno;

    private String cvehorario;
    private String dechorario;

    private String marcaconcepto;
    private String diasdescanso;
    private String sexo;

    private String uomatricula;
    private String uonombre;
    private String uomarcabaja;
    private String uomotivobaja;
    private String uofechabaja;

    private String tpmatricula;
    private String tpnombre;
    private String tpmarcabaja;
    private String tpmotivobaja;
    private String tpfechabaja;
    private String tpfechareanud;

    private String camatricula;
    private String canombre;
    private String camotivo;
    private String canomprocedencia;
    private String cacvedepartamental;
    private String caprocedencia;
    private String cacvecategoria;
    private String canomcategoria;
    private String casueldo;
    private String canumplaza;
    private String cacvesp;
    private String canomespecialidad;

    private String tpdmatricula;
    private String tpdnombre;
    private String tpdmarcabaja;
    private String tpdmotivo;
    private String tpdfechabaja;
    private String tpdfechareand;

    private String diaslaborados;
    private String rfc;
    private String curp;
    private String afiliacion;
    private String uamt;

    private String diainicio;
    private String mesinicio;
    private String yearinicio;
    private String quincena;
    private String qr;

}

