package br.com.intelipost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.intelipost.entity.Credential;

/**
 * The Interface LoginRespository.
 */
@Repository
public interface LoginRespository extends JpaRepository<Credential, Long> {

	/**
	 * Find Credential by login.
	 *
	 * @param login the login
	 * @return the credential
	 */
	public Credential findByLogin(String login);

}
