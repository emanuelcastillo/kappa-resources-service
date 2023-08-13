package com.kappa.resources.config;

import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.boot.autoconfigure.security.oauth2.server.servlet.OAuth2AuthorizationServerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
public class SecurityConfig {


    private static final String API_PROTECTED_PATH_RESOURCE  = "/api/v1/**";
    private static final String ROLE_PREMIUM = "PREMIUM";
    private static final String ROLE_BASIC = "BASIC";
    private static final String ROLE_USER = "USER";
    private static final String ROLE_ADMIN = "ADMIN";


    @Bean///sirve para ordenar los beans de configuracion del contenedor de spring
    SecurityFilterChain clientSecurityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(API_PROTECTED_PATH_RESOURCE).hasAnyRole(ROLE_ADMIN, ROLE_USER)
                        .anyRequest().permitAll()
        );
        httpSecurity.oauth2ResourceServer(oauth->oauth.jwt(
                confing -> confing.decoder(JwtDecoders.fromIssuerLocation("http://localhost:3000"))
        ));
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncode(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter(){
        var authConverter = new JwtGrantedAuthoritiesConverter();
        authConverter.setAuthoritiesClaimName("roles");
        authConverter.setAuthorityPrefix("");
        var converterResponse = new JwtAuthenticationConverter();
        converterResponse.setJwtGrantedAuthoritiesConverter(authConverter);
        return converterResponse;
    }

}
