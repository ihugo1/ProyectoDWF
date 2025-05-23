package com.bancoagricultura.api.dto;

public class LoginRequest {
    private String dui;
    private String password;

    // Getters y Setters
    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}