package fplhn.udpm.identity.infrastructure.config.dbgenerator.repository;

import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.repository.CampusRepository;
import org.springframework.stereotype.Repository;

@Repository("campusTempRepository")
public interface CampusTempRepository extends CampusRepository {

    Campus findByTen(String ten);

}
