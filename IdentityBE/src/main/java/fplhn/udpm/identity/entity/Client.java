package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
@Table(name = "client")
public class Client extends BaseEntity {

    @OneToOne()
    private Module module;

    private String clientId;

    private String clientSecret;

}
