package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


import javax.transaction.Transactional;


import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Curricula;
import domain.Finder;
import domain.Hacker;
import domain.Position;

import repositories.CurriculaRepository;
import repositories.FinderRepository;
import repositories.HackerRepository;
import repositories.PositionRepository;

@Transactional
@Service
public class FinderService {

	// Managed repository ------------------------------
	@Autowired
	private FinderRepository finderRepository;

	// Supporting services -----------------------
	@Autowired
	private ActorService actorService;

	@Autowired
	private PositionRepository positionService;

	@Autowired
	private CurriculaRepository curriculaRepository;
	
	@Autowired
	private SystemConfigurationService systemConfigurationService;
	
	@Autowired
	private HackerRepository hackerRepository;

	// Constructors
	public FinderService() {
		super();
	}

	public Finder create(){
		Finder result;
		Actor principal;


		principal = this.actorService.findByPrincipal();
		Assert.isTrue(
				this.actorService.checkAuthority(principal, "HACKER"),
				"not.allowed");
		result=new Finder();
		result.setResults(new ArrayList<Position>());
		return result;
	}

	// /FINDONE
	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);


		return result;
	}

	// FINDALL
	public Collection<Finder> findAll() {
		Collection<Finder> result;
		result = this.finderRepository.findAll();


		return result;

	}
	public Finder save(Finder finder){
		Finder result;

		Hacker principal;


		principal = (Hacker)this.actorService.findByPrincipal();
		Assert.isTrue(
				this.actorService.checkAuthority(principal, "HACKER"),
				"not.allowed");
		Assert.isTrue(principal.getFinder().equals(finder),"not.allowed");
		Assert.notNull(finder, "not.allowed");
		if(finder.getMinimumSalary()!=null){
			Assert.isTrue(finder.getMinimumSalary() >=0.,"not.negative");
		}

		result = this.finderRepository.save(finder);
		Assert.notNull(result, "not.null");

		return result;
	}
	//DELETE 

	public void delete(Finder finder){
		Hacker principal;


		principal = (Hacker)this.actorService.findByPrincipal();
		Assert.isTrue(
				this.actorService.checkAuthority(principal, "HACKER"),
				"not.allowed");
		Assert.isTrue(finder.getId()!=0);
		Assert.isTrue(principal.getFinder().equals(finder),"not.allowed");
		finder.setResults(null);
		finder.setKeyWord(null);
		finder.setMaximumDeadline(null);
		finder.setMinimumSalary(null);
		finder.setSearchMoment(null);//Watch out!
		finder.setDeadline(null);

		this.finderRepository.save(finder);
	}

	//Ancillary methods

	public void deleteExpiredFinder(Finder finder){
		Date maxLivedMoment = new Date();
		int timeChachedFind;
		Date currentMoment;
		currentMoment = new Date(System.currentTimeMillis() - 1);

		timeChachedFind = this.systemConfigurationService
				.findMySystemConfiguration().getTimeResultsCached();
		maxLivedMoment = DateUtils.addHours(currentMoment, -timeChachedFind);
		if (finder.getSearchMoment().before(maxLivedMoment)) {

			finder.setResults(null);
			finder.setKeyWord(null);
			finder.setMaximumDeadline(null);
			finder.setMinimumSalary(null);
			finder.setSearchMoment(null);//Watch out!
			finder.setDeadline(null);

			this.finderRepository.save(finder);


		}
	}

	public Collection<Position> search(Finder finder){

		Collection<Position> results=new ArrayList<Position>();
		String keyWord;
		Double minimumSalary;
	
		Date maximumDeadline;
		int nResults;

		Collection<Position> resultsPageables = new ArrayList<Position>();

		nResults = this.systemConfigurationService.findMySystemConfiguration()
				.getMaxResults();
		keyWord = (finder.getKeyWord() == null || finder.getKeyWord().isEmpty()) ? ""
				: finder.getKeyWord();

		minimumSalary=(finder.getMinimumSalary() == null ) ? 0
				: finder.getMinimumSalary();

		final Date maxDefaultDate = new GregorianCalendar(2200, Calendar.JANUARY, 1)
		.getTime();


		maximumDeadline= finder.getMaximumDeadline() == null ? maxDefaultDate : finder
				.getMaximumDeadline();
		
		if(finder.getDeadline()==null&&finder.getKeyWord().isEmpty()&&finder.getMinimumSalary()==null&&finder.getMaximumDeadline()==null){
			results=this.positionService.findAll();
		}else{
			results=this.finderRepository.search(minimumSalary,maximumDeadline,keyWord);
			if(finder.getDeadline()!=null){

				List<Position> resultsDeadline=new ArrayList<Position>();
				resultsDeadline.add(this.finderRepository.searchDeadline(finder.getDeadline()));
				for (Position i :resultsDeadline){
					if(!results.contains(i)){
						results.add(i);
					}
				}

			}
		}

		int count=0;

		for(Position p : results){
			resultsPageables.add(p);
			count++;
			if(count>=nResults){
				break;
			}
		}
		finder.setResults(resultsPageables);

		this.save(finder);


		return resultsPageables;


	}
	public Double ratioFinders() {
		Double emptys = this.FindersEmpty();
		Double all = (double) this.findAll().size();
		Double res;

		res = (emptys / all);

		return res;

	}
	public Double FindersEmpty() {
		Double res;
		res = (double) this.finderRepository.FindersEmpty().size();
		return res;
	}
	
	public Collection<Integer> numberCurriculaPerHacker(){
		return this.finderRepository.numberCurriculaPerHacker();
	}
	
	public Integer MaxCurriculaPerHacker(){
		
		return  Collections.max(this.numberCurriculaPerHacker());
	}
	public Integer MinCurriculaPerHacker(){
		
		return  Collections.min(this.numberCurriculaPerHacker());
	}

	public Double AvgCurriculaPerHacker(){
		List<Integer> cvsPerHacker=(List<Integer>) this.numberCurriculaPerHacker();
		int total=0;
		double avg=0.;
		for(int i = 0; i < cvsPerHacker.size(); i++){
			total += cvsPerHacker.get(i);
		}
		 avg = total / cvsPerHacker.size();
		 return avg;
	}

}


