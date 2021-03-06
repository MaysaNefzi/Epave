package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Client;
import com.pfe.star.epave.Models.Vente;
import com.pfe.star.epave.Models.p_c;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
    @Query(value = "SELECT  new com.pfe.star.epave.Models.p_c(p.id ,c.tel1 , c.tel2)  from  Police  p JOIN  p.client c where p.id=?1")
    public List<p_c> clientpolice(Long id);

    Boolean existsByUsername(String username);
    Optional<Client> findByCin(String cin);
    Client getById(Long id);
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE Client c SET c.username=?2, c.password=?2 where c.id =?1 ")
    @Transactional
    int deleteMailPassword(Long id,String vide);

    @Query(value = "select c From Client c where c.username=?1 ")
    @Transactional
    Optional<Client> findByEmail(String email);

}
