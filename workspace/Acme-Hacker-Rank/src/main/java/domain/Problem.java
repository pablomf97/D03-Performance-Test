package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Problem extends DomainEntity {

	// Attributes

	private String title;
	private String statement;
	private String optionalHint;
	private String attachments;
	private Position position;
	private Boolean isDraft;

	// Getters and setters

	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@NotBlank
	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getOptionalHint() {
		return optionalHint;
	}

	public void setOptionalHint(String optionalHint) {
		this.optionalHint = optionalHint;
	}

	@NotBlank
	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	@NotNull
	public Boolean getIsDraft() {
		return isDraft;
	}

	public void setIsDraft(Boolean isDraft) {
		this.isDraft = isDraft;
	}

}
