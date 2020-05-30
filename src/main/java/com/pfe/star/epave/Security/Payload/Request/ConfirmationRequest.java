package com.pfe.star.epave.Security.Payload.Request;

import javax.validation.constraints.NotBlank;

public class ConfirmationRequest {
    @NotBlank
    private String code;
    private String cin;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }
}
