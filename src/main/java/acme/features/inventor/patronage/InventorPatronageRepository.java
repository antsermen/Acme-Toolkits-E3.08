package acme.features.inventor.patronage;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Patronage;
import acme.entities.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorPatronageRepository extends AbstractRepository{
		
		@Query("select p from Patronage p where p.inventor.id = :id and p.published=:published")
		Collection<Patronage> findPatronagesFromInventorId(int id, boolean published);
		
		@Query("select p from Patronage p where p.id = :id")
		Patronage findOnePatronageById(int id);
		
		@Query("select s from SystemConfiguration s")
		SystemConfiguration findSystemConfiguration();
		
}
