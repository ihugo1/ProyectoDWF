package com.bancoagricultura.api.repository;

import com.bancoagricultura.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByDui(String dui);
    Optional<Usuario> findByDuiAndPassword(String dui, String password);

    Usuario findUserByDui(String dui);

    List<Usuario> findByRolNotAndEstadoIn(
            Usuario.RolUsuario rol,
            List<Usuario.EstadoUsuario> estados);

    List<Usuario> findByRolNotInAndEstado(
            List<Usuario.RolUsuario> roles,
            Usuario.EstadoUsuario estado);
}