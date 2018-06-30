package br.com.intelipost.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.intelipost.response.UserResponse;

/**
 * The Class LoginRestController.
 */
@RestController
@RequestMapping("intelipost/auth")
public class LoginRestController extends AbstractController {

	/**
	 * Gets the logged account.
	 *
	 * @return the logged account
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/loggedAccount", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponse> getLoggedAccount() throws Exception {
		if (authenticateUser()) {
			return new ResponseEntity<UserResponse>(
					new UserResponse(SecurityContextHolder.getContext().getAuthentication().getName()), HttpStatus.OK);
		} else {
			throw new Exception();
		}
	}

}
