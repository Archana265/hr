package com.opus.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;

public class RibbonServiceConfiguration {

	@Autowired
	IClientConfig ribbonClientConfig;

	@Bean
	public IPing ribboPing(IClientConfig config)
	{
		System.err.println("In ribbon config"+config);
		return new PingUrl();
	}
	
	@Bean
	public IRule ribbonRule(IClientConfig ribbonClientConfig){
		return new AvailabilityFilteringRule();
	}
}
