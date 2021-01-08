package com.karthick.dbdemo.model.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ADDRESS")
public class Address implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String street;

	@Column(nullable=false)
	private String city;

	private Long zip;
	
	@Column(nullable=false)
	private String country;
	
	private Integer primaryAddress;

	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonIgnore
	private User user;

	public Address() {}

	public Address(Integer id, String street, String city, Long zip, String country, Integer primaryAddress, User user) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
		this.primaryAddress = primaryAddress;
		this.user = user;
	}

	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getCity()
	{
		return city;
	}
	public void setCity(String city)
	{
		this.city = city;
	}
	public User getUser()
	{
		return user;
	}
	public void setUser(User user)
	{
		this.user = user;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
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

	public Integer getPrimaryAddress() {
		return primaryAddress;
	}

	public void setPrimaryAddress(Integer primaryAddress) {
		this.primaryAddress = primaryAddress;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", zip=" + zip + ", country=" + country
				+ ", primaryAddress=" + primaryAddress + ", user=" + user + "]";
	}

}
