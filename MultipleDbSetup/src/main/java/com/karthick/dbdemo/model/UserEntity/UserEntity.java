package com.karthick.dbdemo.model.UserEntity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public abstract class UserEntity<ID> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected ID id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	@Column(length = 10)
	private Long phoneNumber;
	
	@Column(length = 12)
	private Long aadharNumber;
	
	private LocalDate createdDate;
	
	private String street;

	@Column(nullable=false)
	private String city;

	private Long zip;
	
	@Column(nullable=false)
	private String country;

	public UserEntity() {}
	
	public UserEntity(ID id, String firstName, String lastName, String email, Long phoneNumber, Long aadharNumber,
			LocalDate createdDate, String street, String city, Long zip, String country) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.aadharNumber = aadharNumber;
		this.createdDate = createdDate;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}
	
	@PrePersist
    public void ensureIdAssigned() {
          ensureIdAssignedInternal();  
    }

    public abstract void ensureIdAssignedInternal();

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(Long aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getZip() {
		return zip;
	}

	public void setZip(Long zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", aadharNumber=" + aadharNumber + ", createdDate=" + createdDate
				+ ", street=" + street + ", city=" + city + ", zip=" + zip + ", country=" + country + "]";
	}
}
