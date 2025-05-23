package com.bancoagricultura.api.service;

import com.bancoagricultura.api.dto.LoginResponse;
import com.bancoagricultura.api.model.Usuario;
import com.bancoagricultura.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponse authenticate(String dui, String password) {
        Usuario usuario = usuarioRepository.findByDuiAndPassword(dui, password)
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (usuario.getEstado() != Usuario.EstadoUsuario.ACTIVO) {
            throw new RuntimeException("Usuario no está activo");
        }

        return new LoginResponse(usuario);
    }
}