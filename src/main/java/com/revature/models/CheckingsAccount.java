package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "checkingaccounts")
public class CheckingsAccount extends Account {
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "cAccounts")
	@JsonBackReference
	protected List<User> u;

	public List<User> getU() {
		return u;
	}

	public void setU(List<User> u) {
		this.u = u;
	}

	public CheckingsAccount(int id, double balance) {
		super();
		super.id = id;
		super.balance = balance;
	}

	public CheckingsAccount() {
		super();
	}

	@Override
	public String toString() {
		return "CheckingsAccount [id=" + id + ", balance=" + balance + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CheckingsAccount other = (CheckingsAccount) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

}
