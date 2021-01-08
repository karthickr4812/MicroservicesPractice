package com.karthick.dbdemo.model.user;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	@Column(length = 10)
	private Long phoneNumber;
	
	@Column(length = 12)
	private Long aadharNumber;
	
	private LocalDate createdDate;
	
	@OneToMany(mappedBy="user")
	private Set<Address> addresses = new HashSet<>();
	

	public User(){}

	public User(Integer id, String firstName, String lastName, String email, Long phoneNumber, Long aadharNumber,
			Set<Address> addresses, LocalDate createdDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.aadharNumber = aadharNumber;
		this.addresses = addresses;
		this.createdDate = createdDate;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
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

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail()
	{
		return email;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", aadharNumber=" + aadharNumber + ", addresses=" + addresses
				+ ", createdDate=" + createdDate + "]";
	}
	
}
