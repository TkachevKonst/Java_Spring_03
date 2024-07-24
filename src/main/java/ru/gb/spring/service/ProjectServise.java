package ru.gb.spring.service;


import org.springframework.stereotype.Service;
import ru.gb.spring.model.Project;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.repository.ProjectRepository;
import ru.gb.spring.repository.ProjectRepositoryDb;
import ru.gb.spring.repository.TimesheetRepository;
import ru.gb.spring.repository.TimesheetRepositoryDb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProjectServise {

    private final ProjectRepositoryDb projectRepository;
    private final TimesheetRepositoryDb timesheetRepository;

    public ProjectServise(ProjectRepositoryDb projectRepository, TimesheetRepositoryDb timesheetRepository){
        this.projectRepository = projectRepository;
        this.timesheetRepository = timesheetRepository;
    }

    public Optional<Project> getByID(Long id){
        return projectRepository.findById(id);
    }

    public List<Project> getALl(){
        return projectRepository.findAll();
    }

    public Project create (Project project){
        project.setCreatedAt(LocalDate.now());
        return projectRepository.save(project);
    }

    public void delete(Long id){
        projectRepository.deleteById(id);
    }

    public List<Timesheet> getAllTimesheetsProject (Long id){
        List<Timesheet> timesheetList = new ArrayList<>();
        if (projectRepository.findById(id).isEmpty()){
            throw new NoSuchElementException("Проект с id " + id + " отсутствует");
        }
        for (int i = 0; i < timesheetRepository.findAll().size(); i++) {
            if (timesheetRepository.findAll().get(i).getProjectID().equals(id)){
                timesheetList.add(timesheetRepository.findAll().get(i));
            }
        }
        return timesheetList;
    }


}
