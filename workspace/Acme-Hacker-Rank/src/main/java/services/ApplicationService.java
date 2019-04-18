package services;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ApplicationRepository;

@Transactional
@Service
public class ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;




	public Integer maxApplicationsPerHacker(){
	
		return  this.applicationRepository.maxApplicationsPerHacker();
	}

	public Integer minApplicationsPerHacker(){
		
		return  this.applicationRepository.minApplicationsPerHacker();
	}

	public Double avgApplicationsPerHacker(){
	
		return this.applicationRepository.avgApplicationsPerHacker();
	}
	public Double sttdevApplicationsPerHacker(){
		
		return this.applicationRepository.stddevApplicationsPerHacker();
	}







}
