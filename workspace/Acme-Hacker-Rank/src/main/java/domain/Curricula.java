package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Curricula extends DomainEntity{
	
	//Attributes
	
	private MiscellaneousData miscellaneousData;
	private EducationData educationData;
	private PositionData positionData;
	private PersonalData personalData;
	
	//Getters and setters
	
	@OneToOne(cascade = CascadeType.ALL)
	public MiscellaneousData getMiscellaneousData() {
		return miscellaneousData;
	}
	public void setMiscellaneousData(MiscellaneousData miscellaneousData) {
		this.miscellaneousData = miscellaneousData;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	public EducationData getEducationData() {
		return educationData;
	}
	public void setEducationData(EducationData educationData) {
		this.educationData = educationData;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	public PositionData getPositionData() {
		return positionData;
	}
	public void setPositionData(PositionData positionData) {
		this.positionData = positionData;
	}
	
	@OneToOne(cascade = CascadeType.ALL)
	public PersonalData getPersonalData() {
		return personalData;
	}
	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}
	
	
	
	
}
