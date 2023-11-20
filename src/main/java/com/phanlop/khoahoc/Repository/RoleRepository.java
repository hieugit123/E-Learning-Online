package com.phanlop.khoahoc.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phanlop.khoahoc.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRoleName(String roleName);
}
