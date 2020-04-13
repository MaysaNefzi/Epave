package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Expert;
import com.pfe.star.epave.Models.Gestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository  extends JpaRepository<Expert,Long> {
    @Query(value = "SELECT e from Expert e order by e.nom DESC ")
    public List<Expert> getAllExp();
}
