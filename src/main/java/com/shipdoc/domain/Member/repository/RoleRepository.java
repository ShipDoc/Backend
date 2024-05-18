package com.shipdoc.domain.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shipdoc.domain.Member.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
