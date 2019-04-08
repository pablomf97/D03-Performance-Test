package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CompanyService;
import domain.Company;
import forms.EditionCompanyFormObject;
import forms.RegisterCompanyFormObject;

@Controller
@RequestMapping("/company")
public class CompanyController extends AbstractController {

	/* Services */

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ActorService actorService;

	/* Methods */

	/**
	 * 
	 * Display company
	 * 
	 * @params id (optional)
	 * 
	 * @return ModelAndView
	 * **/
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam(required = false) Integer id) {
		ModelAndView res;
		Company toDisplay;
		Boolean found = true;

		try {
			if (id != null) {
				toDisplay = (Company) this.actorService.findOne(id);
				if (toDisplay == null)
					found = false;
			} else {
				toDisplay = (Company) this.actorService.findByPrincipal();
			}

			res = new ModelAndView("company/display");
			res.addObject("company", toDisplay);
			res.addObject("found", found);
		} catch (Throwable oops) {
			found = false;
			res = new ModelAndView("company/display");
			res.addObject("found", found);
		}

		return res;
	}

	/**
	 * 
	 * Edit company GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/edit", method = RequestMethod.GET)
	public ModelAndView editCompany() {
		ModelAndView res;
		Company principal;

		principal = (Company) this.actorService.findByPrincipal();

		EditionCompanyFormObject editionCompanyFormObject = new EditionCompanyFormObject(
				principal);

		res = this.createEditModelAndView(editionCompanyFormObject);

		return res;
	}

	/**
	 * 
	 * Edit company POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(
			@Valid EditionCompanyFormObject editionCompanyFormObject,
			BindingResult binding) {

		ModelAndView res;

		Company company = new Company();
		company = this.companyService.create();

		company = this.companyService.reconstruct(editionCompanyFormObject,
				binding);

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(editionCompanyFormObject);
		} else {
			try {

				this.companyService.save(company);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				res = this.createEditModelAndView(editionCompanyFormObject,
						"company.commit.error");

			}
		}
		return res;
	}

	/**
	 * 
	 * Register company GET
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/register", method = RequestMethod.GET)
	public ModelAndView registerNewCompany() {
		ModelAndView res;

		RegisterCompanyFormObject registerCompanyFormObject = new RegisterCompanyFormObject();
		registerCompanyFormObject.setTermsAndConditions(false);

		res = this.createRegisterModelAndView(registerCompanyFormObject);

		return res;
	}

	/**
	 * 
	 * Register company POST
	 * 
	 * @return ModelAndView
	 **/
	@RequestMapping(value = "/company/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(
			@Valid RegisterCompanyFormObject registerCompanyFormObject,
			BindingResult binding) {

		ModelAndView res;

		Company company = new Company();
		company = this.companyService.create();

		company = this.companyService.reconstruct(registerCompanyFormObject,
				binding);

		if (binding.hasErrors()) {
			res = this.createRegisterModelAndView(registerCompanyFormObject);
		} else {
			try {

				this.companyService.save(company);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				res = this.createRegisterModelAndView(
						registerCompanyFormObject, "company.commit.error");

			}
		}
		return res;
	}

	/* Auxiliary methods */

	protected ModelAndView createEditModelAndView(
			EditionCompanyFormObject editionCompanyFormObject) {
		ModelAndView result;

		result = this.createEditModelAndView(editionCompanyFormObject, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			EditionCompanyFormObject editionCompanyFormObject,
			String messageCode) {
		ModelAndView result;

		result = new ModelAndView("company/edit");
		result.addObject("editionCompanyFormObject", editionCompanyFormObject);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(
			RegisterCompanyFormObject registerCompanyFormObject) {
		ModelAndView result;

		result = this.createRegisterModelAndView(registerCompanyFormObject,
				null);

		return result;
	}

	protected ModelAndView createRegisterModelAndView(
			RegisterCompanyFormObject registerCompanyFormObject,
			String messageCode) {
		ModelAndView result;

		result = new ModelAndView("company/register");
		result.addObject("registerCompanyFormObject", registerCompanyFormObject);
		result.addObject("message", messageCode);

		return result;
	}

}
