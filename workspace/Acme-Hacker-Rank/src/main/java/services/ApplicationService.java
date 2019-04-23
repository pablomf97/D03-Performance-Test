
package services;


import java.util.Collection;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;
import domain.Curricula;
import domain.Hacker;
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
	public void delete(final Integer entity) {
		this.applicationRepository.delete(entity);
	}

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


	protected void deleteApp(final Hacker hacker) {
		Collection<Application> apps;
		apps=this.applicationRepository.findApplicationPerHacker(hacker.getId());

		this.applicationRepository.deleteInBatch(apps);

	}
	public void deleteAppPerPos(Application app){
		this.applicationRepository.delete(app);
	}

}
