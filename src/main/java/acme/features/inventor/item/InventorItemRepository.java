package acme.features.inventor.item;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Item;
import acme.entities.ItemType;
import acme.entities.SystemConfiguration;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface InventorItemRepository extends AbstractRepository {
	
	@Query("SELECT i FROM Item i WHERE i.itemType = :type AND i.inventor.id = :id")
	List<Item> findItemsByTypeAndInventor(ItemType type, Integer id);

	@Query("SELECT i FROM Item i WHERE i.id = :id")
	Item findItemById(Integer id);
	
	@Query("SELECT i FROM Inventor i WHERE i.id = :id")
	Inventor findInventorById(Integer id);

	@Query("SELECT i FROM Item i WHERE i.code = :code")
	Item findItemByCode(String code);
	
	@Query("SELECT i FROM Inventor i")
	List<Inventor> findAllInventors();

	@Query("SELECT i FROM Inventor i WHERE i.userAccount.username = :username")
	Inventor findInventorByUsername(String username);
	
	@Query("select s from SystemConfiguration s")
	SystemConfiguration findSystemConfiguration();
	
	@Query("select q.item from Quantity q where q.id=:id")
	Item findItemByQuantityId(int id);
	
	@Query("select i from Item i where i.name=:itemName")
	Item findItemByName(String itemName);
	
	@Query("select i from Item i where i.published=true")
	List<Item> findPublishedItems();
	@Query("select i from Item i where i.published = true and i not in (select q.item from Quantity q where q.toolkit.id = :toolkitId)")
	Collection<Item> findAssignableItemsToToolkit(int toolkitId);
	

}
