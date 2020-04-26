package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Client;
import com.pfe.star.epave.Models.Vente;
import com.pfe.star.epave.Models.p_c;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    @Query(value = "SELECT  new com.pfe.star.epave.Models.p_c(p.id ,c.tel1 , c.tel2)  from  Police  p JOIN  p.client c where p.id=?1")
    public List<p_c> clientpolice(Long id);

}
