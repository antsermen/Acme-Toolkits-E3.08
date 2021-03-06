package acme.features.patron.patronage;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpamDetector.Spam_Detector.SpamDetector;
import acme.entities.Patronage;
import acme.entities.Status;
import acme.features.inventor.item.InventorItemRepository;
import acme.forms.MoneyExchange;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractCreateService;
import acme.functions.MoneyExchangeFunction;
import acme.roles.Patron;

@Service
public class PatronPatronageCreateService implements AbstractCreateService<Patron, Patronage>{

	@Autowired
	protected InventorItemRepository inventorItemRepository;
	@Autowired
	protected PatronPatronageRepository patronPatronageRepository;

	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;
		return true;
	}

	@Override
	public void bind(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "code", "legalStuff", "budget", "creationDate", "startDate", "deadline", "info");
		entity.setInventor(this.inventorItemRepository.findInventorByUsername(request.getModel().getAttribute("inventor").toString()));
		entity.setStatus(Status.PROPOSED);
	}


	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "code", "legalStuff", "budget", "creationDate", "startDate", "deadline", "info");
		model.setAttribute("inventors", this.inventorItemRepository.findAllInventors());
	}

	@Override
	public Patronage instantiate(final Request<Patronage> request) {
		
		final Patronage result = new Patronage();
		
		result.setStatus(Status.PROPOSED);
		result.setCode("");
		result.setLegalStuff("");
		result.setCreationDate(new Date());
		result.setInfo("");
		result.setPatron(this.patronPatronageRepository.findOnePatronById(request.getPrincipal().getActiveRoleId()));

		
		return result;
	}

	@Override
	public void validate(final Request<Patronage> request, final Patronage entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		if(!errors.hasErrors("code")) {
			final Patronage i = this.patronPatronageRepository.findOnePatronageByCode((entity.getCode()));
			errors.state(request, i == null || i.getId()==entity.getId(),"code", "patron.patronage.form.error.code.duplicated");
		}
		if(!errors.hasErrors("legalStuff")) {
			errors.state(request, !SpamDetector.spamDetector(entity.getLegalStuff(), this.inventorItemRepository.findSystemConfiguration().getWeakSpamTerms(), 
				this.inventorItemRepository.findSystemConfiguration().getStrongSpamTerms(), 
				this.inventorItemRepository.findSystemConfiguration().getWeakSpamThreshold(),
				this.inventorItemRepository.findSystemConfiguration().getStrongSpamThreshold()), "legalStuff", "patron.patronage.form.error.name.spam");
		}

		if(!errors.hasErrors("startDate")) {
			final Date now = new Date();
			final Date minStartDate = DateUtils.addMonths(now, 1);
			errors.state(request, entity.getStartDate().after(minStartDate), "startDate", "patron.patronage.form.error.startDate");

		}
		if(!errors.hasErrors("deadline")) {
			final Date minDeadline = DateUtils.addMonths(entity.getStartDate(), 1);
			errors.state(request, entity.getDeadline().after(minDeadline), "deadline", "patron.patronage.form.error.deadline");
		}
		if(!errors.hasErrors("budget")) {
			errors.state(request, entity.getBudget().getAmount()>0, "budget", "patron.patronage.form.error.budget.negative");
			final String[] acceptedCurrencies = this.inventorItemRepository.findSystemConfiguration().getAcceptedCurrencies().split(",");
			errors.state(request, Arrays.asList(acceptedCurrencies).contains(entity.getBudget().getCurrency()), "budget", "patron.patronage.form.error.budget.acceptedCurrencies");
		}
	}

	@Override
	public void create(final Request<Patronage> request, final Patronage entity) {
		assert request != null;
		assert entity != null;
		
		entity.setPublished(false);
		entity.setPatron(this.patronPatronageRepository.findOnePatronById(request.getPrincipal().getActiveRoleId()));
		Money source, target;
		String targetCurrency;
		MoneyExchange exchange;
		source = entity.getBudget();
		targetCurrency = this.inventorItemRepository.findSystemConfiguration().getSystemCurrency();
		exchange=MoneyExchangeFunction.computeMoneyExchange(source, targetCurrency);
		target=exchange.target;
		entity.setSystemBudget(target);
		entity.setPublished(false);
		entity.setPatron(this.patronPatronageRepository.findOnePatronById(request.getPrincipal().getActiveRoleId()));
		
		entity.setStatus(Status.PROPOSED);
		
		this.patronPatronageRepository.save(entity);
	}
	
	

}
