package acme.features.authenticated.announcement;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Announcement;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedAnnouncementShowService implements AbstractShowService<Authenticated, Announcement>{
	
		// Internal state ======================================================
	
		@Autowired
		protected AuthenticatedAnnouncementRepository repository;
		
		// AbstractShowService<Authenticated, Announcement> interface ==========
		
		@Override
		public boolean authorise(final Request<Announcement> request) {
			assert request != null;
			
			int id;
			id = request.getModel().getInteger("id");
			Announcement announcement; 
			announcement = this.repository.findOneAnnouncementById(id);
			Calendar calendar;
			Date deadline;
			calendar = Calendar.getInstance();
			calendar.add(Calendar.MONTH, -1);
			deadline = calendar.getTime();
			long diferencia;
			long milisegundos;
			diferencia = announcement.getCreationMoment().getTime() - deadline.getTime();
			milisegundos = 2678400000L;
			assert diferencia <= milisegundos;
			
			return true;
		}
		
		@Override
		public Announcement findOne(final Request<Announcement> request) {
			assert request != null;
			
			Announcement result;
			int id;
			
			id = request.getModel().getInteger("id");
			result = this.repository.findOneAnnouncementById(id);
			
			return result;
		}
		
		@Override
		public void unbind(final Request<Announcement> request, final Announcement entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;
			
			request.unbind(entity, model, "creationMoment", "title", "body", "critical", "link");
			model.setAttribute("confirmation", false);
			model.setAttribute("readonly", true);
		}
}
