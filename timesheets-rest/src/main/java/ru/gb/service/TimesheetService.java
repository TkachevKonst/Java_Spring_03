package ru.gb.service;


import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.gb.aspect.TypeArgument;
import ru.gb.model.Timesheet;
import ru.gb.repository.ProjectRepositoryDb;
import ru.gb.repository.TimesheetRepositoryDb;


import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;


@Service
public class TimesheetService {
    private final ProjectRepositoryDb projectRepository;
    private final TimesheetRepositoryDb timesheetRepository;

    public TimesheetService(TimesheetRepositoryDb timesheetRepository, ProjectRepositoryDb projectRepository) {
        this.timesheetRepository = timesheetRepository;
        this.projectRepository = projectRepository;
    }
    @TypeArgument
    public Optional<Timesheet> getByID(Long id) {
        return timesheetRepository.findById(id);
    }



    public List<Timesheet> getALl() {
        try {
            throw new Exception("Опять");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //return timesheetRepository.findAll();
    }

    public Timesheet create(Timesheet timesheet) {
        if (Objects.isNull(timesheet.getProjectID())){
            throw new IllegalArgumentException("projectID не заполнены");
        }
        if (projectRepository.findById(timesheet.getProjectID()).isEmpty()){
            throw new NoSuchElementException("Проект с id" + timesheet.getProjectID() + "отсутствует");
        }
        timesheet.setCreatedAt(LocalDate.now());
        return timesheetRepository.save(timesheet);
    }

    public void delete(Long id) {
        timesheetRepository.deleteById(id);
    }



}
