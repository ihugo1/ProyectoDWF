package com.bancoagricultura.api.controller;

import com.bancoagricultura.api.model.Cuenta;
import com.bancoagricultura.api.repository.CuentaRepository;
import com.bancoagricultura.api.model.Usuario;
import com.bancoagricultura.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import java.util.List;
import java.math.BigDecimal;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // OBTENER TODAS LAS CUENTAS
    @GetMapping
    public List<Cuenta> getAllCuentas() {
        return cuentaRepository.findAll();
    }

    // OBTENER CUENTAS POR DUI
    @GetMapping("/dui/{dui}")
    public List<Cuenta> getCuentasPorDui(@PathVariable String dui) {
        // Validación básica del formato DUI
        if (!dui.matches("^\\d{8}-\\d$")) {
            throw new IllegalArgumentException("Formato de DUI inválido. Use: 12345678-9");
        }

        return cuentaRepository.findByUsuarioDui(dui);
    }

    // OBTENER CUENTAS POR ID_USUARIO
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Cuenta>> obtenerCuentasPorUsuario(@PathVariable String idUsuario) {
        List<Cuenta> cuentas = cuentaRepository.findByUsuarioID(idUsuario);
        return ResponseEntity.ok(cuentas);
    }

    // CREAR UNA CUENTA
    @PostMapping
    public ResponseEntity<?> createCuenta(@RequestHeader("Authorization") String authHeader) {
        try {
            // OBTENER EL CLIENTE QUE ABRE LA CUENTA
            String token = authHeader.replace("Bearer ", "");
            Usuario cliente = usuarioRepository.findById(extractUserIdFromSimpleToken(token))
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            // VALIDAR SI NO POSEE MAS DE 3 CUENTAS
            if (cuentaRepository.countByIdUsuario(cliente.getId_usuario()) >= 3) {
                throw new IllegalArgumentException("Límite de 3 cuentas alcanzado");
            }

            // CREAR UNA CUENTA NUEVA
            Cuenta nuevaCuenta = new Cuenta();
            nuevaCuenta.setNumero_cuenta(generarNumeroCuentaUnico()); // GENERAR NUMERO ALEATORIO
            nuevaCuenta.setUsuario(cliente);
            nuevaCuenta.setSaldo(BigDecimal.ZERO);

            // GUARDAR LA CUENTA
            return ResponseEntity.ok(cuentaRepository.save(nuevaCuenta));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of("Error interno del servidor"));
        }
    }

    private String generarNumeroCuentaUnico() {
        String numeroCuenta;
        do {
            // GENERA ALEATORIO
            numeroCuenta = new Random().ints(20, 0, 10)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining());

            // VERIFICAR QUE NO EXISTA
        } while (cuentaRepository.existsByNumeroCuenta(numeroCuenta));

        return numeroCuenta;
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
