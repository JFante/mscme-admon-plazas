package mx.gob.imss.mscme.admonplazas.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7142720879702848823L;

	public UserNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UserNotFoundException(String msg) {
        super(msg);
    }
}
