package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Police;
import com.pfe.star.epave.Models.s_p;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoliceRepository  extends JpaRepository<Police,Long> {
    @Query(value = "SELECT new com.pfe.star.epave.Models.s_p(s.id , p.id)  from  Sinistre  s JOIN  s.police p where s.id=?1")
    public List<s_p> policesinistre(Long id);
}
