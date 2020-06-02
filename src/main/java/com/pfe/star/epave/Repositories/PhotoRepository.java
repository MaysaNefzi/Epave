package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Offre;
import com.pfe.star.epave.Models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo,Long> {
    @Query(value = "SELECT p from Photo p where p.sinistre.id=?1 ")
    public List<Photo> photoBySin(Long id);
}
