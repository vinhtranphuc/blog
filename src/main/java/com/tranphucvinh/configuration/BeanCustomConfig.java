package com.tranphucvinh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@Configuration
public class BeanCustomConfig {

	/**
	 * SpringCustomConfigurator
	 * @return
	 */
	@Bean
	public SpringCustomConfigurator springCustomConfigurator() {
		return new SpringCustomConfigurator(); // This is just to get context
	}

	/**
	 * AnnotatedEndpointConnectionManager
	 * @return
	 * @throws DeploymentException
	 * @throws IOException
	 * @throws URISyntaxException
	 */

	/**
	 * ServerEndpointExporter
	 * @return
	 */
	@Bean
	public ServerEndpointExporter endpointExporter() {
		return new ServerEndpointExporter();
	}

	/**
	 * LayoutDialect
	 * @return
	 */
	@Bean
	public LayoutDialect layoutDialect() {
		return new LayoutDialect();
	}
}