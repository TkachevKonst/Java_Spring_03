package ru.gb.spring.service;


import org.springframework.stereotype.Service;
import ru.gb.spring.model.Project;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.page.TimesheetPageDto;

import java.util.List;
import java.util.Optional;

@Service
public class TimesheetPageService {

    private final TimesheetService timesheetService;

    private final ProjectServise projectServise;

    public TimesheetPageService(TimesheetService timesheetService, ProjectServise projectServise) {
        this.timesheetService = timesheetService;
        this.projectServise = projectServise;
    }

    public List<TimesheetPageDto> findAll(){
        return timesheetService.getALl().stream()
                .map(this::convert)
                .toList();
    }


    public Optional<TimesheetPageDto> findById (Long id){
        return timesheetService.getByID(id).map(this::convert);
    }



    private TimesheetPageDto convert(Timesheet timesheet){
        Project project = projectServise.getByID(timesheet.getProjectID())
                .orElseThrow();

        TimesheetPageDto timesheetPageDto = new TimesheetPageDto();
        timesheetPageDto.setProjectName(project.getName());
        timesheetPageDto.setProjectId(project.getId());
        timesheetPageDto.setId(String.valueOf(timesheet.getId()));
        timesheetPageDto.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageDto.setCreatedAt(timesheet.getCreatedAt().toString());

        return timesheetPageDto;
    }


}
