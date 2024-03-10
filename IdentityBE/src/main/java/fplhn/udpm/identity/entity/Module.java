package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "module")
public class Module extends BaseEntity {

    @OneToOne(mappedBy = "module")
    private Client client;

    @Column(name = "ten")
    @Nationalized
    private String ten;

    @Column(name = "ma")
    @Nationalized
    private String ma;

    @Column(name = "url")
    @Nationalized
    private String url;

}
