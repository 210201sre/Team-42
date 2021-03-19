package com.revature.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Account {
	/*fields*/
	
	/*constructors*/
	
	/*getters*/
	
	/*setters*/
	
	/*functional methods*/
	
	/*Object class overrides*/
	
	/*CRUD methods*/
	
	/*non-CRUD methods*/
	
	/*GET methods*/
	
	/*POST methods*/
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Account_id", nullable = false, unique = true, updatable = false)
	protected int id;
	@Column(nullable = false, columnDefinition = "numeric(16,2) default 50.00")
	protected double balance;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Account)) {
			return false;
		}
		Account other = (Account) obj;
		return Double.doubleToLongBits(balance) == Double.doubleToLongBits(other.balance) && id == other.id;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", balance=" + balance + "]";
	}
}
