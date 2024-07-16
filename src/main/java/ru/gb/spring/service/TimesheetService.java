package ru.gb.spring.service;


import org.springframework.stereotype.Service;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TimesheetService {
    private final ProjectServise projectServise;
    private final TimesheetRepository repository;

    public TimesheetService(TimesheetRepository repository, ProjectServise projectServise) {
        this.repository = repository;
        this.projectServise = projectServise;
    }

    public Optional<Timesheet> getByID(Long id) {
        return repository.getById(id);
    }

    public List<Timesheet> getALl() {
        return repository.getAll();
    }

    public Timesheet create(Timesheet timesheet) {
        for (int i = 0; i < projectServise.getALl().size(); i++) {
            if (projectServise.getALl().get(i).getId().equals(timesheet.getProjectID())){
                timesheet.setCreatedAt(LocalDate.now());
                return repository.create(timesheet);
            }
        }
        return timesheet = null;
    }

    public void delete(Long id) {
        repository.delete(id);
    }



}
