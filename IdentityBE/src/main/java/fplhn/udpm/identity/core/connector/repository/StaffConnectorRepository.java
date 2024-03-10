package fplhn.udpm.identity.core.connector.repository;

import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.repository.StaffRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffConnectorRepository extends StaffRepository {

    @Query(
            """
                    SELECT s
                    FROM Staff s
                    LEFT JOIN StaffRoleModule srm ON s.id = srm.staff.id
                    LEFT JOIN Role r ON srm.role.id = r.id
                    WHERE r.ma = :roleCode 
                    """
    )
    List<Staff> findByRoleCode(String roleCode);

    @Query(
            """
                    SELECT r
                    FROM Staff s
                    LEFT JOIN StaffRoleModule srm ON s.id = srm.staff.id
                    LEFT JOIN Role r ON srm.role.id = r.id
                    WHERE s.staffCode = :staffCode
                    """
    )
    List<Role> findRoleCodeByStaffId(String staffCode);

    Optional<Staff> findByStaffCode(String staffCode);

}
