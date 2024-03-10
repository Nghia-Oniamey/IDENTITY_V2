package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.repository.StaffRoleModuleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffModuleRoleAuthRepository extends StaffRoleModuleRepository {

    @Query(
            value = """
                    SELECT
                    	r.ma
                    FROM
                    	[nhan_vien-chuc_vu] smr
                    LEFT JOIN chuc_vu r ON
                    	smr.id_chuc_vu = r.id
                    LEFT JOIN nhan_vien nv on smr.id_nhan_vien = nv.id 	
                    WHERE
                    	nv.id = :userId
                    """,
            nativeQuery = true
    )
    List<String> getListRoleCodeByUserId(Long userId);

    @Query(
            value = """
                    SELECT
                    	r.ten
                    FROM
                    	[nhan_vien-chuc_vu] smr
                    LEFT JOIN chuc_vu r ON
                    	smr.id_chuc_vu = r.id
                    LEFT JOIN nhan_vien nv on smr.id_nhan_vien = nv.id 	
                    WHERE
                    	nv.id = :userId
                    """,
            nativeQuery = true
    )
    List<String> getListRoleNameByUserId(Long userId);
}
