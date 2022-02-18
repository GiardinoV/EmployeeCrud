package com.springboot2.springboot.Repositories;

import com.springboot2.springboot.Models.Employee;
import com.springboot2.springboot.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IEmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeid(String employeeid);
    }
