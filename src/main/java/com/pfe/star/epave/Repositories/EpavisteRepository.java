package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Epaviste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpavisteRepository extends JpaRepository<Epaviste,Long> {
}
