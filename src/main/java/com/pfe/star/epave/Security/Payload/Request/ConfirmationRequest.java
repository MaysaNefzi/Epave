package com.pfe.star.epave.Security.Payload.Request;

import javax.validation.constraints.NotBlank;

public class ConfirmationRequest {
    @NotBlank
    private String code;
    private String username;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
