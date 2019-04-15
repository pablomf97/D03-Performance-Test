package services;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ApplicationRepository;

@Transactional
@Service
public class ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;



	public List<Long> applicationsPerHacker(){
		return this.applicationRepository.applicationsPerHacker();
	}

	public Long maxApplicationsPerHacker(){
		List<Long> l = this.applicationsPerHacker();
		Long res=(long) 0;
		if (!l.isEmpty()){
			res=Collections.max(this.applicationsPerHacker());
		}
		return  res;
	}

	public Long minApplicationsPerHacker(){
		List<Long> l = this.applicationsPerHacker();
		Long res=(long) 0;
		if (!l.isEmpty()){
			res=Collections.min(this.applicationsPerHacker());
		}
		return  res;
	}

	public Double avgApplicationsPerHacker(){
		int total=0;
		double avg=0.;
		if(this.applicationsPerHacker().isEmpty()){

		}else{
			for(int i = 0; i < this.applicationsPerHacker().size(); i++){
				total += this.applicationsPerHacker().get(i);
			}
			avg = (total / (double)this.applicationsPerHacker().size());
		}
		return avg;
	}
	public Double sttdevApplicationsPerHacker(){
		List<Long> apsPerH= (List<Long>) this.applicationsPerHacker();
		double mean = this.avgApplicationsPerHacker();
		double temp = 0;
		Double res=0.;
		if(this.applicationsPerHacker().isEmpty()){

		}else{
			for (int i = 0; i < apsPerH.size(); i++)
			{
				Long val = apsPerH.get(i);


				double squrDiffToMean = Math.pow(val - mean, 2);

				temp += squrDiffToMean;
			}


			double meanOfDiffs = (double) temp / (double) (apsPerH.size());
			res=Math.sqrt(meanOfDiffs);
		}

		return res;
	}







}
