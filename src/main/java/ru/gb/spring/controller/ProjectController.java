package ru.gb.spring.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.spring.model.Project;
import ru.gb.spring.model.Timesheet;
import ru.gb.spring.service.ProjectServise;
import ru.gb.spring.service.TimesheetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final TimesheetController timesheetController;

    private final ProjectServise projectServise;

    public ProjectController(ProjectServise projectServise, TimesheetController timesheetController){
        this.projectServise = projectServise;
        this.timesheetController = timesheetController;
    }
    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getAllTimesheetsProject (@PathVariable Long id){
        return ResponseEntity.ok(projectServise.getAllTimesheetsProject(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> get (@PathVariable Long id){
        Optional<Project> project = projectServise.getByID(id);

        if(project.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(project.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAll(){
        return ResponseEntity.ok(projectServise.getALl());
    }

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project){
        project = projectServise.create(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return ResponseEntity.noContent().build();
    }



}
