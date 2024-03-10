package fplhn.udpm.identity.core.connector.repository;

import fplhn.udpm.identity.entity.Client;
import fplhn.udpm.identity.repository.ClientRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientConnectorRepository extends ClientRepository {

    Optional<Client> findByClientIdAndClientSecret(String clientId, String clientSecret);

}
