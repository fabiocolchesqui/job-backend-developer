package br.com.intelipost.login;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * The Class SecurityLoginTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestPropertySource(value = "classpath:application.properties")
public class SecurityLoginTest {

	/** The url. */
	private String url;
	
	/** The client. */
	private CloseableHttpClient client;

	//https://github.com/intelipost/job-backend-developer
	
	/**
	 * Inits url and http client.
	 */
	@Before
	public void init() {
		this.url = "http://localhost:8080";
		this.client = HttpClientBuilder.create().build();
	}

	/**
	 * Login authentication test.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Test
	public void loginAuthenticationTest() throws IOException {
		HttpPost request = new HttpPost(url + "/app/authentication");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("j_username", "login1"));
		nvps.add(new BasicNameValuePair("j_password", "1234"));
		nvps.add(new BasicNameValuePair("spring_security_remember_me", "true"));
		nvps.add(new BasicNameValuePair("submit", "Login"));
		request.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
		HttpResponse httpResponse = client.execute(request);
		assertEquals(200, httpResponse.getStatusLine().getStatusCode());
	}

}
