package com.videoClub.config;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CepKieSessionConfig {
	
	@Autowired
	private KieContainer kieContainer;
	
	@Bean(name = "loggingKieSession")
	public KieSession loggingKieSession() {
		return kieContainer.newKieSession("cepRulesSession");
	}
	
	@Bean(name = "cepPurchaseSession")
	public KieSession cepPurchaseSession() {
		return kieContainer.newKieSession("cepPurchaseSession");
	}
	
	@Bean(name = "cepConfigKsessionRealtimeClock")
	public KieSession cepConfigKsessionRealtimeClock() {
		return kieContainer.newKieSession("cepConfigKsessionRealtimeClock");
	}

}
