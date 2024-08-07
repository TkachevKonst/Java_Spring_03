package ru.gb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.model.Role;


import java.util.List;


public interface RoleRepository extends JpaRepository<Role, Long> {

}
