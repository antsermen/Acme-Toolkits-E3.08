package acme.features.authenticated.systemConfiguration;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedSystemConfigurationRepository extends AbstractRepository {
	
	@Query("select s from SystemConfiguration s")
	SystemConfiguration findSystemConfiguration();

}