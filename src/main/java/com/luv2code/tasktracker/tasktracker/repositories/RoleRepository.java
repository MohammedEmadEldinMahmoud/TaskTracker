package com.luv2code.tasktracker.tasktracker.repositories;

import com.luv2code.tasktracker.tasktracker.entity.Role;
import com.luv2code.tasktracker.tasktracker.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long > {

        Optional<Role> findByName(RoleName name);

}
