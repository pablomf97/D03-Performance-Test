package services;

import java.util.Collections;
import java.util.List;

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
	public List<Long >positionsPerCompany(){

		return this.positionRepository.positionsPerCompany();
	}
	public Long maxPositionPerCompany(){
		return Collections.max(this.positionRepository.positionsPerCompany());
	}

	public Long minPositionPerCompany(){
		return Collections.min(this.positionRepository.positionsPerCompany());
	}

	public Double avgPositionPerCompany(){
		int total=0;
		double avg=0.;
		for(int i = 0; i < this.positionsPerCompany().size(); i++){
			total += this.positionsPerCompany().get(i);
		}
		avg = (total / (double)this.positionsPerCompany().size());
		return avg;
	}
	public Double sttdevPositionPerCompany(){
		List<Long> posPerComp=(List<Long>) this.positionsPerCompany();
		double mean = this.avgPositionPerCompany();
		double temp = 0;

		for (int i = 0; i < posPerComp.size(); i++)
		{
			Long val = posPerComp.get(i);


			double squrDiffToMean = Math.pow(val - mean, 2);

			temp += squrDiffToMean;
		}

		double meanOfDiffs = (double) temp / (double) (posPerComp.size());

		return Math.sqrt(meanOfDiffs);
	}
	
	
	
}
