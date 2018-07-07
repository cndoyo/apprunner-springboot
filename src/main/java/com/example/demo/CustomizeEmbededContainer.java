package com.example.demo;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class CustomizeEmbededContainer implements
		WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

	private Logger log = LoggerFactory.getLogger(CustomizeEmbededContainer.class);

	@Override
	public void customize(ConfigurableServletWebServerFactory server) {
		Map<String, String> settings = System.getenv();
		// When run from app-runner, you must use the port set in the
		// environment variable APP_PORT
		int port = Integer.parseInt(settings.getOrDefault("APP_PORT", "8081"));
		// All URLs must be prefixed with the app name, which is got via the
		// APP_NAME env var.
		String appName = settings.getOrDefault("APP_NAME", "my-app");
		String env = settings.getOrDefault("APP_ENV", "local"); // "prod" or
																// "local"
		boolean isLocal = "local".equals(env);
		log.info("Starting " + appName + " in " + env + " on port " + port);

		server.setPort(port);
		server.setContextPath("/" + appName);

	}

}
