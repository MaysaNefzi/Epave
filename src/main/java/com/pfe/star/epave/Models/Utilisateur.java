package com.pfe.star.epave.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String login;
    @NotNull
    private String password;

    public Utilisateur(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Utilisateur() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
