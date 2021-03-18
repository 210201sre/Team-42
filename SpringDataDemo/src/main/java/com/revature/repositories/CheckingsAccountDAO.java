package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.CheckingsAccount;

public interface CheckingsAccountDAO extends JpaRepository<CheckingsAccount, Integer> {

}
