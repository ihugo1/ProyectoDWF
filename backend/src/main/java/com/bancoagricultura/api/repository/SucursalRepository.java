package com.bancoagricultura.api.repository;

import com.bancoagricultura.api.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {
}