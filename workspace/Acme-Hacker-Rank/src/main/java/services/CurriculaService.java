package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CurriculaRepository;
import domain.Curricula;

@Transactional
@Service
public class CurriculaService {

	@Autowired
	private CurriculaRepository	curriculaRepository;
	
	public Collection<Curricula> findCurriculasByHackerId (int hackerId) {
		Collection<Curricula> curriculas;
		
		curriculas = this.curriculaRepository.findCurriculasByHackerId(hackerId);
		
		return curriculas;
	}
}
