package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;

@Controller
@RequestMapping(value = "statistics/administrator")
public class DashboardAdministratorController extends AbstractController{
	
	// Display
	@Autowired
	private FinderService finderService;

		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display() {
			ModelAndView result;
			
			Double AvgCurriculaPerHacker=this.finderService.AvgCurriculaPerHacker();
			Double ratioFinders=this.finderService.ratioFinders();
			Long MaxCurriculaPerHacker=this.finderService.MaxCurriculaPerHacker();
			Double stdevCurriculaPerHacker =this.finderService.stdevCurriculaPerHacker();
			Double []statsFinder=this.finderService.StatsFinder();
			Long MinCurriculaPerHacker =this.finderService.MinCurriculaPerHacker();
			result = new ModelAndView("administrator/statistics");
			

			result.addObject("AvgCurriculaPerHacker",AvgCurriculaPerHacker);
			result.addObject("ratioFinders",ratioFinders);
			result.addObject("statsFinder",statsFinder);
			result.addObject("MinCurriculaPerHacker",MinCurriculaPerHacker);
			result.addObject("MaxCurriculaPerHacker",MaxCurriculaPerHacker);
			result.addObject("stdevCurriculaPerHacker",stdevCurriculaPerHacker);
			result.addObject("requestURI", "statistics/administrator/display.do");
			return result;
		}
}
