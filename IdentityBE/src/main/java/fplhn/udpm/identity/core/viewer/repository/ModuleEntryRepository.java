package fplhn.udpm.identity.core.viewer.repository;

import fplhn.udpm.identity.core.viewer.response.ModuleEntryResponse;
import fplhn.udpm.identity.repository.ModuleRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleEntryRepository extends ModuleRepository {

    @Query(
            value = """
                    SELECT 
                        m.ten AS name,
                        m.url AS uri
                    FROM
                        module m
                    """,
            nativeQuery = true
    )
    List<ModuleEntryResponse> findAllModule();

}
