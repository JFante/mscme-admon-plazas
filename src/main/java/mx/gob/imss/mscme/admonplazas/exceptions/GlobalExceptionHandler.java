package mx.gob.imss.mscme.admonplazas.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;
import mx.gob.imss.mscme.admonplazas.models.response.RespuestaGenerica;
import mx.gob.imss.mscme.admonplazas.utils.Mensajes;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<RespuestaGenerica<Void>> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Exception BadCredentialsException {}", ex);
        RespuestaGenerica<Void> errorResponse = new RespuestaGenerica<>(false, Mensajes.MSG005.getMensaje(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<RespuestaGenerica<Void>> handleUsernameNotFoundException(UserNotFoundException ex) {
        log.error("Exception UserNotFoundException {}", ex);
        RespuestaGenerica<Void> errorResponse = new RespuestaGenerica<>(false, Mensajes.MSG004.getMensaje(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // Maneja la excepción de argumento ilegal (ej. de tus servicios)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RespuestaGenerica<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Exception IllegalArgumentException {}", ex);

        // Para cualquier otro IllegalArgumentException genérico
        RespuestaGenerica<?> respuesta = new RespuestaGenerica<>(false, ex.getMessage(), null);
        return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RespuestaGenerica<?>> handleGeneralException(Exception ex) {
        // Un manejador genérico para cualquier otra excepción no manejada
        log.error("Exception handleGeneralException {}", ex);

        RespuestaGenerica<?> respuesta = new RespuestaGenerica<>(false, ex.getMessage(), null);
        return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}