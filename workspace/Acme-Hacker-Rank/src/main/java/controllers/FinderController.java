package controllers;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Finder;
import domain.Hacker;
import domain.Position;

import services.ActorService;
import services.FinderService;
import services.SystemConfigurationService;

@Controller
@RequestMapping("/finder/hacker")
public class FinderController extends AbstractController{
	

	// Services

	@Autowired
	private FinderService				finderService;

	@Autowired
	private ActorService 		actorService;
	
	
	@Autowired
	private SystemConfigurationService	systemConfigurationService;

	// Constructors

	public FinderController() {
		super();
	}
	// /list

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Finder finder;

		Hacker principal;

		principal = (Hacker) this.actorService.findByPrincipal();
		finder = principal.getFinder();
		Assert.isTrue(
				this.actorService.checkAuthority(principal, "HACKER"),
				"not.allowed");

		final Collection<Position> positions = finder.getResults();

		result = new ModelAndView("finder/list");
		result.addObject("positions", positions);
		result.addObject("requestUri", "finder/hacker/list.do");

		return result;
	}
	// DELETE
	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Finder finder, final BindingResult binding) {
	
		ModelAndView result;
		try {
			
			this.finderService.delete(finder);
			result = new ModelAndView("redirect:search.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(finder, "finder.commit.error");
		}

		return result;
	}
	// search
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search() {
		ModelAndView result;
		Finder finder;

		Hacker principal;

		principal = (Hacker) this.actorService.findByPrincipal();
		finder = principal.getFinder();
		Assert.isTrue(
				this.actorService.checkAuthority(principal, "HACKER"),
				"not.allowed");
		Date maxLivedMoment = new Date();

		

		finder = principal.getFinder();
		if (finder.getSearchMoment() != null) {
			final int timeChachedFind = this.systemConfigurationService.findMySystemConfiguration().getTimeResultsCached();
			maxLivedMoment = DateUtils.addHours(maxLivedMoment, -timeChachedFind);

			if (finder.getSearchMoment().before(maxLivedMoment))
				this.finderService.deleteExpiredFinder(finder);
		}

		result = new ModelAndView("finder/search");
		result.addObject("finder", finder);
		if (!finder.getResults().isEmpty())
			result.addObject("positions", finder.getResults());
		result.addObject("requestUri", "finder/hacker/search.do");
		return result;
	}
	
	// ancillary methods

		protected ModelAndView createEditModelAndView(final Finder finder) {
			ModelAndView result;

			result = this.createEditModelAndView(finder, null);

			return result;
		}

		protected ModelAndView createEditModelAndView(final Finder finder, final String messageCode) {
			ModelAndView result;
			final Collection<Position> positions;
			positions = finder.getResults();

			result = new ModelAndView("finder/search");
			result.addObject("message", messageCode);
			result.addObject("finder", finder);
			result.addObject("positions", positions);

			return result;
		}

	
	
}
