package com.bancoagricultura.api.repository;

import com.bancoagricultura.api.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByUsuario_Dui(String dui);
    List<Prestamo> findByEstado(Prestamo.EstadoPrestamo estado);
}