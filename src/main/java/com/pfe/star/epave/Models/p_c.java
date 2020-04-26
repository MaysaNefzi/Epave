package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class p_c {

    private  Long id_p;
    private Long tel;
    private Long tel2;

    public p_c(Long id_p, Long tel, Long tel2) {
        this.id_p = id_p;
        this.tel = tel;
        this.tel2 = tel2;
    }

    public p_c( ) {
    }
    public Long getId_p() {
        return id_p;
    }

    public void setId_p(Long id_p) {
        this.id_p = id_p;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }
    public Long getTel2() {
        return tel2;
    }

    public void setTel2(Long tel2) {
        this.tel2 = tel2;
    }

}
