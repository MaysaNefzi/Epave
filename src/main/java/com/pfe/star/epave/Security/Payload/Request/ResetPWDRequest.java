package com.pfe.star.epave.Security.Payload.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ResetPWDRequest {
    @Email
    @NotBlank
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
