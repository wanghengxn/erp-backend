package tech.qdhxy.erp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private Security security;
    private CorsConfiguration cors;

    @Getter
    @Setter
    public static class Security {
        private Authentication authentication;
    }
    @Getter
    @Setter
    public static class Authentication {
        private Jwt jwt;
    }
    @Getter
    @Setter
    public static class Jwt{
        private String secret;
        private String base64Secret;
        private Long tokenValidityInSeconds = 1800L;
        private Long tokenValidityInSecondsForRememberMe = 2592000L;
    }
}
