package fplhn.udpm.identity.core.admin.managerole.repository;

import fplhn.udpm.identity.core.admin.managerole.model.request.AdminRoleRequest;
import fplhn.udpm.identity.core.admin.managerole.model.response.AdminRoleResponse;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRoleRepository extends RoleRepository {

    @Query("""
                SELECT x.id as id, x.ma as ma, x.ten as ten, x.deleteStatus as status FROM Role x 
                WHERE (:#{#request.ten} IS NULL OR :#{#request.ten} LIKE '' OR x.ten LIKE  CONCAT('%', :#{#request.ten}, '%')) 
                AND
                ( :#{#request.arrayField} IS NULL OR :#{#request.arrayField == null ? true : #request.arrayField.empty} = true OR x.id IN :#{#request.arrayField} )
                AND
                (:#{#request.ma} IS NULL OR :#{#request.ma} LIKE '' OR x.ma LIKE  CONCAT('%', :#{#request.ma}, '%')) 
                AND 
                (
                (:#{#request.q} IS NULL OR :#{#request.q} LIKE '' OR x.ma LIKE  CONCAT('%', :#{#request.q}, '%')) OR 
                (:#{#request.q} IS NULL OR :#{#request.q} LIKE '' OR x.ten LIKE  CONCAT('%', :#{#request.q}, '%'))
                )
            """)
    Page<AdminRoleResponse> findAllRole(@Param("request") AdminRoleRequest request, Pageable pageable);


    Optional<Role> findByMa(String ma);

    Boolean existsByMa(String ma);

}
