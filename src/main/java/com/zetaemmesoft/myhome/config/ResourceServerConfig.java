package com.zetaemmesoft.myhome.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.zetaemmesoft.myhome.filter.CustomFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id_monitor";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
	resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

	http.headers().frameOptions().sameOrigin();

	http.csrf().disable().httpBasic().disable().anonymous().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().requestMatchers().and().authorizeRequests()
		.antMatchers("/services/rest/**").authenticated();

	http.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);


    }

    @Bean
    public CustomFilter filter() {
	return new CustomFilter();
    }

}