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
@Table(name = "bo_mon")
public class Department extends BaseEntity {

    @Column(name = "ma")
    @Nationalized
    private String ma; // -.-

    @Column(name = "ten")
    @Nationalized
    private String ten;

}
