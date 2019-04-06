package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.CreditCard;

@Transactional
@Service
public class AdministratorService {

	/* Working repository */

	@Autowired
	private AdministratorRepository administratorRepository;

	/* Services */

	@Autowired
	private SystemConfigurationService systemConfigurationService;

	@Autowired
	private ActorService actorService;

	/* Simple CRUD methods */

	/**
	 * Create an administrator
	 * 
	 * @return Administrator
	 */
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

		auth.setAuthority(Authority.ADMIN);
		authority.add(auth);
		userAccount.setAuthorities(authority);

		res.setUserAccount(userAccount);
		res.setCreditCard(creditCard);

		return res;
	}

	/**
	 * Find an administrator on the database
	 * 
	 * @param administratorId
	 * 
	 * @return Administrator
	 */
	public Administrator findOne(Integer administratorId) {
		Administrator res;

		Assert.notNull(administratorId);
		res = this.administratorRepository.findOne(administratorId);

		return res;
	}

	/**
	 * Find all administrators
	 * 
	 * @return Collection<Administrator>
	 */
	public List<Administrator> findAll() {

		return this.administratorRepository.findAll();
	}

	/**
	 * Save an administrator
	 * 
	 * @param Administator
	 * 
	 * @return Administrator
	 */
	public Administrator save(Administrator administrator) {
		Administrator res;
		Actor principal;

		Assert.notNull(administrator);

		principal = this.actorService.findByPrincipal();

		if (administrator.getId() == 0) {

			Assert.isTrue(this.actorService.checkAuthority(principal,
					"ADMINISTRATOR"), "no.permission");

			/* Managing password */
			Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			String encodedpass = encoder.encodePassword(administrator
					.getUserAccount().getPassword(), null);
			administrator.getUserAccount().setPassword(encodedpass);

			/* Managing phone number */
			char[] phoneArray = administrator.getPhoneNumber().toCharArray();
			if ((!administrator.getPhoneNumber().equals(null) && !administrator
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					administrator.setPhoneNumber("+" + cc + " "
							+ administrator.getPhoneNumber());
				}
			}

			/* Managing email */
			String email = administrator.getEmail();
			Assert.isTrue(
					this.actorService.checkEmail(email, principal
							.getUserAccount().getAuthorities().iterator()
							.next().toString()), "actor.email.error");

			/* Managing photo */
			Assert.isTrue(ResourceUtils.isUrl(administrator.getPhoto()),
					"actor.photo.error");
		} else {

			Assert.isTrue(principal.getId() == administrator.getId(),
					"no.permission");

			/* Managing phone number */
			char[] phoneArray = administrator.getPhoneNumber().toCharArray();
			if ((!administrator.getPhoneNumber().equals(null) && !administrator
					.getPhoneNumber().equals(""))) {
				if (phoneArray[0] != '+' && Character.isDigit(phoneArray[0])) {
					String cc = this.systemConfigurationService
							.findMySystemConfiguration().getCountryCode();
					administrator.setPhoneNumber("+" + cc + " "
							+ administrator.getPhoneNumber());
				}
			}

			/* Managing email */
			String email = administrator.getEmail();
			Assert.isTrue(
					this.actorService.checkEmail(email, principal
							.getUserAccount().getAuthorities().iterator()
							.next().toString()), "actor.email.error");

			/* Managing photo */
			Assert.isTrue(ResourceUtils.isUrl(administrator.getPhoto()),
					"actor.photo.error");
		}

		res = this.administratorRepository.save(administrator);
		return res;

	}

	/**
	 * Delete an administrator
	 * 
	 * @param Administator
	 */
	public void delete(Administrator administrator) {
		Actor principal;

		Assert.notNull(administrator);

		principal = this.actorService.findByPrincipal();

		Assert.isTrue(principal.getId() == administrator.getId(),
				"no.permission");

		this.administratorRepository.delete(administrator.getId());
	}

	/* Other methods */

	/**
	 * Reconstruct an administrator from the database
	 * 
	 * @param Administator
	 * 
	 * @return Administrator
	 */
	public Administrator reconstruct(Administrator administrator) {
		Actor principal;
		Administrator res = this.create();

		Assert.notNull(administrator);

		principal = this.actorService.findByPrincipal();

		if (administrator.getId() == 0) {

			Assert.isTrue(this.actorService.checkAuthority(principal,
					"ADMINISTRATOR"), "no.permission");

			res = administrator;

		} else {

			Assert.isTrue(principal.getId() == administrator.getId(),
					"no.permission");

			/* Setting new values */
			res.setId(administrator.getId());
			res.setName(administrator.getName());
			res.setSurname(administrator.getSurname());
			res.setVAT(administrator.getVAT());
			res.setPhoto(administrator.getPhoto());
			res.setEmail(administrator.getEmail());
			res.setPhoneNumber(administrator.getPhoneNumber());
			res.setAddress(administrator.getAddress());
			res.setCreditCard(administrator.getCreditCard());

		}

		return res;
	}
}
