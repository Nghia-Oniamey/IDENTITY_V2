package fplhn.udpm.identity.infrastructure.security.service;

import fplhn.udpm.identity.entity.RefreshToken;
import fplhn.udpm.identity.infrastructure.security.UserPrincipal;
import fplhn.udpm.identity.infrastructure.security.repository.RefreshTokenAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StaffAuthRepository;
import fplhn.udpm.identity.infrastructure.security.repository.StudentAuthRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${app.refresh-token.validity-in-seconds}")
    private Long refreshTokenDurationMs;

    private RefreshTokenAuthRepository refreshTokenRepository;

    private StaffAuthRepository staffAuthRepository;

    private StudentAuthRepository studentAuthRepository;

    @Autowired
    public void setRefreshTokenRepository(RefreshTokenAuthRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Autowired
    public void setStaffAuthRepository(StaffAuthRepository staffAuthRepository) {
        this.staffAuthRepository = staffAuthRepository;
    }

    @Autowired
    public void setStudentAuthRepository(StudentAuthRepository studentAuthRepository) {
        this.studentAuthRepository = studentAuthRepository;
    }

    public Optional<RefreshToken> findByToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    public RefreshToken createRefreshToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Long isTokenRevoke = refreshTokenRepository.isRevoked(userPrincipal.getId());

        if (isTokenRevoke != null) {
            refreshTokenRepository.deleteByUserId(userPrincipal.getId());
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userPrincipal.getId());
        refreshToken.setExpiredAt(System.currentTimeMillis() + refreshTokenDurationMs);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiredAt() < Instant.now().toEpochMilli()) {
            refreshTokenRepository.delete(token);
            return null;
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }

}
