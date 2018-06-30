package br.com.intelipost.response;

/**
 * The Class UserResponse.
 */
public class UserResponse {

	/** The username. */
	private String username;

	/**
	 * Instantiates a new user response.
	 *
	 * @param username the username
	 */
	public UserResponse(String username) {
		this.username = username;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

}
