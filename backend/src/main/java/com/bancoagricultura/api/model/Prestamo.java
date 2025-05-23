package com.bancoagricultura.api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Prestamos")
public class Prestamo {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_prestamo;

    // ID CLIENTE
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // MONTO DE PRESTAMO
    private BigDecimal monto_solicitado;

    // INTERES
    private BigDecimal tasa_interes;

    // PLAZO DE PAGO
    private Integer plazo_anios;

    // CUOTA MENSUAL
    private BigDecimal cuota_mensual;

    // ESTADO
    public enum EstadoPrestamo {
        ESPERA, APROBADO, RECHAZADO
    }
    @Enumerated(EnumType.STRING)
    private EstadoPrestamo estado = EstadoPrestamo.ESPERA;

    // CAJERO QUE ABRIO EL PRESTAMO
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cajero", nullable = false)
    private Usuario cajero; // Usuario con rol CAJERO

    // GERENTE QUE CHECKEA EL PRESTAMO (LO APRUEBA O LO RECHAZA)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gerente")
    private Usuario gerente;

    // GETTERS Y SETTERS

    public Long getId_prestamo() {
        return id_prestamo;
    }

    public void setId_prestamo(Long id_prestamo) {
        this.id_prestamo = id_prestamo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getMonto_solicitado() {
        return monto_solicitado;
    }

    public void setMonto_solicitado(BigDecimal monto_solicitado) {
        this.monto_solicitado = monto_solicitado;
    }

    public BigDecimal getTasa_interes() {
        return tasa_interes;
    }

    public void setTasa_interes(BigDecimal tasa_interes) {
        this.tasa_interes = tasa_interes;
    }

    public Integer getPlazo_anios() {
        return plazo_anios;
    }

    public void setPlazo_anios(Integer plazo_anios) {
        this.plazo_anios = plazo_anios;
    }

    public BigDecimal getCuota_mensual() {
        return cuota_mensual;
    }

    public void setCuota_mensual(BigDecimal cuota_mensual) {
        this.cuota_mensual = cuota_mensual;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public Usuario getCajero() {
        return cajero;
    }

    public void setCajero(Usuario cajero) {
        this.cajero = cajero;
    }

    public Usuario getGerente() {
        return gerente;
    }

    public void setGerente(Usuario gerente) {
        this.gerente = gerente;
    }
}