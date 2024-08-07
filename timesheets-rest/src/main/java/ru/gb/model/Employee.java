package ru.gb.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private Set<Long> timesheetId;

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

    public Set<Long> getTimesheetId() {
        return timesheetId;
    }

    public void setTimesheetId(Set<Long> timesheetId) {
        this.timesheetId = timesheetId;
    }
}
