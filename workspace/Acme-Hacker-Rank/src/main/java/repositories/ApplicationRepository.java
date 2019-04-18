
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
	
	@Query("select a from Application a where a.hacker.id = ?1")
	Collection<Application> findApplicationsByHackerId(int hackerId);
	
	@Query("select a from Application a join a.position p where p.company.id = ?1")
	Collection<Application> findApplicationsByCompanyId(int companyId);

	@Query("select a from Application a where a.problem.id = ?1")
	Collection<Application> findByProblem(int id);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPosition(int id);

}
