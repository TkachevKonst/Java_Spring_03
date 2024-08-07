package ru.gb.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Timesheet;
import ru.gb.service.TimesheetService;


import java.util.List;
import java.util.Optional;
@Tag(name = "Timesheets", description = "API для работы с расписаниями")
@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

    private final TimesheetService service;

    public TimesheetController(TimesheetService service) {
        this.service = service;
    }

    @Operation(summary = "Get Timesheet", description = "Получить расписание по индефикатору")
    @GetMapping("/{id}")
    public ResponseEntity<Timesheet> get(@PathVariable @Parameter(description = "Индефикатор расписания") Long id) {
        Optional<Timesheet> timesheet = service.getByID(id);

        if (timesheet.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(timesheet.get());
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Get All Timesheet", description = "Получить список всех расписаний")
    @GetMapping
    public ResponseEntity<List<Timesheet>> getAll() {
        return ResponseEntity.ok(service.getALl());
    }
    @Operation(summary = "Post Timesheet", description = "Записать новое расписание")
    @PostMapping
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        Optional<Timesheet> timesheetOptional = Optional.ofNullable(service.create(timesheet));
        if (timesheetOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(timesheetOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Delete Timesheet", description = "Удалить расписание по индефикатору")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Индефикатор расписания") Long id) {
        return ResponseEntity.noContent().build();
    }

}
