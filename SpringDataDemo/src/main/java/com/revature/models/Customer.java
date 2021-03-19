package com.revature.models;

import java.sql.Date;

import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "customer_data", schema = "project1")
public class Customer extends Person {

	/*fields*/
	private Date dateJoined;

	/*constructors*/
	public Customer(int id, Date dateJoined, String firstname, String lastname, String zipcode, String city,
			String streetAdress, String state) {
		super();
		this.id = id;
		this.dateJoined = dateJoined;
		super.firstName = firstname;
		super.lastName = firstname;
		super.zipcode = zipcode;
		super.city = city;
		super.streetAdress = streetAdress;
		super.state = state;
	}

	public Customer() {
		super();
	}

	public Customer(Employee e) {
		this.id = e.getId();
		super.firstName = e.getFirstName();
		super.lastName = e.getLastName();
		super.zipcode = e.getZipcode();
		super.city = e.getCity();
		super.streetAdress = e.getStreetAdress();
		super.state = e.getState();
		dateJoined = e.getDateHired();
	}
	
	/*getters*/
	public Date getDateJoined() {
		return dateJoined;
	}

	/*setters*/
	public void setDateJoined(Date dateJoined) {
		this.dateJoined = dateJoined;
	}

	/*functional methods*/
	
	/*Object class overrides*/
	@Override
	public String toString() {
		return "Customer [dateJoined=" + dateJoined + ", id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", zipcode=" + zipcode + ", city=" + city + ", streetAdress=" + streetAdress + ", state="
				+ state + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dateJoined == null) ? 0 : dateJoined.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Customer other = (Customer) obj;
		if (dateJoined == null) {
			if (other.dateJoined != null) {
				return false;
			}
		} else if (!dateJoined.equals(other.dateJoined))
			return false;
		return true;
	}

}
