package com.zetaemmesoft.myhome.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.token.signing.key}")
    private String securityTokenSigningKey;

    @Value("${access.token.validity.seconds}")
    private int accessTokenValiditySeconds;

    @Value("${client.id}")
    private String clientId;

    @Value("${client.secret}")
    private String clientSecret;

    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String TRUST = "trust";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
	JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	converter.setSigningKey(securityTokenSigningKey);
	return converter;
    }

    @Bean
    public TokenStore tokenStore() {
	return new JwtTokenStore(accessTokenConverter());
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer client) throws Exception {
	client.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).authorizedGrantTypes(GRANT_TYPE_PASSWORD).scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
		.accessTokenValiditySeconds(accessTokenValiditySeconds);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
	endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).accessTokenConverter(accessTokenConverter());
    }
}
