package ru.gb.spring.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    private List<Long> projectId;
    private List<Long> timesheetId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getProjectId() {
        return projectId;
    }

    public void setProjectId(List<Long> projectId) {
        this.projectId = projectId;
    }

    public List<Long> getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(List<Long> timesheetId) {
        this.timesheetId = timesheetId;
    }
}
