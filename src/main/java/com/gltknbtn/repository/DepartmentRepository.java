package com.gltknbtn.repository;

import com.gltknbtn.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called dpaRepository
// CRUD refers Create, Read, Update, Delete

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

}