package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class s_p {
    private Long id_sin;
    private Long id_police;

    public Long getId_sin() {
        return id_sin;
    }

    public s_p() {
    }

    public void setId_sin(Long id_sin) {
        this.id_sin = id_sin;
    }

    public s_p(Long id_sin, Long id_police) {
        this.id_sin = id_sin;
        this.id_police = id_police;
    }

    public Long getId_police() {
        return id_police;
    }

    public void setId_police(Long id_police) {
        this.id_police = id_police;
    }
}
