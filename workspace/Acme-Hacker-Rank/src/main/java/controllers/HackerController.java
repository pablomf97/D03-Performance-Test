package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.HackerService;
import domain.Hacker;
import forms.EditionFormObject;
import forms.RegisterFormObject;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	/* Services */

	@Autowired
	private HackerService hackerService;

	@Autowired
	private ActorService actorService;

	/* Methods */

	/**
	 * 
	 * Edit hacker GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/edit", method = RequestMethod.GET)
	public ModelAndView editHacker() {
		ModelAndView res;
		Hacker principal;

		principal = (Hacker) this.actorService.findByPrincipal();
		EditionFormObject editionFormObject = new EditionFormObject(principal);

		res = this.createEditModelAndView(editionFormObject);

		return res;
	}

	/**
	 * 
	 * Edit hacker POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid EditionFormObject editionFormObject,
			BindingResult binding) {

		ModelAndView res;

		Hacker hacker = new Hacker();
		hacker = this.hackerService.create();

		hacker = this.hackerService.reconstruct(editionFormObject, binding);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(editionFormObject);
		} else {
			try {

				this.hackerService.save(hacker);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				res = this.createEditModelAndView(editionFormObject,
						"hacker.commit.error");

			}
		}
		return res;
	}

	/**
	 * 
	 * Register hacker GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/register", method = RequestMethod.GET)
	public ModelAndView registerNewHacker() {
		ModelAndView res;

		RegisterFormObject registerFormObject = new RegisterFormObject();
		registerFormObject.setTermsAndConditions(false);

		res = this.createRegisterModelAndView(registerFormObject);

		return res;
	}

	/**
	 * 
	 * Register hacker POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid RegisterFormObject registerFormObject,
			BindingResult binding) {

		ModelAndView res;

		Hacker hacker = new Hacker();
		hacker = this.hackerService.create();

		hacker = this.hackerService.reconstruct(registerFormObject, binding);

		if (binding.hasErrors()) {
			res = this.createRegisterModelAndView(registerFormObject);
		} else {
			try {

				this.hackerService.save(hacker);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				res = this.createRegisterModelAndView(registerFormObject,
						"hacker.commit.error");

			}
		}
		return res;
	}

	/* Auxiliary methods */

	protected ModelAndView createEditModelAndView(
			EditionFormObject editionFormObject) {
		ModelAndView result;

		result = this.createEditModelAndView(editionFormObject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			EditionFormObject editionFormObject, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("hacker/edit");
		result.addObject("editionFormObject", editionFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(
			RegisterFormObject registerFormObject) {
		ModelAndView result;

		result = this.createRegisterModelAndView(registerFormObject, null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(
			RegisterFormObject registerFormObject, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("hacker/register");
		result.addObject("registerFormObject", registerFormObject);
		result.addObject("message", messageCode);

		return result;
	}

}
