package fplhn.udpm.identity.core.admin.managestaff.repository;

import fplhn.udpm.identity.core.admin.managestaff.model.response.RoleResponse;
import fplhn.udpm.identity.repository.RoleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleStaffRepository extends RoleRepository {

    @Query(
            value = """
                    SELECT
                        r.id as id,
                        r.ten as name
                    FROM
                        chuc_vu r
                    """,
            nativeQuery = true
    )
    List<RoleResponse> getAllRole();


    @Query(
            value = """
                    SELECT
                        r.id as id,
                        r.ten as name
                    FROM
                        chuc_vu r
                    LEFT JOIN [nhan_vien-chuc_vu] smr ON r.id = smr.id_chuc_vu
                    LEFT JOIN nhan_vien nv ON smr.id_nhan_vien = nv.id
                    WHERE nv.id = :id  
                    """,
            nativeQuery = true
    )
    List<RoleResponse> findAllRoleByStaffId(Long id);

}
