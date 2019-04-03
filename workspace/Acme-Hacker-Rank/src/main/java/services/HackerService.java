package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HackerRepository;
import security.Authority;
import security.UserAccount;
import domain.CreditCard;
import domain.Finder;
import domain.Hacker;

@Transactional
@Service
public class HackerService {

	/* Working repository */

	@Autowired
	private HackerRepository hackerRepository;

	/* Services */

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	/* Simple CRUD methods */

	public Hacker create() {
		Hacker res;
		UserAccount userAccount;
		Authority auth;
		Collection<Authority> authority;
		CreditCard creditCard;
		Finder finder;

		creditCard = new CreditCard();
		auth = new Authority();
		authority = new ArrayList<Authority>();
		userAccount = new UserAccount();
		finder = new Finder();
		res = new Hacker();

		auth.setAuthority(Authority.HACKER);
		authority.add(auth);
		userAccount.setAuthorities(authority);

		res.setUserAccount(userAccount);
		res.setCreditCard(creditCard);
		res.setFinder(finder);

		return res;
	}

	public Hacker findOne(Integer hackerId) {
		Hacker res;

		Assert.notNull(hackerId);
		res = this.hackerRepository.findOne(hackerId);

		return res;
	}

	public List<Hacker> findAll() {

		return this.hackerRepository.findAll();
	}

	public Hacker save(Hacker hacker) {
		Hacker res;
		Assert.notNull(hacker);

		char[] phoneArray = hacker.getPhoneNumber().toCharArray();
		if ((!hacker.getPhoneNumber().equals(null) && !hacker.getPhoneNumber()
				.equals("")))
			if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
				String cc = this.systemConfigurationService
						.findMySystemConfiguration().getCountryCode();
				hacker.setPhoneNumber("+" + cc + " " + hacker.getPhoneNumber());
			}

		if (hacker.getId() == 0) {
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String encodedpass = encoder.encodePassword(hacker.getUserAccount()
					.getPassword(), null);
			hacker.getUserAccount().setPassword(encodedpass);
		}

		res = this.hackerRepository.save(hacker);
		return res;

	}

}
