package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportRepository  extends JpaRepository<Rapport,Long> {
}
