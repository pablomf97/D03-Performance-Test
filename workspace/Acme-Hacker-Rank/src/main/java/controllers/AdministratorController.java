package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import domain.Administrator;
import forms.RegisterFormObject;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	/* Services */

	@Autowired
	private AdministratorService administratorService;

	/* Methods */

	/**
	 * 
	 * Register administrator GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/administrator/register", method = RequestMethod.GET)
	public ModelAndView createAdmin() {
		ModelAndView res;

		RegisterFormObject registerFormObject = new RegisterFormObject();
		registerFormObject.setTermsAndConditions(false);

		res = this.createEditModelAndView(registerFormObject);

		return res;
	}

	/**
	 * 
	 * Register administrator POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/administrator/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid RegisterFormObject registerFormObject,
			BindingResult binding) {

		ModelAndView res;

		Administrator administrator = new Administrator();
		administrator = this.administratorService.create();

		administrator = this.administratorService.reconstruct(
				registerFormObject, binding);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(registerFormObject);
		} else {
			try {

				this.administratorService.save(administrator);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				res = this.createEditModelAndView(registerFormObject,
						"administrator.commit.error");

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

		result = new ModelAndView("administrator/register");
		result.addObject("registerFormObject", registerFormObject);
		result.addObject("message", messageCode);

		return result;
	}
}
