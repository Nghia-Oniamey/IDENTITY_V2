package fplhn.udpm.identity.infrastructure.config.dbgenerator.repository;

import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.repository.DepartmentRepository;

public interface DepartmentTempRepository extends DepartmentRepository {

    Department findByTen(String ten);

}
