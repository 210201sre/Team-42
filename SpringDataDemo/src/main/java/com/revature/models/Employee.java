package com.revature.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

@Entity
@Table(name = "employee_data", schema = "project1")
public class Employee extends Person {

	/*fields*/
	protected Date dateHired;

	@Column(nullable = false, columnDefinition = "numeric(16,2) default 50000.00")
	protected double salary;
	
	/*constructors*/
	public Employee(int id, Date dateHired, double salary, String firstname, String lastname, String zipcode,
			String city, String streetAdress, String state) {
		super();
		this.id = id;
		this.dateHired = dateHired;
		this.salary = salary;
		super.firstName = firstname;
		super.lastName = firstname;
		super.zipcode = zipcode;
		super.city = city;
		super.streetAdress = streetAdress;
		super.state = state;
	}

	public Employee(Customer c) {
		this.id = c.getId();
		this.salary = 50000;
		super.firstName = c.getFirstName();
		super.lastName = c.getLastName();
		super.zipcode = c.getZipcode();
		super.city = c.getCity();
		super.streetAdress = c.getStreetAdress();
		super.state = c.getState();
		dateHired = new Date(dateHired.getTime());
	}

	public Employee() {
		super();
	}

	/*getters*/
	public Date getDateHired() {
		return dateHired;
	}

	public double getSalary() {
		return salary;
	}

	/*setters*/
	public void setDateHired(Date temp) {
		this.dateHired = temp;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	/*functional methods*/
	
	/*Object class overrides*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dateHired == null) ? 0 : dateHired.hashCode());
		long temp;
		temp = Double.doubleToLongBits(salary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Employee other = (Employee) obj;
		if (dateHired == null) {
			if (other.dateHired != null) {
				return false;
			}
		} else if (!dateHired.equals(other.dateHired))
			return false;
		if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "employee [dateHired=" + dateHired + ", salary=" + salary + ", id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", zipcode=" + zipcode + ", city=" + city + ", streetAdress="
				+ streetAdress + ", state=" + state + "]";
	}

}
