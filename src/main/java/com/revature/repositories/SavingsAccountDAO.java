package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.models.SavingsAccount;
@Repository
public interface SavingsAccountDAO extends JpaRepository<SavingsAccount, Integer> {

}
