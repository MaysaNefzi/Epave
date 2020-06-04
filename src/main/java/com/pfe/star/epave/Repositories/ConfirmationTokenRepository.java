package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Client;
import com.pfe.star.epave.Models.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken,Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    @Modifying
    @Query("delete from ConfirmationToken t where t.idClient=?1 ")
    @Transactional
    int deleteConfirmationToken(Long id);

    @Query("select c from ConfirmationToken c where c.idClient=?1 ")
    Optional< ConfirmationToken> tokenbyidClient(Long id);


}
