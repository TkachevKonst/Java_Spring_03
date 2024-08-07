package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.model.Project;


public interface ProjectRepositoryDb extends JpaRepository<Project,Long> {
}
