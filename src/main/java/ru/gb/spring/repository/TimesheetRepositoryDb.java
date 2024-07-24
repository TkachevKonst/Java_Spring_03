package ru.gb.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.spring.model.Timesheet;

public interface TimesheetRepositoryDb extends JpaRepository<Timesheet, Long> {
}
