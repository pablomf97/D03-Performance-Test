package services;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PositionRepository;

@Transactional
@Service
public class PositionService {

	@Autowired
	PositionRepository positionRepository;



	public Double minSalarayPositions(){
		return this.positionRepository.minSalarayPositions();

	}
	public Double maxSalaryPositions(){
		return this.positionRepository.maxSalaryPositions();

	}
	public Double AVGSalaryPositions(){
		return this.positionRepository.AVGSalaryPositions();
	}
	public Double STDDEVSalaryPositions(){
		return this.positionRepository.STDDEVSalaryPositions();
	}

	public String bestPositionSalary(){
		String res=this.positionRepository.bestPositionSalary();
		if(res==null){
			res="";
		}
		return res;
	}
	public String worstPositionSalary(){
		String res=this.positionRepository.worstPositionSalary();
		if(res==null){
			res="";
		}
		return res;
	}
	public String  companyWithMorePositions(){
		String res=this.positionRepository.companyWithMorePositions();
		if(res==null){
			res="";
		}
		return res;
	}

	public Integer maxPositionPerCompany(){
	
		return  this.positionRepository.maxPositionPerCompany();
	}

	public Integer minPositionPerCompany(){
		return this.positionRepository.minPositionPerCompany();
		}

	public Double avgPositionPerCompany(){
	
		return this.positionRepository.avgPositionPerCompany();
	}
	public Double sttdevPositionPerCompany(){
	
		return this.positionRepository.stddevPositionPerCompany();
	}



}
