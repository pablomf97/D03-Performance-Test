package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CompanyService;
import domain.Company;
import forms.RegisterCompanyFormObject;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	/* Services */

	@Autowired
	private CompanyService companyService;

	/* Methods */

	/**
	 * 
	 * Register company GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/register", method = RequestMethod.GET)
	public ModelAndView createAdmin() {
		ModelAndView res;

		RegisterCompanyFormObject registerCompanyFormObject = new RegisterCompanyFormObject();
		registerCompanyFormObject.setTermsAndConditions(false);

		res = this.createEditModelAndView(registerCompanyFormObject);

		return res;
	}

	/**
	 * 
	 * Register company POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(
			@Valid RegisterCompanyFormObject registerCompanyFormObject,
			BindingResult binding) {

		ModelAndView res;

		Company company = new Company();
		company = this.companyService.create();

		company = this.companyService.reconstruct(registerCompanyFormObject,
				binding);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(registerCompanyFormObject);
		} else {
			try {

				this.companyService.save(company);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				res = this.createEditModelAndView(registerCompanyFormObject,
						"company.commit.error");

			}
		}
		return res;
	}

	/* Auxiliary methods */

	protected ModelAndView createEditModelAndView(
			RegisterCompanyFormObject registerCompanyFormObject) {
		ModelAndView result;

		result = this.createEditModelAndView(registerCompanyFormObject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			RegisterCompanyFormObject registerCompanyFormObject,
			String messageCode) {
		ModelAndView result;

		result = new ModelAndView("company/register");
		result.addObject("registerCompanyFormObject", registerCompanyFormObject);
		result.addObject("message", messageCode);

		return result;
	}

}
