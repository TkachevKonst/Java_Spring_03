package ru.gb.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.spring.model.Employee;

public interface EmployeeRepositoryDb extends JpaRepository<Employee,Long> {
}
