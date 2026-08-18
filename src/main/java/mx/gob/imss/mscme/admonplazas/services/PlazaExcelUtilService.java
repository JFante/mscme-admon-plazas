package mx.gob.imss.mscme.admonplazas.services;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import mx.gob.imss.mscme.admonplazas.models.entities.PlazaLayout;

public interface PlazaExcelUtilService {

    boolean esFilaVacia(Row fila);

    void asignarValor(PlazaLayout plaza, String encabezado, Cell celda);

    String obtenerValorTexto(Cell celda);

    Integer obtenerValorEntero(Cell celda);

    Long obtenerValorLong(Cell celda);

    BigDecimal obtenerValorDecimal(Cell celda);

}
