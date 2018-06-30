package br.com.intelipost;

import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * The Class ApplicationWeb.
 */
@Configuration
@ImportResource("classpath:config/spring-security.xml")
public class ApplicationWeb extends SpringBootServletInitializer {

	/** The Constant DISPATCHER. */
	private static final String DISPATCHER = "dispatcher";

	/** Initializes the application servlet on startup. */
	@Override
	public void onStartup(ServletContext container) throws ServletException {
		ServletRegistration.Dynamic registration = container.addServlet(DISPATCHER, new DispatcherServlet());
		registration.setLoadOnStartup(1);
		registration.addMapping("/");
		super.onStartup(container);
	}
}