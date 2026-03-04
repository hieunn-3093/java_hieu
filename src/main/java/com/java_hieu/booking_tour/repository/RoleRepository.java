package com.java_hieu.booking_tour.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.java_hieu.booking_tour.entity.Role;
import com.java_hieu.booking_tour.entity.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(RoleName name);
}
