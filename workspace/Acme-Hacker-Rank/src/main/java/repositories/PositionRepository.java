
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	@Query("select p from Position p where p.company.id = ?1")
	Collection<Position> findByOwner(int id);

	@Query("select p from Position p where p.ticker = ?1")
	Position findByTicker(String ticker);

	@Query("select p from Position p where p.isDraft = false")
	Collection<Position> findAllFinal();

}
