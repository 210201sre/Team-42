package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Integer> {

}
