package br.com.intelipost.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The Class AbstractController.
 */
public abstract class AbstractController {

	/** The session registry. */
	@Autowired
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;

	/**
	 * Authenticate user.
	 *
	 * @return the boolean
	 */
	public Boolean authenticateUser() {
		return SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
				&& !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
						? true
						: false;
	}

	/**
	 * Handle bad requests.
	 *
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@ExceptionHandler(Exception.class)
	public void handleBadRequests(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_ACCEPTABLE.value());
	}

}
