
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select a from Application a where a.problem.id = ?1")
	Collection<Application> findByProblem(int id);

	@Query("select a from Application a where a.position.id = ?1")
	Collection<Application> findByPosition(int id);

}
