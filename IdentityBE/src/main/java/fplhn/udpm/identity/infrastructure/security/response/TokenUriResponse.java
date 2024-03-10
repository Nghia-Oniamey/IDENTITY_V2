package fplhn.udpm.identity.infrastructure.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TokenUriResponse {

    private String accessToken;

    private String refreshToken;

    public String getTokenAuthorization() {
        return "{" + "\"accessToken\":\"" + accessToken + "\",\"refreshToken\":\"" + refreshToken + "\"}";
    }

}
