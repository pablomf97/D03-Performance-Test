package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Actor;

import utilities.AbstractTest;


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
//	RF.11.2		The standard deviation of the number of applications per hacker
//	RF.11.1		The average of the number of positions per company.
//	RF.11.1		The minimum of the number of positions per company.
//	RF.11.1		The maximum of the number of positions per company.
//	RF.11.1		The standard deviation of the number of positions per company.
	

//	RF.11.1		The maximum of the number of positions per company.
	@Test
	public void maxPositionPerCompanyDriver() {
		Object testingData[][] = { { "admin", 3, null },// Positive
				{ "admin", 5, IllegalArgumentException.class },//non expected
																			
				{ "hacker1", 3, IllegalArgumentException.class } //non authorized actor
																	
		};

		for (int i = 0; i < testingData.length; i++) {
			maxPositionPerCompanyTemplate((String) testingData[i][0],
					(Integer) testingData[i][1], (Class<?>) testingData[i][2]);
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

		Assert.isTrue(this.positionService.maxPositionPerCompany().equals(max));


	}
//	RF.11.1		The minimum of the number of positions per company.

	
	
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
