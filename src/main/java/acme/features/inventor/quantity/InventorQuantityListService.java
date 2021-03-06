package acme.features.inventor.quantity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Quantity;
import acme.entities.Toolkit;
import acme.features.inventor.item.InventorItemRepository;
import acme.features.inventor.toolkit.InventorToolkitRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.helpers.CollectionHelper;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorQuantityListService implements AbstractListService<Inventor,Quantity>{
	
	@Autowired
	protected InventorToolkitRepository toolkitRepository;
	
	@Autowired
	protected InventorItemRepository itemRepository;
	
	@Override
	public boolean authorise(final Request<Quantity> request) {
		assert request != null;
		
		boolean result;
		int masterId;
		Toolkit toolkit;

		masterId = request.getModel().getInteger("masterId");
		toolkit = this.toolkitRepository.findOneToolkitById(masterId);
		result = (toolkit != null && (!toolkit.isPublished() || request.isPrincipal(toolkit.getInventor())));

		return result;
	}
	
	@Override
	public Collection<Quantity> findMany(final Request<Quantity> request) {
		assert request != null;
		
		Collection<Quantity> quantities;
		final int masterId;
		masterId = request.getModel().getInteger("masterId");
		quantities = this.toolkitRepository.findQuantityByToolkitId(masterId);
		
		return quantities;
	}
	
	@Override
	public void unbind(final Request<Quantity> request, final Collection<Quantity> entities, final Model model) {
		assert request != null; 
		assert !CollectionHelper.someNull(entities);
		assert model != null; 
		
		int masterId;
		final Toolkit toolkit;
		
		masterId = request.getModel().getInteger("masterId");
		
		toolkit = this.toolkitRepository.findOneToolkitById(masterId);
		
		final boolean showCreate = (!toolkit.isPublished() && request.isPrincipal(toolkit.getInventor()));
		
		model.setAttribute("masterId", masterId);
		model.setAttribute("showCreate", showCreate);
		 
	}
	
	@Override
	public void unbind(final Request<Quantity> request, final Quantity entity, final Model model) {
		assert request != null; 
		assert entity != null; 
		assert model != null; 
 
		request.unbind(entity, model, "itemsNumber", "item.itemType", "item.name", "item.retailPrice","item.systemRetailPrice"); 
		 
	}

}
