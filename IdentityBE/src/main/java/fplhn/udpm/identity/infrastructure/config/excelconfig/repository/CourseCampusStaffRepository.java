package fplhn.udpm.identity.infrastructure.config.excelconfig.repository;

import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.repository.DepartmentCampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("CourseCampusStaffRepositoryExcel")
public interface CourseCampusStaffRepository extends DepartmentCampusRepository {

    @Query(
            """
                    select bmtcs
                    from DepartmentCampus bmtcs
                    left join bmtcs.department bm
                    left join bmtcs.campus cs
                    where bm.ten = :tenBoMon and cs.ten = :tenCoSo
                    """
    )
    Optional<DepartmentCampus> findByBoMonCoSo(String tenBoMon, String tenCoSo);

    @Query(
            value = """
                    select bm.ten + '-' + cs.ten as tenBoMon
                    from bo_mon bm
                    join dbo.bo_mon_theo_co_so bmtcs on bm.id = bmtcs.id_bo_mon
                    join dbo.co_so cs on bmtcs.id_co_so = cs.id
                    where bmtcs.id_co_so = :coSoId
                    """,
            nativeQuery = true
    )
    List<String> findAllByCoSo(Long coSoId);


    @Query(
            value = """
                    select cs.ten as tenCoSo
                    from co_so cs
                    """,
            nativeQuery = true
    )
    List<String> findAllCampusName();

    @Query(
            value = """
                    select bm.ten as tenBoMon
                    from bo_mon bm
                    """,
            nativeQuery = true
    )
    List<String> findAllDepartmentName();


}
