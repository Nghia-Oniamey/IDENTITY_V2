package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.entity.AccessToken;
import fplhn.udpm.identity.repository.AccessTokenRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessTokenAuthRepository extends AccessTokenRepository {

    @Query(
            value = """
                            SELECT revoked_at
                            FROM access_token ac
                            WHERE ac.user_id = :userId
                    """,
            nativeQuery = true
    )
    Long isRevoked(Long userId);

    @Query(
            value = """
                    SELECT access_token
                    FROM access_token ac
                    WHERE ac.user_id = :userId
                    """,
            nativeQuery = true
    )
    String getAccessTokenByUserId(Long userId);

    Optional<AccessToken> findByUserId(Long userId);

}
