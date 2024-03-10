package fplhn.udpm.identity.core.auth.service.impl;

import fplhn.udpm.identity.core.auth.model.request.RefreshTokenRequest;
import fplhn.udpm.identity.core.auth.model.response.TokenRefreshResponse;
import fplhn.udpm.identity.core.auth.repository.AccessTokenAuthEntryRepository;
import fplhn.udpm.identity.core.auth.repository.ModuleAuthEntryRepository;
import fplhn.udpm.identity.core.auth.repository.RefreshTokenAuthEntryRepository;
import fplhn.udpm.identity.core.auth.service.AuthService;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.entity.RefreshToken;
import fplhn.udpm.identity.infrastructure.security.service.RefreshTokenService;
import fplhn.udpm.identity.infrastructure.security.service.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccessTokenAuthEntryRepository accessTokenAuthEntryRepository;

    private final RefreshTokenAuthEntryRepository refreshTokenAuthEntryRepository;

    private final ModuleAuthEntryRepository moduleAuthEntryRepository;

    private final RefreshTokenService refreshTokenService;

    private final TokenProvider tokenProvider;

    @Override
    public ResponseObject refreshToken(RefreshTokenRequest request) {
        try {
            String token = request.getRefreshToken();
            String currentHost = request.getCurrentHost();

            Optional<RefreshToken> refreshTokenOptional = refreshTokenAuthEntryRepository.findByRefreshToken(token);
            if (refreshTokenOptional.isEmpty() || refreshTokenOptional.get().getRevokedAt() != null) {
                return new ResponseObject(null, HttpStatus.NOT_FOUND, "Token not found");
            }
            Optional<Module> moduleOptional = moduleAuthEntryRepository.findByUrl(currentHost);
            if (moduleOptional.isEmpty()) {
                return new ResponseObject(null, HttpStatus.NOT_FOUND, "Module not found");
            }

            RefreshToken refreshToken = refreshTokenService.verifyExpiration(refreshTokenOptional.get());
            if (refreshToken == null) {
                return new ResponseObject(null, HttpStatus.NOT_ACCEPTABLE, "Token is expired");
            }

            String newAccessToken = tokenProvider.createToken(refreshTokenOptional.get().getUserId(), moduleOptional.get().getUrl());
            TokenRefreshResponse response = new TokenRefreshResponse();
            response.setAccessToken(newAccessToken);
            response.setRefreshToken(refreshTokenOptional.get().getRefreshToken());
            return new ResponseObject(response, HttpStatus.OK, "Token refreshed");
        } catch (Exception e) {
            return new ResponseObject(null, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public ResponseObject logout(Long userId) {
        int isDeleteSuccessRefreshToken = refreshTokenAuthEntryRepository.deleteByUserId(userId);
        int isDeleteSuccessAccessToken = accessTokenAuthEntryRepository.deleteByUserId(userId);
        if (isDeleteSuccessRefreshToken == 0 && isDeleteSuccessAccessToken == 0) {
            return new ResponseObject(null, HttpStatus.NOT_FOUND, "Token not found");
        }
        return new ResponseObject(null, HttpStatus.OK, "Logout success");
    }

}
