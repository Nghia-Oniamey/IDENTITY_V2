package fplhn.udpm.identity.infrastructure.config.excelconfig.repository;


import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.repository.CampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainCampusJobConfigRepository extends CampusRepository {

    Optional<Campus> findByTen(String ten);

    @Query("""
            SELECT cs FROM Campus cs
            LEFT JOIN
                DepartmentCampus bmtcs on cs.id = bmtcs.campus.id
            LEFT JOIN
                Staff nv on nv.departmentCampus.id = bmtcs.id
            WHERE
                nv.id = :idNhanVien
            """
    )
    Optional<Campus> findByIdNhanVien(Long idNhanVien);

}
