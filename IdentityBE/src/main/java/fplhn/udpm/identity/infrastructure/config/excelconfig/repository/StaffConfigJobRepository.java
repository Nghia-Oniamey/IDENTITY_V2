package fplhn.udpm.identity.infrastructure.config.excelconfig.repository;

import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.repository.StaffRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("excelNhanVienRepository")
public interface StaffConfigJobRepository extends StaffRepository {

    Optional<Staff> findNhanVienByStaffCode(String maNhanVien);


}
