package services;


import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


import utilities.AbstractTest;
import domain.Actor;




@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class DashboardServiceTest extends AbstractTest {
	
	
	
	@Autowired
	private ActorService actorService;
	
	@Autowired
	private FinderService finderService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private HackerService hackerService;

//	RF.11.6		The best position in terms of salary
//	RF.11.6		The worst position in terms of salary
//	RF.11.4		The hackers who have made more applications
//	RF.11.3		The companies that have offered more positions
//	RF.11.5		The average of the salaries offered
//	RF.11.5		The minimum of the salaries offered
//	RF.11.5		The maximum of the salaries offered
//	RF.11.5		The standard deviation of the salaries offered
//	RF.11.2		The average of the number of applications per hacker
//	RF.11.2		The minimum of the number of applications per hacker
//	RF.11.2		The maximum of the number of applications per hacker




	

//	RF.11.1		The maximum of the number of positions per company.
	@Test 
	public void maxPositionPerCompanyDriver() {
		Object testingData[][] = { { "admin", 3, null },// Positive
				{ "admin", 5, IllegalArgumentException.class },//non expected
																			
				{ "hacker1", 3, IllegalArgumentException.class } //non authorized actor
																	
		};

		for (int i = 0; i < testingData.length; i++) {
			maxPositionPerCompanyTemplate((String) testingData[i][0],
					(int) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void maxPositionPerCompanyTemplate(String username,Integer max,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.maxPositionPerCompanyTest(max);
				
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void maxPositionPerCompanyTest(Integer max) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res=this.positionService.maxPositionPerCompany();
		Assert.isTrue(res.intValue()==max);


	}
//	RF.11.1		The minimum of the number of positions per company.
	@Test
	public void minPositionPerCompanyDriver() {
		Object testingData[][] = { { "admin", 0, null },// Positive
				{ "admin", 5, IllegalArgumentException.class },//non expected
																			
				{ "hacker1", 0, IllegalArgumentException.class } //non authorized actor
																	
		};

		for (int i = 0; i < testingData.length; i++) {
			minPositionPerCompanyTemplate((String) testingData[i][0],
					(Integer) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void minPositionPerCompanyTemplate(String username,Integer min,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.minPositionPerCompanyTest(min);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void minPositionPerCompanyTest(Integer min) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Integer res =this.positionService.minPositionPerCompany();
		Assert.isTrue(res.intValue()==min);


	}
	
//	RF.11.1		The average of the number of positions per company.
	
	@Test
	public void avgPositionPerCompanyDriver() {
		Object testingData[][] = { { "admin", 1.66667, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected
																			
				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor
																	
		};

		for (int i = 0; i < testingData.length; i++) {
			avgPositionPerCompanyTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void avgPositionPerCompanyTemplate(String username,Double val,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.avgPositionPerCompanyTest(val);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void avgPositionPerCompanyTest(Double val) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.positionService.avgPositionPerCompany();
		Assert.isTrue(res.doubleValue()==val);


	}
	
//	RF.11.1		The standard deviation of the number of positions per company.
	
	@Test
	public void stddevPositionPerCompanyDriver() {
		Object testingData[][] = { { "admin", 1.24722, null },// Positive
				{ "admin", 5., IllegalArgumentException.class },//non expected
																			
				{ "hacker1", 0., IllegalArgumentException.class } //non authorized actor
																	
		};

		for (int i = 0; i < testingData.length; i++) {
			stddevPositionPerCompanyTemplate((String) testingData[i][0],
					(Double) testingData[i][1], (Class<?>) testingData[i][2]);
		}
	}

	protected void stddevPositionPerCompanyTemplate(String username,Double val,
			Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);

			this.stddevPositionPerCompanyTest(val);

			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}

		super.checkExceptions(expected, caught);
	}

	public void stddevPositionPerCompanyTest(Double val) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal,
				"ADMIN"));
		Double res =this.positionService.sttdevPositionPerCompany();
		Assert.isTrue(res.doubleValue()==val);


	}
	
//	RF.11.2		The standard deviation of the number of applications per hacker
	
	
	
	
	
	
	//LEVEL B
	
//	RF.18.2	Max results in the finders
//	RF.18.2	Min results in the finders
//	RF.18.2	Average results in the finders
//	RF.18.2	Standard deviation results in the finders
//	RF.18.3	The ratio of empty versus non-empty finders
//
//
//	RF.18.1	Average of the number of curricula per hacker
//	RF.18.1	Maximum of the number of curricula per hacker
//	RF.18.1	Minimum of the number of curricula per hacker
//	RF.18.1	Deviation standard of the number of curricula per hacker

}
