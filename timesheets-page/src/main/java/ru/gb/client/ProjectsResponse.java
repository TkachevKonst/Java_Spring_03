package ru.gb.client;

import java.time.LocalDate;
import java.util.Set;

public class ProjectsResponse {

    private Long id;
    private String name;
    private LocalDate createdAt;
    private Set<Long> employee;

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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Long> getEmployee() {
        return employee;
    }

    public void setEmployee(Set<Long> employee) {
        this.employee = employee;
    }
}
