package br.com.intelipost.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.intelipost.entity.Credential;
import br.com.intelipost.repository.LoginRespository;

/**
 * The Class CustomAuthenticationProvider.
 */
@Service
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	/** The Constant DEFAULT_ROLE. */
	private static final String DEFAULT_ROLE = "ADMIN";
	
	/** The Constant USER_NOT_FOUND. */
	private static final String USER_NOT_FOUND = "User not found.";
	
	/** The Constant USER_LOGGED. */
	private static final String USER_LOGGED = "User logged in.";

	/** The login respository. */
	@Autowired
	private LoginRespository loginRespository;

	/** The session registry. */
	@Autowired
	@Qualifier("sessionRegistry")
	private SessionRegistry sessionRegistry;

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#additionalAuthenticationChecks(org.springframework.security.core.userdetails.UserDetails, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected UserDetails retrieveUser(String login, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser = null;
		try {
			Credential credential = loginRespository.findByLogin(login);
			if (credential == null) {
				throw new UsernameNotFoundException(USER_NOT_FOUND);
			} else {
				loadedUser = new User(credential.getLogin(), credential.getPassword(),
						AuthorityUtils.createAuthorityList(DEFAULT_ROLE));
				List<Object> principals = sessionRegistry.getAllPrincipals();
				for (Object principal : principals) {
					if (principal instanceof User) {
						if (loadedUser.equals(principal)) {
							throw new Exception(USER_LOGGED);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new InternalAuthenticationServiceException(e.getMessage(), e);
		}
		
		return loadedUser;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return super.authenticate(authentication);
	}
	
}
