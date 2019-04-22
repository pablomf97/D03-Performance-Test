
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Curricula;
import domain.EducationData;
import domain.Hacker;
import domain.PositionData;

import repositories.CurriculaRepository;
import repositories.EducationDataRepository;
import repositories.MiscellaneousDataRepository;
import repositories.PersonalDataRepository;
import repositories.PositionDataRepository;

@Transactional
@Service
public class CurriculaService {

	@Autowired
	private CurriculaRepository	curriculaRepository;
	
	@Autowired 
	private PersonalDataRepository personalDataRepository;
	
	@Autowired
	private MiscellaneousDataRepository miscellaneousDataRepository;
	
	@Autowired
	private PositionDataRepository positionDataRepository;
	
	@Autowired
	private EducationDataRepository educationDataRepository;


	public void delete(final Integer entity) {
		this.curriculaRepository.delete(entity);
	}
	
	protected void deleteCV(final Hacker hacker) {
		Collection<Curricula> cvs;
		cvs=this.curriculaRepository.findCVPerHacker(hacker.getId());
	
			for (Curricula cv :cvs){
				
		
				this.miscellaneousDataRepository.deleteInBatch(cv.getMiscellaneousData());
				for (EducationData ed: cv.getEducationData()){
					this.educationDataRepository.delete(ed);
				}
				for (PositionData pd : cv.getPositionData()){
					this.positionDataRepository.delete(pd);
				}

				
				this.curriculaRepository.delete(cv);
			}
			
		
		
	}

}
