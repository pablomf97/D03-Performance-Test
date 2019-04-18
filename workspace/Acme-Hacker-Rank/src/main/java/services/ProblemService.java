package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ProblemRepository;
import domain.Problem;

@Transactional
@Service
public class ProblemService {

	@Autowired
	private ProblemRepository	problemRepository;
	
	public Collection<Problem> findProblemsByPositionId (int positionId) {
		Collection<Problem> problems;
		
		problems = this.problemRepository.findProblemsByPositionId(positionId);
		
		return problems;
	}
}
