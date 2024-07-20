package ru.gb.spring.service;

import org.springframework.stereotype.Service;
import ru.gb.spring.model.Project;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.page.ProjectPageDto;
import ru.gb.spring.page.TimesheetPageDto;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectPageService {


    private final TimesheetService timesheetService;

    private final ProjectServise projectServise;

    public ProjectPageService(TimesheetService timesheetService, ProjectServise projectServise) {
        this.timesheetService = timesheetService;
        this.projectServise = projectServise;
    }

    public List<ProjectPageDto> findAll(){
        return projectServise.getALl().stream()
                .map(this::convert)
                .toList();
    }


    public Optional<ProjectPageDto> findById (Long id){
        return projectServise.getByID(id).map(this::convert);
    }



    private ProjectPageDto convert(Project project){


        ProjectPageDto projectPageDto = new ProjectPageDto();
        projectPageDto.setId(String.valueOf(project.getId()));
        projectPageDto.setName(project.getName());
        projectPageDto.setCreatedAt(project.getCreatedAt().toString());

        return projectPageDto;
    }



}
