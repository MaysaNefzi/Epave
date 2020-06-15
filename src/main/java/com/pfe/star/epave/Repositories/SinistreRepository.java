package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Sinistre;
import com.pfe.star.epave.Models.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SinistreRepository  extends JpaRepository<Sinistre,Long> {
    @Query(value = "SELECT s from Sinistre  s where s.id=?1 ")
    public Sinistre Sin(Long idS);

    @Query(value = "SELECT s from Sinistre  s where s.immatriculation=?1 ")
    public Sinistre Sin_ByImm(String immatriculation);

    @Query(value = "SELECT s from Sinistre  s where UPPER(s.marque) LIKE CONCAT('%',UPPER(:recherche),'%')")
    List<Sinistre> findByMarqueLike(String recherche);
    @Query(value = "SELECT s from Sinistre  s where UPPER(s.modele) LIKE CONCAT('%',UPPER(:recherche),'%')")

    List<Sinistre> findByModeleLike(String recherche);

}
