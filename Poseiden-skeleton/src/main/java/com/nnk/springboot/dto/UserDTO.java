package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;

public class UserDTO {
    private Integer id;

    @NotBlank(message = "FullName is mandatory")
    private String fullname;

    @NotBlank(message = "Username is mandatory")
    private String username;


    private String password;

    @NotBlank(message = "Role is mandatory")
    private String role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
