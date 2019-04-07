package services;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import repositories.CompanyRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Company;
import domain.CreditCard;
import forms.RegisterCompanyFormObject;

@Transactional
@Service
public class CompanyService {

	/* Working repository */

	@Autowired
	private CompanyRepository companyRepository;

	/* Services */

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private CreditCardService creditCardService;

	/* Simple CRUD methods */

	public Company create() {
		Company res;
		UserAccount userAccount;
		Authority auth;
		Collection<Authority> authority;
		CreditCard creditCard;

		creditCard = new CreditCard();
		auth = new Authority();
		authority = new ArrayList<Authority>();
		userAccount = new UserAccount();
		res = new Company();

		auth.setAuthority(Authority.COMPANY);
		authority.add(auth);
		userAccount.setAuthorities(authority);

		res.setUserAccount(userAccount);
		res.setCreditCard(creditCard);

		return res;
	}

	public Company findOne(Integer companyId) {
		Company res;

		Assert.notNull(companyId);
		res = this.companyRepository.findOne(companyId);

		return res;
	}

	public List<Company> findAll() {

		return this.companyRepository.findAll();
	}

	public Company save(Company company) {
		Company res;
		Actor principal;

		Assert.notNull(company);

		if (company.getId() == 0) {

			/* Managing phone number */
			char[] phoneArray = company.getPhoneNumber().toCharArray();
			if ((!company.getPhoneNumber().equals(null) && !company
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					company.setPhoneNumber(cc + " " + company.getPhoneNumber());
				}
			}

			/* Managing email */
			// String email = administrator.getEmail();
			// Assert.isTrue(
			// this.actorService.checkEmail(email, principal
			// .getUserAccount().getAuthorities().iterator()
			// .next().toString()), "actor.email.error");

			/* Managing photo */
			Assert.isTrue(ResourceUtils.isUrl(company.getPhoto()),
					"actor.photo.error");
		} else {
			principal = this.actorService.findByPrincipal();

			Assert.isTrue(principal.getId() == company.getId(), "no.permission");

			/* Managing phone number */
			char[] phoneArray = company.getPhoneNumber().toCharArray();
			if ((!company.getPhoneNumber().equals(null) && !company
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					company.setPhoneNumber(cc + " " + company.getPhoneNumber());
				}
			}

			/* Managing email */
			String email = company.getEmail();
			Assert.isTrue(
					this.actorService.checkEmail(email, principal
							.getUserAccount().getAuthorities().iterator()
							.next().toString()), "actor.email.error");

			/* Managing photo */
			Assert.isTrue(ResourceUtils.isUrl(company.getPhoto()),
					"actor.photo.error");
		}

		res = this.companyRepository.save(company);
		return res;
	}

	/**
	 * Reconstruct an company from a register object form from the database
	 * 
	 * @param RegisterFormObject
	 * 
	 * @return Company
	 */
	public Company reconstruct(RegisterCompanyFormObject form,
			BindingResult binding) {

		/* Creating company */
		Company res = this.create();

		res.setName(form.getName());
		res.setSurname(form.getSurname());
		res.setVAT(form.getVAT());
		res.setPhoto(form.getPhoto());
		res.setEmail(form.getEmail());
		res.setPhoneNumber(form.getPhoneNumber());
		res.setAddress(form.getAddress());
		res.setCommercialName(form.getCommercialName());

		/* Creating credit card */
		CreditCard creditCard = new CreditCard();

		creditCard.setHolder(form.getHolder());
		creditCard.setMake(form.getMake());
		creditCard.setNumber(form.getNumber());
		creditCard.setExpirationMonth(form.getExpirationMonth());
		creditCard.setExpirationYear(form.getExpirationYear());
		creditCard.setCVV(form.getCVV());

		res.setCreditCard(creditCard);

		/* Creating user account */
		UserAccount userAccount = new UserAccount();

		List<Authority> authorities = new ArrayList<Authority>();
		Authority authority = new Authority();
		authority.setAuthority(Authority.ADMIN);
		authorities.add(authority);
		userAccount.setAuthorities(authorities);

		userAccount.setUsername(form.getUsername());

		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();
		userAccount
				.setPassword(encoder.encodePassword(form.getPassword(), null));

		res.setUserAccount(userAccount);

		/* VAT */
		if (form.getVAT() != null) {
			try {

				Assert.isTrue(form.getVAT() < 1. && form.getVAT() > 0,
						"VAT.error");
			} catch (Throwable oops) {
				binding.addError(new FieldError("registerObjectForm", "VAT",
						form.getPassword(), false, null, null, "VAT.error"));
			}
		}

		/* Password confirmation */
		if (form.getPassword() != null) {
			try {

				Assert.isTrue(
						form.getPassword().equals(form.getPassConfirmation()),
						"pass.confirm.error");
			} catch (Throwable oops) {
				binding.addError(new FieldError("registerObjectForm",
						"password", form.getPassword(), false, null, null,
						"pass.confirm.error"));
			}
		}

		/* Terms&Conditions */
		if (form.getTermsAndConditions() != null) {
			try {
				Assert.isTrue((form.getTermsAndConditions()), "terms.error");
			} catch (Throwable oops) {
				binding.addError(new FieldError("registerObjectForm",
						"termsAndConditions", form.getTermsAndConditions(),
						false, null, null, "terms.error"));
			}
		}

		/* Credit card */
		if (form.getNumber() != null) {
			try {
				Assert.isTrue(this.creditCardService
						.checkCreditCardNumber(creditCard.getNumber()),
						"card.number.error");
			} catch (Throwable oops) {
				binding.addError(new FieldError("registerObjectForm", "number",
						form.getNumber(), false, null, null,
						"card.number.error"));
			}
		}

		if (creditCard.getExpirationMonth() != null
				&& creditCard.getExpirationYear() != null) {

			try {
				Assert.isTrue(
						!this.creditCardService.checkIfExpired(
								creditCard.getExpirationMonth(),
								creditCard.getExpirationYear()),
						"card.date.error");
			} catch (ParseException pe) {
				binding.addError(new FieldError("registerObjectForm", "number",
						form.getExpirationMonth(), false, null, null,
						"card.date.error"));
			}

			if (form.getCVV() != null) {
				try {
					Assert.isTrue(form.getCVV() < 999 && form.getCVV() > 100,
							"CVV.error");
				} catch (Throwable oops) {
					binding.addError(new FieldError("registerObjectForm",
							"CVV", form.getCVV(), false, null, null,
							"CVV.error"));
				}
			}
		}

		return res;
	}
}
