package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Actor extends DomainEntity{
	
	//Atributes
	
	private String name;
	private String surname;
	private Double VAT;
	private String holder;
	private String make;
	private String number;
	private Integer expirationMonth;
	private Integer expirationYear;
	private int CVV;
	private String photo;
	private String email;
	private String phoneNumber;
	private String address;
	private UserAccount userAccount;
	
	
	//Getters and Setters
	
	@NotBlank
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotBlank
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@NotNull
	@Range(min = 0, max = 1)
	public Double getVAT() {
		return VAT;
	}
	public void setVAT(Double vAT) {
		VAT = vAT;
	}
	
	@NotBlank
	public String getHolder() {
		return holder;
	}
	public void setHolder(String holder) {
		this.holder = holder;
	}
	
	@NotBlank
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	
	@NotBlank
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@NotNull
	@Range(min = 1, max = 12)
	public Integer getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	
	@NotNull
	@Range(min = 0, max = 99)
	public Integer getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}
	
	@NotNull
	@Range(min = 0, max = 999)
	public int getCVV() {
		return CVV;
	}
	public void setCVV(int cVV) {
		CVV = cVV;
	}
	
	@URL
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	@NotBlank
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@NotNull
	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	public UserAccount getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	
	
	

}
