package com.bancoagricultura.api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "usuarios")
public class Usuario {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;

    // DUI
    @Column(unique = true, nullable = false, length = 10)
    private String dui;

    // NOMBRE
    @Column(nullable = false, length = 50)
    private String nombre;

    // CONTRA
    private String password;

    // ROL
    public enum RolUsuario {
        CLIENTE, DEPENDIENTE, CAJERO, PERSONAL_LIMPIEZA, RECEPCIONISTA, ASESOR_FINANCIERO, GERENTE_SUCURSAL, GERENTE_GENERAL
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;

    // ESTADO
    public enum EstadoUsuario {
        ACTIVO,
        INACTIVO,
        PENDIENTE_APROBACION
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    // SALARIO (SOLO PARA CLIENTES)
    private BigDecimal salario;

    // COMERCIO (SOLO PARA DEPENDIENTES)

    private String nombre_comercio;

    // ID DE SUCURSAL (SOLO PARA EMPLEADOS) (LLAVE FORANEA)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_sucursal")
    private Sucursal sucursal;

    // GETTERS Y SETTERS

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public EstadoUsuario getEstado() {
        return estado;
    }

    public void setEstado(EstadoUsuario estado) {
        this.estado = estado;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public String getNombre_comercio() {
        return nombre_comercio;
    }

    public void setNombre_comercio(String nombre_comercio) {
        this.nombre_comercio = nombre_comercio;
    }
}