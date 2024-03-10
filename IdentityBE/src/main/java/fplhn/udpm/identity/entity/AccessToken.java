package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseTokenEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "access_token")
public class AccessToken extends BaseTokenEntity {

    @Column(name = "access_token", length = 8000)
    private String accessToken;

    @Column(name = "expired_at")
    private Long expiredAt;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "revoked_at")
    private Long revokedAt;

}
