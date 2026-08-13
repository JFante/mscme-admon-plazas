package mx.gob.imss.mscme.admonplazas.models.response;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Oculta campos nulos en la respuesta JSON
public class RespuestaGenerica<T> {

    private boolean exito;
    private String mensaje;
    private T respuesta;

    public RespuestaGenerica(boolean exito, String mensaje, T respuesta) {
        this.exito = exito;
        this.mensaje = mensaje;
        this.respuesta = respuesta;
    }

    // Getters y Setters
    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(T respuesta) {
        this.respuesta = respuesta;
    }
}
