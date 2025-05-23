package com.bancoagricultura.api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "movimientos")
public class Movimiento {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_movimiento;

    // ID CUENTA (LLAVE FORANEA)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cuenta")
    private Cuenta cuenta;

    // TIPO
    public enum TipoMovimiento {
        DEPOSITO, RETIRO, TRANSFERENCIA_ENVIADA, TRANSFERENCIA_RECIBIDA
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Movimiento.TipoMovimiento tipo = TipoMovimiento.DEPOSITO;

    // MONTO
    private BigDecimal monto;

    // ID DE AUTOR
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    // COMISION
    private BigDecimal comision;

    // GETTERS Y SETTERS
    public Long getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(Long id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }
}
