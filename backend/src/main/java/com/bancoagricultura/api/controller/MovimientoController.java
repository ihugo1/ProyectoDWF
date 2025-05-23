package com.bancoagricultura.api.controller;

import com.bancoagricultura.api.model.Movimiento;
import com.bancoagricultura.api.model.Cuenta;
import com.bancoagricultura.api.model.Usuario;;
import com.bancoagricultura.api.repository.MovimientoRepository;
import com.bancoagricultura.api.repository.CuentaRepository;
import com.bancoagricultura.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoRepository movimientoRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // CREAR MOVIMIENTO
    @PostMapping
    public ResponseEntity<?> createMoviemiento(@RequestHeader("Authorization") String authHeader
            ,@RequestBody Movimiento movimientoRequest){
        try{
            // VALIDAR QUE LA CUENTA EXISTE
            if(movimientoRequest.getCuenta().getId_cuenta() == null){
                throw new IllegalArgumentException("El movimiento debe tener una cuenta asignada");
            }

            // VALIDAR QUE EL MOVIMIENTO TIENE UN TIPO VALIDO
            if(movimientoRequest.getTipo() == null){
                throw new IllegalArgumentException("El movimiento debe ser de un tipo especificado");
            }

            // EVALUAR QUE EL MONTO SEA MAYOR QUE $50
            if(movimientoRequest.getMonto().compareTo(new BigDecimal(50.00))<0){
                throw new IllegalArgumentException("El movimiento debe ser minimo de $50");
            }

            // OBTENER LA CUENTA DEL CLIENTE Y EL USUARIO QUE REALIZA EL MOVIMIENTO (EMPLEADO/DEPENDIENTE)
            Cuenta cuenta = cuentaRepository.findById(movimientoRequest.getCuenta().getId_cuenta())
                    .orElseThrow(() -> new IllegalArgumentException("Cuenta no encontrada"));

            String token = authHeader.replace("Bearer ", "");
            Usuario usuario = usuarioRepository.findById(extractUserIdFromSimpleToken(token))
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

            // COMISION INICIAL
            BigDecimal comision = BigDecimal.ZERO;

            // EN CASO DE DEPOSITO
            if(movimientoRequest.getTipo() == Movimiento.TipoMovimiento.DEPOSITO){
                BigDecimal montoADepositar = movimientoRequest.getMonto();
                // APLICAR COMISION (DEPENDIENTES)
                if(usuario.getRol() == Usuario.RolUsuario.DEPENDIENTE){
                    comision = montoADepositar.multiply(new BigDecimal(0.05));
                    montoADepositar = montoADepositar.subtract(comision);
                }
                cuenta.setSaldo(cuenta.getSaldo().add(montoADepositar));
            }

            // EN CASO DE RETIRO
            if(movimientoRequest.getTipo() == Movimiento.TipoMovimiento.RETIRO){
                BigDecimal montoARetirar= movimientoRequest.getMonto();
                // APLICAR COMISION (DEPENDIENTES)
                if(usuario.getRol() == Usuario.RolUsuario.DEPENDIENTE){
                    comision = montoARetirar.multiply(new BigDecimal(0.05));
                    montoARetirar = montoARetirar.add(comision);
                }
                // ERROR SI NO SE POSEE SALDO SUFICIENTE EN LA CUENTA
                if(montoARetirar.compareTo(cuenta.getSaldo()) > 0){
                    throw new IllegalArgumentException(
                            "Saldo insuficiente para realizar el retiro de $"+montoARetirar);
                }
                cuenta.setSaldo(cuenta.getSaldo().subtract(montoARetirar));
            }

            // CREAR EL REGISTRO DEL MOVIMIENTO
            Movimiento nuevoMoviento = new Movimiento();
            nuevoMoviento.setUsuario(usuario);
            nuevoMoviento.setTipo(movimientoRequest.getTipo());
            nuevoMoviento.setMonto(movimientoRequest.getMonto());
            nuevoMoviento.setComision(comision);
            nuevoMoviento.setCuenta(cuenta);

            // GUARDAR EL MOVIEMIENTO
            return ResponseEntity.ok(movimientoRepository.save(nuevoMoviento));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of("Error interno del servidor"));
        }
    }

    // OBTENER TODOS LOS MOVIMIENTOS DE UNA ID
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Movimiento>> getMovimientosByUserId(
            @PathVariable Long usuarioId) {
        List<Movimiento> movimientos = movimientoRepository.findMovimientosByUsuarioId(usuarioId);
        return ResponseEntity.ok(movimientos);
    }

    // OBTENER TODOS LOS MOVIMIENTOS
    @GetMapping
    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    // EXTRAER EL ID DEL TOKEN
    private Long extractUserIdFromSimpleToken(String token) {
        try {
            // Extrae el ID del formato "token-simple-123"
            String idStr = token.replace("token-simple-", "");
            return Long.parseLong(idStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Token inválido");
        }
    }
}



