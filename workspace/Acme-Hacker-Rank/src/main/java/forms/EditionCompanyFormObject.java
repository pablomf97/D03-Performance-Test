package forms;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import domain.Company;

public class EditionCompanyFormObject {

	/* Attributes */

	/* Actor attributes */
	private String username;
	private String password;

	private int id;
	private int version;
	private String name;
	private String surname;
	private Double VAT;
	private String photo;
	private String email;
	private String phoneNumber;
	private String address;
	private String commercialName;

	/* Credit Card attributes */
	private String holder;
	private String make;
	private String number;
	private Integer expirationMonth;
	private Integer expirationYear;
	private Integer CVV;

	public EditionCompanyFormObject() {

	}

	public EditionCompanyFormObject(Company company) {
		this.id = company.getId();
		this.version = company.getVersion();
		this.username = company.getUserAccount().getUsername();
		this.password = company.getUserAccount().getPassword();
		this.name = company.getName();
		this.surname = company.getSurname();
		this.VAT = company.getVAT();
		this.photo = company.getPhoto();
		this.email = company.getEmail();
		this.phoneNumber = company.getPhoneNumber();
		this.address = company.getAddress();
		this.commercialName = company.getCommercialName();
		this.holder = company.getCreditCard().getHolder();
		this.make = company.getCreditCard().getMake();
		this.number = company.getCreditCard().getNumber();
		this.expirationMonth = company.getCreditCard().getExpirationMonth();
		this.expirationYear = company.getCreditCard().getExpirationYear();
		this.CVV = company.getCreditCard().getCVV();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public void setVAT(Double VAT) {
		this.VAT = VAT;
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

	@NotBlank
	public String getCommercialName() {
		return commercialName;
	}

	public void setCommercialName(String commercialName) {
		this.commercialName = commercialName;
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
	@CreditCardNumber
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
	public Integer getCVV() {
		return CVV;
	}

	public void setCVV(Integer CVV) {
		this.CVV = CVV;
	}

}