package ru.gb.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Project;
import ru.gb.model.Timesheet;
import ru.gb.service.ProjectServise;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Tag(name = "Projects", description = "API для работы с проектами")
@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final TimesheetController timesheetController;

    private final ProjectServise projectServise;

    public ProjectController(ProjectServise projectServise, TimesheetController timesheetController){
        this.projectServise = projectServise;
        this.timesheetController = timesheetController;
    }

    @Operation(summary = "Get Timesheets Project", description = "Получить все timesheets определеннного проекта по индефикатору")
    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getAllTimesheetsProject (@PathVariable @Parameter(description = "Индефикатор проекта") Long id){
        try {
            return ResponseEntity.ok(projectServise.getAllTimesheetsProject(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }

    }
    @Operation(summary = "Get Project", description = "Получить проект по индефикатору")
    @GetMapping("/{id}")
    public ResponseEntity<Project> get (@PathVariable @Parameter(description = "Индефикатор проекта") Long id){
        Optional<Project> project = projectServise.getByID(id);

        if(project.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(project.get());
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Get All Project", description = "Получить список всех проектов")
    @GetMapping
    public ResponseEntity<List<Project>> getAll(){
        return ResponseEntity.ok(projectServise.getALl());
    }

    @Operation(summary = "Post Project", description = "Записать новый проект")
    @PostMapping
    public ResponseEntity<Project> create(@RequestBody Project project){
        project = projectServise.create(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
    @Operation(summary = "Delete Project", description = "Удалить проект по индефикатору")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Индефикатор проекта") Long id){
        projectServise.delete(id);
        return ResponseEntity.noContent().build();
    }



}
