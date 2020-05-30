package com.videoClub.config;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingKieSessionConfig {
	
	@Autowired
	private KieContainer kieContainer;
	
	@Bean
	public KieSession kieSession() {
		return kieContainer.newKieSession("cepRulesSession");
	}

}
