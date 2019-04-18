
package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CurriculaRepository;

@Transactional
@Service
public class CurriculaService {

	@Autowired
	private CurriculaRepository	curriculaRepository;


	public void delete(final Integer entity) {
		this.curriculaRepository.delete(entity);
	}

}
