package com.springboot2.springboot.Repositories;


import com.springboot2.springboot.Models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeJpaRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeId(String employeeId);


}
