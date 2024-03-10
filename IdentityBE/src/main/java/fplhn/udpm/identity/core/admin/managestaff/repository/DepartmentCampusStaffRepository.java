package fplhn.udpm.identity.core.admin.managestaff.repository;

import fplhn.udpm.identity.core.admin.managestaff.model.response.CampusResponse;
import fplhn.udpm.identity.core.admin.managestaff.model.response.DepartmentResponse;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.repository.DepartmentCampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentCampusStaffRepository extends DepartmentCampusRepository {

    @Query(
            value = """
                    SELECT DISTINCT 
                        bm.id as id,
                        bm.ten as name
                    FROM
                        bo_mon bm
                    """,
            nativeQuery = true)
    List<DepartmentResponse> findAllDepartment();

    @Query(
            value = """
                    SELECT DISTINCT 
                        cs.id as id,
                        cs.ten as name
                    FROM
                        co_so cs
                    """,
            nativeQuery = true)
    List<CampusResponse> findAllCampus();

    Optional<DepartmentCampus> findByCampusAndDepartment(Campus campus, Department department);

}
