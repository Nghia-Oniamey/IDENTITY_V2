package fplhn.udpm.identity.entity;

import fplhn.udpm.identity.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "bo_mon_theo_co_so")
public class DepartmentCampus extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_co_so")
    private Campus campus;

    @ManyToOne
    @JoinColumn(name = "id_bo_mon")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "id_chu_nhiem_bo_mon", nullable = true)
    private Staff staff;

}
