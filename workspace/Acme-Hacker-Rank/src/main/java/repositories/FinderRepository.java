package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Position;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {
	@Query("select p from Position p ")
	Collection<Position> search(String keyWord,Date deadline,Double minimumSalary,Date maximumDeadline);
	
}
