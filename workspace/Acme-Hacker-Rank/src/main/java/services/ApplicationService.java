
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Position;
import domain.Problem;

@Transactional
@Service
public class ApplicationService {

	@Autowired
	private ApplicationRepository	applicationRepository;


	public Collection<Application> findByProblem(final Problem problem) {
		Assert.notNull(problem);
		final Collection<Application> res = this.applicationRepository.findByProblem(problem.getId());
		return res;
	}

	public Collection<Application> findByPosition(final Position position) {
		Assert.notNull(position);
		final Collection<Application> res = this.applicationRepository.findByPosition(position.getId());
		return res;
	}

}
