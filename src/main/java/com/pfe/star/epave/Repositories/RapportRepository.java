package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.RapportPre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportRepository  extends JpaRepository<RapportPre,Long> {
    @Query(value = "SELECT rp  from RapportPre rp  where rp.verif <> -1   ")
    public List<RapportPre> listeRapport();

}
