package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreRepository  extends JpaRepository<Offre,Long> {

}
