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
	@Query("select p from Position p where p.salary >= '?0'   and where  p.deadline <= '?1' and (p.ticker like %?2% or p.description like %?2% or p.title like %?2% or p.profileRequired like %?2% or p.technologiesRequired like %?2% or p.skillsRequired like %?2%)")
	Collection<Position> search(Double minimumSalary,Date maximumDeadline,String keyWord);
	
	
	@Query("select max(h.finder.results.size), min(h.finder.results.size), avg(h.finder.results.size),sqrt(sum(h.finder.results.size* h.finder.results.size) / count(h.finder.results.size) -(avg(h.finder.results.size) * avg(h.finder.results.size))) from Hacker h")
	Double[] StatsFinder();

	@Query("select p from Position p where  ( p.deadline like '?0')")
	Position searchDeadline(Date deadline);
	@Query("select f from Finder f where f.results.size='0'")
	Collection<Finder> FindersEmpty();
	
	@Query("select (select count(e) from Curricula e where e.hacker=h) from Hacker h")
	Collection<Integer> numberCurriculaPerHacker();
	
}
