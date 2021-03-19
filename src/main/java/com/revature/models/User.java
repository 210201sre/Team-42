package com.revature.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", nullable = false, unique = true, updatable = false)
	private int id;

	@Length(min = 5)
	@NotBlank
	@Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]*")
	private String username;
	@Length(min = 4)
	@NotBlank
	private String password;
	private boolean employee;
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<CheckingsAccount> cAccounts;
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<SavingsAccount> sAccounts;
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private Employee employee_data;
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private Customer customer_data;

}
