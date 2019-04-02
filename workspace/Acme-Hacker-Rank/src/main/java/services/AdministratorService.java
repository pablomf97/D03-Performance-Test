package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Administrator;
import domain.CreditCard;

@Transactional
@Service
public class AdministratorService {

	/* Working repository */

	@Autowired
	private AdministratorRepository administratorRepository;

	/* Simple CRUD methods */

	public Administrator create() {
		Administrator res;
		UserAccount userAccount;
		Authority auth;
		Collection<Authority> authority;
		CreditCard creditCard;

		creditCard = new CreditCard();
		auth = new Authority();
		authority = new ArrayList<Authority>();
		userAccount = new UserAccount();
		res = new Administrator();

		auth.setAuthority(Authority.ADMINISTRATOR);
		authority.add(auth);
		userAccount.setAuthorities(authority);

		res.setUserAccount(userAccount);
		res.setCreditCard(creditCard);

		return res;
	}

	public Administrator findOne(Integer administratorId) {
		Administrator res;

		Assert.notNull(administratorId);
		res = this.administratorRepository.findOne(administratorId);

		return res;
	}

	public List<Administrator> findAll() {

		return this.administratorRepository.findAll();
	}
}
