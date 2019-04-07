package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.HackerService;
import domain.Hacker;
import forms.RegisterFormObject;

@Controller
@RequestMapping("/hacker")
public class HackerController extends AbstractController {

	/* Services */

	@Autowired
	private HackerService hackerService;

	/* Methods */

	/**
	 * 
	 * Register hacker GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/register", method = RequestMethod.GET)
	public ModelAndView createAdmin() {
		ModelAndView res;

		RegisterFormObject registerFormObject = new RegisterFormObject();
		registerFormObject.setTermsAndConditions(false);

		res = this.createEditModelAndView(registerFormObject);

		return res;
	}

	/**
	 * 
	 * Register hacker POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/hacker/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RegisterFormObject registerFormObject,
			BindingResult binding) {

		ModelAndView res;

		Hacker hacker = new Hacker();
		hacker = this.hackerService.create();

		hacker = this.hackerService.reconstruct(registerFormObject, binding);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(registerFormObject);
		} else {
			try {

				this.hackerService.save(hacker);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				res = this.createEditModelAndView(registerFormObject,
						"hacker.commit.error");

			}
		}
		return res;
	}

	/* Auxiliary methods */

	protected ModelAndView createEditModelAndView(
			RegisterFormObject registerFormObject) {
		ModelAndView result;

		result = this.createEditModelAndView(registerFormObject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			RegisterFormObject registerFormObject, String messageCode) {
		ModelAndView result;

		result = new ModelAndView("hacker/register");
		result.addObject("registerFormObject", registerFormObject);
		result.addObject("message", messageCode);

		return result;
	}

}
