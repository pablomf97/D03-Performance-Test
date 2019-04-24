package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Application;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ApplicationServiceTest extends AbstractTest{
	
	@Autowired
	private ApplicationService applicationService;
	
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

	/*
	 * 
	 * During this test we are going to try:
	 * 
	 * (RF9.3) Manage the applications to their positions, which includes listing them grouped by status, showing them, and updating them.
	 * 
	 * (RF10.1) Manage his or her applications, which includes listing them grouped by status, showing them, creating them, and updating them.
	 */
	
	/*
	 * ####################### TEST CREATE LEGAL RECORDS #######################
	 */

	@Test
	public void driverCreateApplication() {
		Object testingData[][] = {

		/* 1. Attribute 'hacker' Testing */

		/* Test 1.1 ----------------------------------------------- */
		{ "hacker1", "position1_c1",  null
		/*
		 * 
		 * There is no error expected here, a hacker creates an application with 
		 * valid data
		 */
		},

		/* Test 1.2 ----------------------------------------------- */
		{ "company1", "position1_c1", ConstraintViolationException.class
		/*
		 * 
		 * Expected a Constraint Violation Exception because
		 * a company is trying to create an application
		 */
		},
		
		/* Test 1.3 ----------------------------------------------- */
		{ "", "position1_c1", IllegalArgumentException.class
		/*
		 * 
		 * Expected a Illegal Argument Exception because
		 * the atribute hacker is empty
		 */
		},

		/* 2. Attribute 'position' Testing */
		/* Test 2.1 ----------------------------------------------- */
		{ "hacker1", "position2_c1",  null
		/*
		 * 
		 * There is no error expected here, a hacker creates an application with 
		 * valid data
		 */
		},

		/* Test 2.2 ----------------------------------------------- */
		{ "hacker1", "position3_c1", ConstraintViolationException.class
		/*
		 * 
		 * Expected a Illegal Argument Exception because
		 * the position is set to draft
		 */
		},
		/* Test 2.3 ----------------------------------------------- */
		{ "hacker1", "", ConstraintViolationException.class
		/*
		 * 
		 * Expected a Illegal Argument Exception because
		 * the position is not specified
		 */
		},
		

		};

		for (int i = 0; i < testingData.length; i++) {
			templateCreateApplication((String) testingData[i][0],
					(int) super.getEntityId((String) testingData[i][1]),
					(Class<?>) testingData[i][3]);}
		}
		

	protected void templateCreateApplication(String username, int positionId,
		Class<?> expected) {

		Class<?> caught = null;

		try {
			this.authenticate(username);
			Application application = this.applicationService
					.create();

			this.applicationService.save(application);

			this.applicationService.flush();

			this.unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
	}

}
