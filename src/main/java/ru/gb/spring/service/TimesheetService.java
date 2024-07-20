package ru.gb.spring.service;


import org.springframework.stereotype.Service;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.repository.ProjectRepository;
import ru.gb.spring.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class TimesheetService {
    private final ProjectRepository projectRepository;
    private final TimesheetRepository timesheetRepository;

    public TimesheetService(TimesheetRepository timesheetRepository, ProjectRepository projectRepository) {
        this.timesheetRepository = timesheetRepository;
        this.projectRepository = projectRepository;
    }

    public Optional<Timesheet> getByID(Long id) {
        return timesheetRepository.getById(id);
    }

    public List<Timesheet> getALl() {
        return timesheetRepository.getAll();
    }

    public Timesheet create(Timesheet timesheet) {
        if (Objects.isNull(timesheet.getProjectID())){
            throw new IllegalArgumentException("projectID не заполнены");
        }
        if (projectRepository.getById(timesheet.getProjectID()).isEmpty()){
            throw new NoSuchElementException("Проект с id" + timesheet.getProjectID() + "отсутствует");
        }
        timesheet.setCreatedAt(LocalDate.now());
        return timesheetRepository.create(timesheet);
    }

    public void delete(Long id) {
        timesheetRepository.delete(id);
    }



}
