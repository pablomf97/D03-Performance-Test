package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CompanyRepository;
import security.Authority;
import security.UserAccount;
import domain.Company;
import domain.CreditCard;

@Transactional
@Service
public class CompanyService {

	/* Working repository */

	@Autowired
	private CompanyRepository companyRepository;

	/* Services */

	@Autowired
	private SystemConfigurationService systemConfigurationService;

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
		Assert.notNull(company);

		char[] phoneArray = company.getPhoneNumber().toCharArray();
		if ((!company.getPhoneNumber().equals(null) && !company
				.getPhoneNumber().equals("")))
			if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
				String cc = this.systemConfigurationService
						.findMySystemConfiguration().getCountryCode();
				company.setPhoneNumber("+" + cc + " "
						+ company.getPhoneNumber());
			}

		if (company.getId() == 0) {
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String encodedpass = encoder.encodePassword(company
					.getUserAccount().getPassword(), null);
			company.getUserAccount().setPassword(encodedpass);
		}

		res = this.companyRepository.save(company);
		return res;

	}
}
