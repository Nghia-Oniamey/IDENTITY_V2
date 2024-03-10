package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "co_so")
public class Campus extends BaseEntity {

    @Column(name = "ma", length = 255, nullable = true)
    @Nationalized
    private String ma;

    @Column(name = "ten", length = 255, nullable = true)
    @Nationalized
    private String ten;

}
