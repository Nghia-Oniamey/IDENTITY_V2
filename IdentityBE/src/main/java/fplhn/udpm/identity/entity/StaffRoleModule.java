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
@Table(name = "nhan_vien-chuc_vu")
public class StaffRoleModule extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "id_chuc_vu")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "id_nhan_vien")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "id_modules")
    private Module module;

}
