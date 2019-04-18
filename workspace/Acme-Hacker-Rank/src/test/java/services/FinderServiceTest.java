package services;



import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.transaction.Transactional;


import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;


import domain.Finder;
import domain.Hacker;

import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FinderServiceTest extends AbstractTest{
	
	@Autowired
	private ActorService actorService;
	
	@Autowired 
	private FinderService finderService;
	
	
	@Test
	public void searchDriver(){
		Object testingData[][]={
				{"hacker1",3,"position",null,null,null,null},//positive
				{null,3,"position",null,null,null,IllegalArgumentException.class},//negative: 
				//{"admin",3,"position",null,null,null,ClassCastException.class},//negative: Admin 
			//	{"hacker1",1,null,null,100.0,null,null},
			//	{"hacker2",3,null,null,null,null,null},
				{"hacker1",3,null,null,null,new GregorianCalendar(2050, Calendar.JANUARY, 1)
				.getTime(),null},
				//{},
				
		};
		
		for (int i = 0; i < testingData.length; i++) {
			searchTemplate((String) testingData[i][0], 
					(int) testingData[i][1], (String) testingData[i][2],
					(Date) testingData[i][3],
					(Double) testingData[i][4],(Date) testingData[i][5],(Class<?>) testingData[i][6]);
		}
	}


	private void searchTemplate(String username,int results, String keyWord,
			Date deadline, Double minimumSalary, Date maximumDeadline, Class<?> expected) {
		Class<?> caught;
		
		caught=null;
		 
		try{
			authenticate(username);
			final Hacker principal = (Hacker)this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal,
					"HACKER"));
			
			Finder finder=principal.getFinder();
			finder.setDeadline(deadline);
			finder.setKeyWord(keyWord);
			finder.setMaximumDeadline(maximumDeadline);
			finder.setMinimumSalary(minimumSalary);
			this.finderService.save(finder);
			
			this.finderService.search(finder);
			
			Assert.isTrue(finder.getResults().size()==results);
			
			unauthenticate();
			
		}catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
		
	}
	
	@Test
	public void deleteCacheFinderDrive(){
		
		Date date = DateUtils.addHours(new Date(), -2);
		
		Object testingData[][]={
				
				{"hacker1", new Date(),null},
				{"hacker1", date,IllegalArgumentException.class},
			
		};
		
		for (int i = 0; i < testingData.length; i++) {
			deleteCacheFinderTemplate((String) testingData[i][0], 
					(Date) testingData[i][1], (Class<?>) testingData[i][2]);
		}
		
	}


	private void deleteCacheFinderTemplate(String username, Date date,
			Class<?> expected) {
		
		Class<?> caught;
		
		caught=null;
		 
		try{
			authenticate(username);
			final Hacker principal = (Hacker)this.actorService.findByPrincipal();
			Assert.isTrue(this.actorService.checkAuthority(principal,
					"HACKER"));
			Finder finder=principal.getFinder();
			finder.setSearchMoment(date);
			this.finderService.deleteExpiredFinder(finder);
			
			Assert.isTrue(finder.getSearchMoment()!=null);
		
			
			unauthenticate();
			
		}catch (Throwable oops) {
			caught = oops.getClass();
		}
		super.checkExceptions(expected, caught);
		
	}
	
	
	
}
