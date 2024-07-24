package ru.gb.spring.service;

import org.springframework.stereotype.Service;
import ru.gb.spring.model.Employee;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.repository.EmployeeRepositoryDb;
import ru.gb.spring.repository.ProjectRepositoryDb;
import ru.gb.spring.repository.TimesheetRepositoryDb;


import java.util.ArrayList;
import java.util.List;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepositoryDb repositoryDb;
    private final ProjectRepositoryDb projectRepositoryDb;
    private final TimesheetRepositoryDb timesheetRepositoryDb;

    public EmployeeService(EmployeeRepositoryDb repositoryDb, ProjectRepositoryDb projectRepositoryDb, TimesheetRepositoryDb timesheetRepositoryDb) {
        this.repositoryDb = repositoryDb;
        this.projectRepositoryDb = projectRepositoryDb;
        this.timesheetRepositoryDb = timesheetRepositoryDb;
    }

    public Optional<Employee> getByID(Long id) {
        return repositoryDb.findById(id);
    }

    public List<Employee> getALl() {
        return repositoryDb.findAll();
    }

    public Employee create(Employee employee) {
        return repositoryDb.save(employee);
    }

    public void delete(Long id) {
        repositoryDb.deleteById(id);
    }

    public List<Timesheet> getAllTimesheetsEmployee(Long id) {
        List<Timesheet> timesheetList = new ArrayList<>();
        if (repositoryDb.findById(id).isEmpty()) {
            throw new NoSuchElementException("Сотрудник с id " + id + " отсутствует");
        }
        for (int i = 0; i < timesheetRepositoryDb.findAll().size(); i++) {
            if (repositoryDb.findById(id).get().getTimesheetId().contains(timesheetRepositoryDb.findAll().get(i).getId())) {
                timesheetList.add(timesheetRepositoryDb.findAll().get(i));
            }
        }
        return timesheetList;
    }
}

