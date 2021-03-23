package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.CheckingsAccount;

@Repository
public interface CheckingsAccountDAO extends JpaRepository<CheckingsAccount, Integer> {

}
