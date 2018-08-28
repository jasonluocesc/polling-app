package com.wenbin.polls.repository;

import com.wenbin.polls.model.Role;
import com.wenbin.polls.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Wenbin Luo @ Aalto University
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
