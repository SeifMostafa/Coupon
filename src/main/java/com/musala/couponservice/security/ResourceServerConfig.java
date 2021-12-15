package com.musala.couponservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

//@Configuration
//@EnableAuthorizationServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "couponservice";
    @Value("${publicKey}")
    private String publicKey;

//    @Value("${keyFile}")
//    private String keyFile;
//    @Value("${password}")
//    private String password;
//    @Value("${alias}")
//    private String alias;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                mvcMatchers(HttpMethod.GET, "/couponapi/coupons/{code:^[A-Z]*$}").
                hasAnyRole("USER", "ADMIN").mvcMatchers(HttpMethod.POST, "/couponapi/coupons")
                .hasAnyRole("ADMIN").
                anyRequest().denyAll().and().csrf().disable();

    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(keyFile), password.toCharArray());
//        KeyPair keyPair = keyStoreKeyFactory.getKeyPair(alias);
//        jwtAccessTokenConverter.setKeyPair(keyPair);
        // jwtAccessTokenConverter.setSigningKey("testkey");
        jwtAccessTokenConverter.setVerifierKey(publicKey);
        return jwtAccessTokenConverter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
}
