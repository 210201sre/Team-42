package com.revature.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

	/*fields*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, unique = true, updatable = false)
	protected int id;
	
	protected String firstName;
	
	protected String lastName;
	
	protected String zipcode;
	
	protected String city;
	
	protected String streetAdress;
	
	protected String state;

	/*constructors*/
	
	/*getters*/
	public int getId() {
		return id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getZipcode() {
		return zipcode;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getStreetAdress() {
		return streetAdress;
	}
	
	public String getState() {
		return state;
	}	

	/*setters*/
	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setStreetAdress(String streetAdress) {
		this.streetAdress = streetAdress;
	}

	public void setState(String state) {
		this.state = state;
	}

	/*functional methods*/
	
	/*Object class overrides*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((streetAdress == null) ? 0 : streetAdress.hashCode());
		result = prime * result + ((zipcode == null) ? 0 : zipcode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (firstName == null) {
			if (other.firstName != null) {
				return false;
			}
		} else if (!firstName.equals(other.firstName)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (lastName == null) {
			if (other.lastName != null) {
				return false;
			}
		} else if (!lastName.equals(other.lastName)) {
			return false;
		}
		if (state == null) {
			if (other.state != null) {
				return false;
			}
		} else if (!state.equals(other.state)) {
			return false;
		}
		if (streetAdress == null) {
			if (other.streetAdress != null) {
				return false;
			}
		} else if (!streetAdress.equals(other.streetAdress))
			return false;
		if (zipcode == null) {
			if (other.zipcode != null) {
				return false;
			}
		} else if (!zipcode.equals(other.zipcode)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", zipcode=" + zipcode
				+ ", city=" + city + ", streetAdress=" + streetAdress + ", state=" + state + "]";
	}

}
