package fplhn.udpm.identity.infrastructure.security.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private final Auth auth = new Auth();

    private final OAuth2 oauth2 = new OAuth2();

    @Getter
    public static class Auth {

        @Value("${app.jwt.secret-key}")
        private String tokenSecret;

        @Value("${app.jwt.token-validity-in-seconds}")
        private long tokenExpirationMsec;

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }
    }

    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

}
