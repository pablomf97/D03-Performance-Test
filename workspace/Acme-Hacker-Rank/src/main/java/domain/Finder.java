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
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Attributes

	private String keyWord;
	private Date minimumDeadline;
	private Double minimumSalary;
	private Date maximumDeadline;
	private Collection<Position> results;
	private Date searchMoment;

	// Getters and setters

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMinimumDeadline() {
		return minimumDeadline;
	}

	public void setMinimumDeadline(Date minimumDeadline) {
		this.minimumDeadline = minimumDeadline;
	}

	public Double getMinimumSalary() {
		return minimumSalary;
	}

	public void setMinimumSalary(Double minimumSalary) {
		this.minimumSalary = minimumSalary;
	}

	public Date getMaximumDeadline() {
		return maximumDeadline;
	}

	public void setMaximumDeadline(Date maximumDeadline) {
		this.maximumDeadline = maximumDeadline;
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
	@Past
	public Date getSearchMoment() {
		return searchMoment;
	}

	public void setSearchMoment(Date searchMoment) {
		this.searchMoment = searchMoment;
	}
	

}
