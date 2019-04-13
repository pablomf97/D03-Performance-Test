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
		return Collections.max(this.applicationRepository.applicationsPerHacker());
	}

	public Long minApplicationsPerHacker(){
		return Collections.min(this.applicationRepository.applicationsPerHacker());
	}

	public Double avgApplicationsPerHacker(){
		int total=0;
		double avg=0.;
		for(int i = 0; i < this.applicationsPerHacker().size(); i++){
			total += this.applicationsPerHacker().get(i);
		}
		avg = (total / (double)this.applicationsPerHacker().size());
		return avg;
	}
	public Double sttdevApplicationsPerHacker(){
		List<Long> apsPerH= (List<Long>) this.applicationsPerHacker();
		double mean = this.avgApplicationsPerHacker();
		double temp = 0;

		for (int i = 0; i < apsPerH.size(); i++)
		{
			Long val = apsPerH.get(i);


			double squrDiffToMean = Math.pow(val - mean, 2);

			temp += squrDiffToMean;
		}

		double meanOfDiffs = (double) temp / (double) (apsPerH.size());

		return Math.sqrt(meanOfDiffs);
	}
	
	
	
	
	
	
	
}
