package acme.features.inventor.toolkit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Toolkit;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Inventor;

@Service
public class InventorListToolkitsService implements AbstractListService<Inventor, Toolkit>{
	
			// Internal state ========================================================
	
			@Autowired
			protected InventorToolkitRepository repository;
			
			// AbstractListService<Inventor, Toolkit> interface ============
			
			@Override
			public boolean authorise(final Request<Toolkit> request) {
				assert request != null;
				
				return true;
			}
			
			@Override
			public Collection<Toolkit> findMany(final Request<Toolkit> request) {
				assert request != null;
				
				Collection<Toolkit> result;
				int inventorId;
				
				inventorId = request.getModel().getInteger("inventorId");
				result = this.repository.findToolkitsFromInventorId(inventorId);
				
				return result;
			}
			
			@Override
			public void unbind(final Request<Toolkit> request, final Toolkit entity, final Model model) {
				assert request != null;
				assert entity != null;
				assert model != null;
				
				request.unbind(entity, model, "code", "title", "description", "notes", "link", "retailPrice");
			}
}
