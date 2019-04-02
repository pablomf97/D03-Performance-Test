package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Hacker extends Actor{
	
	//Attributes
	private Finder finder;

	
	//Geters and setters
	
	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Finder getFinder() {
		return finder;
	}

	public void setFinder(Finder finder) {
		this.finder = finder;
	}
	
	

	
	
	
}
