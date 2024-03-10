package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.repository.StaffRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffAuthRepository extends StaffRepository {

    @Query("SELECT s FROM Staff s WHERE s.accountFE = :accountFE")
    Optional<Staff> findByAccountFE(String accountFE);

    @Query("SELECT s FROM Staff s WHERE s.accountFPT = :accountFPT")
    Optional<Staff> findByAccountFPT(String accountFPT);

}
