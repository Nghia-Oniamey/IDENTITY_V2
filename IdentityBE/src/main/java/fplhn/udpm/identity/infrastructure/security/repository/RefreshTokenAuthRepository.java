package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.entity.RefreshToken;
import fplhn.udpm.identity.repository.RefreshTokenRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenAuthRepository extends RefreshTokenRepository {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Modifying
    @Query(
            value = """
                    delete from  refresh_token
                    where user_id = :userId
                    """,
            nativeQuery = true
    )
    int deleteByUserId(Long userId);

    @Query(
            value = """
                            SELECT revoked_at
                            FROM refresh_token rt
                            WHERE rt.user_id = :userId
                    """,
            nativeQuery = true
    )
    Long isRevoked(Long userId);


    @Query(
            value = """
                    SELECT rt
                    FROM RefreshToken rt
                    WHERE rt.userId = :userId
                    """
    )
    RefreshToken getRefreshTokenByUserId(Long userId);

}
