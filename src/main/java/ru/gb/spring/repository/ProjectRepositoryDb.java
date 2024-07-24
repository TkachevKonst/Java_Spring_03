package ru.gb.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.spring.model.Project;

public interface ProjectRepositoryDb extends JpaRepository<Project,Long> {
}
