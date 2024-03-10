package fplhn.udpm.identity.repository;

import fplhn.udpm.identity.entity.Campus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampusRepository extends JpaRepository<Campus, Long> {
}