package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity{
	
	//Attributes
	
	private String keyWord;
	private Date deadline;
	private Double minimumSalary;
	private Double maximumSalary;
	private Collection<Position> results;
	
	//Getters and setters
	
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public Double getMinimumSalary() {
		return minimumSalary;
	}
	public void setMinimumSalary(Double minimumSalary) {
		this.minimumSalary = minimumSalary;
	}
	public Double getMaximumSalary() {
		return maximumSalary;
	}
	public void setMaximumSalary(Double maximumSalary) {
		this.maximumSalary = maximumSalary;
	}
	
	@Valid
	@NotNull
	@ManyToMany
	public Collection<Position> getResults() {
		return results;
	}
	public void setResults(Collection<Position> results) {
		this.results = results;
	}
	

	
}
