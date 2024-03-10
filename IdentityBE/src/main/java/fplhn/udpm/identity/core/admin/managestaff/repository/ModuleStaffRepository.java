package fplhn.udpm.identity.core.admin.managestaff.repository;

import fplhn.udpm.identity.core.admin.managestaff.model.response.ModuleResponse;
import fplhn.udpm.identity.repository.ModuleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleStaffRepository extends ModuleRepository {

    @Query(
            value = """
                    SELECT
                        m.id as id,
                        m.ten as name
                    FROM module m
                    """,
            nativeQuery = true
    )
    List<ModuleResponse> getAllModule();


    @Query(
            value = """
                    SELECT
                        m.id as id,
                        m.ten as name
                    FROM
                        module m
                    LEFT JOIN [nhan_vien-chuc_vu] smr ON m.id = smr.id_modules
                    LEFT JOIN nhan_vien nv ON smr.id_nhan_vien = nv.id
                    WHERE nv.id = :id  
                    """,
            nativeQuery = true
    )
    List<ModuleResponse> findAllModuleByStaffId(Long id);

}
