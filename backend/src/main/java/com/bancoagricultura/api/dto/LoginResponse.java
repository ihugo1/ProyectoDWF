package com.bancoagricultura.api.dto;

import com.bancoagricultura.api.model.Usuario;

public class LoginResponse {
    private Long id;
    private String nombre;
    private Usuario.RolUsuario rol;
    private String token;

    // Constructor
    public LoginResponse(Usuario usuario) {
        this.id = usuario.getId_usuario();
        this.nombre = usuario.getNombre();
        this.rol = usuario.getRol();
        this.token = "token-simple-" + usuario.getId_usuario();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Usuario.RolUsuario getRol() {
        return rol;
    }

    public String getToken() {
        return token;
    }
}