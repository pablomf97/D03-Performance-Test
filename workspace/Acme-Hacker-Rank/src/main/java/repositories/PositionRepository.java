package repositories;



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
	
	
	@Query("select max(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c")
	Integer maxPositionPerCompany();
	@Query("select min(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c")
	Integer minPositionPerCompany();
	@Query("select avg(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c")
	Double avgPositionPerCompany();
	@Query("select stddev(1.0*(select count(p) from Position p where p.company.id = c.id)) from Company c")
	Double stddevPositionPerCompany();
	

}
