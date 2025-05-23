package com.bancoagricultura.api.repository;

import com.bancoagricultura.api.model.Cuenta;
import com.bancoagricultura.api.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query("SELECT COUNT(c) FROM Cuenta c WHERE c.usuario.id_usuario = :idUsuario")
    long countByIdUsuario(@Param("idUsuario") Long idUsuario);

    @Query("SELECT c FROM Cuenta c WHERE c.usuario.id_usuario = :id_usuario")
    List<Cuenta> findByUsuarioID(@Param("id_usuario") String id_usuario);

    @Query("SELECT c FROM Cuenta c WHERE c.usuario.dui = :dui")
    List<Cuenta> findByUsuarioDui(@Param("dui") String dui);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cuenta c WHERE c.numero_cuenta = :numeroCuenta")
    boolean existsByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

    @Query("SELECT c FROM Cuenta c WHERE c.numero_cuenta = :numeroCuenta")
    Optional<Cuenta> findByNumeroCuenta(@Param("numeroCuenta") String numeroCuenta);

}