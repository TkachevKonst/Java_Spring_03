package ru.gb.spring.service;


import org.springframework.stereotype.Service;
import ru.gb.spring.model.Project;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.repository.ProjectRepository;
import ru.gb.spring.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProjectServise {

    private final ProjectRepository projectRepository;
    private final TimesheetRepository timesheetRepository;

    public ProjectServise(ProjectRepository projectRepository, TimesheetRepository timesheetRepository){
        this.projectRepository = projectRepository;
        this.timesheetRepository = timesheetRepository;
    }

    public Optional<Project> getByID(Long id){
        return projectRepository.getById(id);
    }

    public List<Project> getALl(){
        return projectRepository.getAll();
    }

    public Project create (Project project){
        project.setCreatedAt(LocalDate.now());
        return projectRepository.create(project);
    }

    public void delete(Long id){
        projectRepository.delete(id);
    }

    public List<Timesheet> getAllTimesheetsProject (Long id){
        List<Timesheet> timesheetList = new ArrayList<>();
        if (projectRepository.getById(id).isEmpty()){
            throw new NoSuchElementException("Проект с id " + id + " отсутствует");
        }
        for (int i = 0; i < timesheetRepository.getAll().size(); i++) {
            if (timesheetRepository.getAll().get(i).getProjectID().equals(id)){
                timesheetList.add(timesheetRepository.getAll().get(i));
            }
        }
        return timesheetList;
    }


}
