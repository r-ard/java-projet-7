package com.nnk.springboot.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class PasswordChangeDTO {
    @NotBlank(message = "Old password must not be null")
    private String oldPassword;

    @NotBlank(message = "New password must not be null")
    private String newPassword;

    @NotBlank(message = "New password confirmation must not be null")
    private String confirmNewPassword;

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

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
