package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import utilities.AbstractTest;
import domain.Administrator;
import forms.RegisterFormObject;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	/*
	 * Total coverage of all tests
	 * 
	 * 
	 * Coverage of the total project (%):
	 * 
	 * 
	 * Coverage of the total project (lines of codes):
	 * 
	 * ################################################################
	 * 
	 * Total coverage by exclusively executing this test class
	 * 
	 * 
	 * Coverage of the total project (%):
	 * 
	 * 
	 * Coverage of the total project (lines of codes):
	 */

	// System under test ---------------------------------------
	@Autowired
	private Validator validator;

	@Autowired
	private AdministratorService administratorService;

	// Tests ----------------------------------------------------

	// Test: Caso de uso:
	// An actor who is not authenticated must be able to:
	// Register to the system as a administrator. (7.1)
	@Test
	public void driver() {
		Object testingData[][] = {
				/* Positive case */
				{ "admin", "adminT", "adminT", "adminT", "adminT",
						"ES12345678", "https://www.foto.com",
						"adminT@adminT.adminT", "666666666", "c/ adminT",
						"adminT", "VISA", "4111111111111111", 02, 22, 123, null },
				/* Negative: invalid admin */
				{ null, "adminT", "adminT", "adminT", "adminT", "ES12345678",
						"https://www.foto.com", "adminT@adminT.adminT",
						"666666666", "c/ adminT", "adminT", "VISA",
						"4111111111111111", 02, 22, 123,
						IllegalArgumentException.class },
				/* Negative cases: invalid data */
				{ "admin", "adminT", null, "adminT", "adminT", "ES12345678",
						"https://www.foto.com", "adminT@adminT.adminT",
						"666666666", "c/ adminT", "adminT", "VISA",
						"4111111111111111", 02, 22, 123,
						DataIntegrityViolationException.class },
				{ "admin", "adminT", "adminT", "adminT", "adminT", null,
						"https://www.foto.com", "adminT@adminT.adminT",
						"666666666", "c/ adminT", "adminT", "VISA",
						"4111111111111111", 02, 22, 123,
						DataIntegrityViolationException.class },
				{ "admin", "adminT", "adminT", "adminT", "adminT",
						"ES12345678", null, "adminT@adminT.adminT",
						"666666666", "c/ adminT", "adminT", "VISA",
						"4111111111111111", 02, 22, 123,
						DataIntegrityViolationException.class },
				{ "admin", "adminT", "adminT", "adminT", "adminT",
						"ES12345678", "https://www.foto.com", null,
						"666666666", "c/ adminT", "adminT", "VISA",
						"4111111111111111", 02, 22, 123,
						DataIntegrityViolationException.class },
				{ "admin", "adminT", "adminT", "adminT", "adminT",
						"ES12345678", "https://www.foto.com",
						"adminT@adminT.adminT", "666666666", "c/ adminT", null,
						null, null, null, null, null,
						DataIntegrityViolationException.class } };

		for (int i = 0; i < testingData.length; i++) {
			template((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (String) testingData[i][3],
					(String) testingData[i][4], (String) testingData[i][5],
					(String) testingData[i][6], (String) testingData[i][7],
					(String) testingData[i][8], (String) testingData[i][9],
					(String) testingData[i][10], (String) testingData[i][11],
					(String) testingData[i][12], (Integer) testingData[i][13],
					(Integer) testingData[i][14], (Integer) testingData[i][15],
					(Class<?>) testingData[i][16]);
		}
	}

	protected void template(String creatorUsername, String username,
			String password, String name, String surname, String VAT,
			String photo, String email, String phoneNumber, String address,
			String holder, String make, String number, Integer expirationMonth,
			Integer expirationYear, Integer CVV, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(creatorUsername);

			this.registerAdministrator(username, password, name, surname, VAT,
					photo, email, phoneNumber, address, holder, make, number,
					expirationMonth, expirationYear, CVV);

			unauthenticate();
		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void registerAdministrator(String username, String password,
			String name, String surname, String VAT, String photo,
			String email, String phoneNumber, String address, String holder,
			String make, String number, Integer expirationMonth,
			Integer expirationYear, Integer CVV) {

		RegisterFormObject adminForm = new RegisterFormObject();
		Administrator newAdmin = new Administrator();
		BindingResult binding = null;

		adminForm.setUsername(username);
		adminForm.setPassword(password);
		adminForm.setPassConfirmation(password);
		adminForm.setTermsAndConditions(true);
		adminForm.setName(name);
		adminForm.setSurname(surname);
		adminForm.setVAT(VAT);
		adminForm.setPhoto(photo);
		adminForm.setEmail(email);
		adminForm.setPhoneNumber(phoneNumber);
		adminForm.setAddress(address);
		adminForm.setHolder(holder);
		adminForm.setMake(make);
		adminForm.setNumber(number);
		adminForm.setExpirationMonth(expirationMonth);
		adminForm.setExpirationYear(expirationYear);
		adminForm.setCVV(CVV);

		newAdmin = this.administratorService.reconstruct(adminForm, binding);

		this.validator.validate(newAdmin, binding);
		this.administratorService.save(newAdmin);

	}

}