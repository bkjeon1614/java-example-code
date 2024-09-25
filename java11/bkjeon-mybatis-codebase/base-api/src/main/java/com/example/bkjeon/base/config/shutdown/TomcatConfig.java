package com.example.bkjeon.base.config.shutdown;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

	private final GracefulShutdownTomcatConnector gracefulShutdownTomcatConnector;

	public TomcatConfig(GracefulShutdownTomcatConnector gracefulShutdownTomcatConnector) {
		this.gracefulShutdownTomcatConnector = gracefulShutdownTomcatConnector;
	}

	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(gracefulShutdownTomcatConnector);

		return factory;
	}

}
