package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CurriculaRepository;
import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import domain.PersonalData;
import domain.PositionData;

@Transactional
@Service
public class CurriculaService {

	//Repository

	@Autowired
	private CurriculaRepository curriculaRepository;

	//Services

	@Autowired
	private ActorService actorService;
	
	@Autowired
	private PersonalDataService personalDataService;
	
	@Autowired
	private Validator validator;

	//Create
	public Curricula create(){
		Curricula result;
		Hacker principal;

		principal = (Hacker) this.actorService.findByPrincipal();
		Assert.isTrue(this.actorService.checkAuthority(principal, "HACKER"));

		result = new Curricula();
		result.setEducationData(new ArrayList<EducationData>());
		result.setPositionData(new ArrayList<PositionData>());
		result.setPersonalData(this.personalDataService.defaultData());
		result.setIsCopy(false);
		result.setHacker(principal);
		
		this.curriculaRepository.saveAndFlush(result);
		
		return result;
	}

	//Save
	public Curricula save(final Curricula curricula){
		Curricula result;
		Hacker principal;

		//Checking curricula owner
		principal = (Hacker) this.actorService.findByPrincipal();
		Assert.isTrue(curricula.getHacker().getId() == principal.getId());

		//Checking persistence
		if(curricula.getId()==0){
			Assert.notNull(curricula.getPersonalData());
		}else{
			Assert.notNull(curricula.getPersonalData());
			Assert.notEmpty(curricula.getEducationData());
			Assert.notEmpty(curricula.getPositionData());

		}

		result = this.curriculaRepository.save(curricula);

		return result;
	}

	public void delete(Curricula curricula){
		Hacker principal;

		//Checking curricula owner
		principal = (Hacker) this.actorService.findByPrincipal();
		Assert.isTrue(curricula.getHacker().getId() == principal.getId());
		
		this.curriculaRepository.delete(curricula);
		
	}

	//Finds
	public Curricula findOne(int curriculaId){

		Curricula result = this.curriculaRepository.findOne(curriculaId);

		return result;
	}

	public Collection<Curricula> findAll(){

		Collection<Curricula> result = this.curriculaRepository.findAll();

		return result;
	}
	
	public Collection<Curricula> getCurriculasByHacker(int hackerId){
		
		Collection<Curricula>result = this.curriculaRepository.getCurriculasByHacker(hackerId);
		
		return result;
	}
	
	public Curricula getCurriculaByMiscellaneousData(int dataId){
		Curricula result = this.curriculaRepository.getCurriculaByMiscellaneousData(dataId);
		
		return result;
	}
	
	public Curricula getCurriculaByEducationData(int dataId){
		Curricula result = this.curriculaRepository.getCurriculaByEducationData(dataId);
		
		return result;
	}
	
	public Curricula getCurriculaByPositionData(int dataId){
		Curricula result = this.curriculaRepository.getCurriculaByPositionData(dataId);
		
		return result;
	}
	
	public Curricula getCurriculaByPersonalData(int dataId){
		Curricula result = this.curriculaRepository.getCurriculaByPersonalData(dataId);
		
		return result;
	}
	

}
