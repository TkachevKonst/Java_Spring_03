package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.model.Timesheet;


public interface TimesheetRepositoryDb extends JpaRepository<Timesheet, Long> {
}
