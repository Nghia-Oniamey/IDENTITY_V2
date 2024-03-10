package fplhn.udpm.identity.core.admin.managestaff.repository;

import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.repository.CampusRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampusStaffRepository extends CampusRepository {

    Optional<Campus> findByTen(String ten);

}
