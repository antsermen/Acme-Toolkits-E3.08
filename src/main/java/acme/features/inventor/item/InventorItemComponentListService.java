package acme.features.inventor.item;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Item;
import acme.entities.ItemType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorItemComponentListService implements AbstractListService<Inventor, Item>{
	
	//Internal state----------------------------------------------------------
	
	@Autowired
	protected InventorItemRepository inventorItemRepository;
	
	//AbstractListService------------------------------------------------------

	@Override
	public boolean authorise(final Request<Item> request) {
		assert request != null;
		return true;
	}

	@Override
	public Collection<Item> findMany(final Request<Item> request) {
		assert request != null;
		return this.inventorItemRepository.findItemsByTypeAndInventor(ItemType.COMPONENT, request.getPrincipal().getActiveRoleId());
	}

	@Override
	public void unbind(final Request<Item> request, final Item entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "itemType", "name", "code", "technology", "description", "retailPrice", "link", "published");
	}
	
	
		

}
