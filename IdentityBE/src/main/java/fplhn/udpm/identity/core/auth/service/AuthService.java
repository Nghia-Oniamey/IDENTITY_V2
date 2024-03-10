package fplhn.udpm.identity.core.auth.service;

import fplhn.udpm.identity.core.auth.model.request.RefreshTokenRequest;
import fplhn.udpm.identity.core.common.config.ResponseObject;

public interface AuthService {

    ResponseObject refreshToken(RefreshTokenRequest request);

    ResponseObject logout(Long userId);

}
