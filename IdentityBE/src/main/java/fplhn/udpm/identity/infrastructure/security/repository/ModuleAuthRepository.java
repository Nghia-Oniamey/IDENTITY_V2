package fplhn.udpm.identity.infrastructure.security.repository;

import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.repository.ModuleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleAuthRepository extends ModuleRepository {

    @Query("SELECT m FROM Module m WHERE m.url = :url")
    Optional<Module> findByUrl(String url);

}
