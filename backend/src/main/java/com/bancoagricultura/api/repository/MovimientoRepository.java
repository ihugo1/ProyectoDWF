package com.bancoagricultura.api.repository;

import com.bancoagricultura.api.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.usuario.id_usuario = :usuarioId")
    List<Movimiento> findMovimientosByUsuarioId(@Param("usuarioId") Long usuarioId);
}
