package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.ERole;
import com.pfe.star.epave.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);
}
