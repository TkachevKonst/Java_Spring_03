package ru.gb.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.spring.model.Role;
import ru.gb.spring.model.User;

import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long> {

}
