package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.RapportFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportFinalRepository  extends JpaRepository<RapportFinal,Long> {
}
