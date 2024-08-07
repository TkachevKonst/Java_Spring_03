package ru.gb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.model.Employee;
import ru.gb.model.Timesheet;
import ru.gb.service.EmployeeService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Tag(name = "Employees", description = "API для работы с сотрудниками")
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get Employee", description = "Получить сотрудника по индефикатору")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable @Parameter(description = "Индефикатор пользователя") Long id) {
        Optional<Employee> employee = employeeService.getByID(id);

        if (employee.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(employee.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get All Employee", description = "Получить список всех сотрудников")
    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.getALl());
    }
    @Operation(summary = "Post Employee", description = "Записать нового сотрудника")
    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employee = employeeService.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }
    @Operation(summary = "Delete Employee", description = "Удаление сотрудника по индефикатору")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Индефикатор пользователя") Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "Get Timesheets Employee", description = "Получить все timesheets определеннного пользователя по индефикатору")
    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getAllTimesheetsProject(@PathVariable @Parameter(description = "Индефикатор пользователя") Long id) {
        try {
            return ResponseEntity.ok(employeeService.getAllTimesheetsEmployee(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

    }

}
