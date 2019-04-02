package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity{
	
	//Attributes
	
	private String title;
	private String description;
	private Date deadline;
	private String profileRequired;
	private String technologiesRequired;
	private Double salary;
	private String ticker;
	private String skillsRequired;
	private Company company;
	
	
	//Getters and setters
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@NotNull
	@Future
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	@NotBlank
	public String getProfileRequired() {
		return profileRequired;
	}
	public void setProfileRequired(String profileRequired) {
		this.profileRequired = profileRequired;
	}
	
	@NotBlank
	public String getTechnologiesRequired() {
		return technologiesRequired;
	}
	public void setTechnologiesRequired(String technologiesRequired) {
		this.technologiesRequired = technologiesRequired;
	}
	
	@Range(min = 0)
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	@NotBlank
	@Pattern(regexp = "\\[A-Z]{4}-d{4}")
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	@NotBlank
	public String getSkillsRequired() {
		return skillsRequired;
	}
	public void setSkillsRequired(String skillsRequired) {
		this.skillsRequired = skillsRequired;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	
	
	
	

}
