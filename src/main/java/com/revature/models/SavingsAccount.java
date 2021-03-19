package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "savingsaccounts", schema = "project1")
public class SavingsAccount extends Account {

	/*fields*/
	@Column(nullable = false, columnDefinition = "numeric(2,2) default 50.00")
	private double intrest;
	
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.LAZY, mappedBy = "sAccounts")
	@JsonBackReference
	protected List<User> u;

	/*constructors*/
	public SavingsAccount(int id, double balance, double intrest) {
		super();
		super.id = id;
		super.balance = balance;
		this.intrest = intrest;

	}

	public SavingsAccount() {
		super();
	}

	/*getters*/
	public List<User> getU() {
		return u;
	}

	public double getIntrest() {
		return intrest;
	}

	/*setters*/
	public void setU(List<User> u) {
		this.u = u;
	}
	
	public void setIntrest(double intrest) {
		this.intrest = intrest;
	}

	/*functional methods*/
	
	/*Object class overrides*/
	@Override
	public String toString() {
		return "SavingsAccount [id=" + super.id + ", super.balance=" + balance + ", intrest=" + intrest + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		temp = Double.doubleToLongBits(intrest);
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
		SavingsAccount other = (SavingsAccount) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (Double.doubleToLongBits(intrest) != Double.doubleToLongBits(other.intrest)) {
			return false;
		}
		return true;
	}
}
