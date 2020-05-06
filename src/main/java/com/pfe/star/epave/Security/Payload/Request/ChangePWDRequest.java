package com.pfe.star.epave.Security.Payload.Request;

import javax.validation.constraints.NotBlank;

public class ChangePWDRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;


    public ChangePWDRequest(@NotBlank String oldPassword, @NotBlank String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;

    }

    public ChangePWDRequest() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }


}
