package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Sinistre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SinistreRepository  extends JpaRepository<Sinistre,Long> {
}
