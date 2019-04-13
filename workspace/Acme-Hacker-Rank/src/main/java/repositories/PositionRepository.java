package repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
	
	@Query("select MIN(p.salary) from Position p")
	Double minSalarayPositions();
	@Query("select MAX(p.salary) from Position p")
	Double maxSalaryPositions();
	
	@Query("select AVG(p.salary) from Position p")
	Double AVGSalaryPositions();
	
	@Query("select STDDEV(p.salary) from Position p")
	Double STDDEVSalaryPositions();
	@Query("select p.title from Position p where p.salary=( select max(p.salary) from Position p)")
	String bestPositionSalary();
	@Query("select p.title from Position p where p.salary=( select min(p.salary) from Position p)")
	String worstPositionSalary();
	
	@Query("select max(p.company.commercialName) from Position p")
	String companyWithMorePositions();
	
	@Query("select ((select count(*) from Position p where p.company=c)) from Company c ")
	List<Long> positionsPerCompany();
	

}
