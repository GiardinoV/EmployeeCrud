package com.springboot2.springboot;

import com.springboot2.springboot.Models.Employee;
import com.springboot2.springboot.Repositories.IEmployeeJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
//@AutoConfigureTestDatabase (connection = EmbeddedDatabaseConnection.H2) M
public class EmployeeJpaRepositoryTest {
    @Autowired
    private IEmployeeJpaRepository repo;
    
    @Test
    public void saveEmployee(){
        Employee vir = new Employee("Vir", "Giardino", "v123");
        Employee carla = new Employee("Carla", "Perez", "v124");

        repo.save(vir);
        repo.save(carla);

        repo.flush();

        assertEquals(2, repo.findAll().size());
    }
}
