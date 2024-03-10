package fplhn.udpm.identity.core.auth.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TokenRefreshResponse {

    private String accessToken;

    private String refreshToken;

}
