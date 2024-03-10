package fplhn.udpm.identity.core.auth.repository;

import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.repository.ModuleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleAuthEntryRepository extends ModuleRepository {

    Optional<Module> findByUrl(String url);

}
