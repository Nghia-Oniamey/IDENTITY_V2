package fplhn.udpm.identity.repository;

import fplhn.udpm.identity.entity.DepartmentCampus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentCampusRepository extends JpaRepository<DepartmentCampus, Long> {
}