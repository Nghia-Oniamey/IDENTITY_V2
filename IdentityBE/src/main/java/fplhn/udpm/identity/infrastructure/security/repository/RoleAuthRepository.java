package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.repository.RoleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleAuthRepository extends RoleRepository {

    @Query("SELECT r FROM Role r WHERE r.ma = 'STAFF'")
    Optional<Role> findRoleStaff();

}
