package com.bancoagricultura.api.controller;

import com.bancoagricultura.api.model.Prestamo;
import com.bancoagricultura.api.model.Usuario;
import com.bancoagricultura.api.repository.PrestamoRepository;
import com.bancoagricultura.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;


@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {
    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // CREAR PRESTAMO
    @PostMapping
    public ResponseEntity<?> crearPrestamo(@RequestHeader("Authorization") String authHeader,
            @RequestBody Prestamo prestamoRequest){
        try{
            // OBTENER EL EMPLEADO QUE ABRE EL PRESTAMO
            String token = authHeader.replace("Bearer ", "");
            Usuario cajero = usuarioRepository.findById(extractUserIdFromSimpleToken(token))
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

            // OBTENER POR NUMERO DE DUI AL CLIENTE QUE SOLICITA EL PRESTAMO
            Usuario cliente = usuarioRepository.findByDui(prestamoRequest.getUsuario().getDui())
                    .orElseThrow(() -> new IllegalArgumentException("Error al verificar cliente"));

            // OBTENER EL SALARIO DEL CLIENTE Y EL MONTO SOLICITADO
            BigDecimal salario = cliente.getSalario();
            BigDecimal montoSolicitado = prestamoRequest.getMonto_solicitado();

            // VALIDAR QUE EL PRESTAMO ES MINIMO DE 1000
            if (montoSolicitado.compareTo(new BigDecimal("1000")) < 0) {
                throw new IllegalArgumentException("El monto minimo de un prestamo es de $1000");
            }

            // VARIBLES A CALCULAR PARA CREAR EL PRESTAMO
            BigDecimal montoMaximo;
            BigDecimal tasaInteres;
            BigDecimal cuotaMensalMaxima;

            // ENCONTRAR EL CASO DE MONTO MAXIMO QUE PUEDE SOLICITARSE SEGUN EL SALARIO
            if (salario.compareTo(new BigDecimal("365")) < 0) {
                montoMaximo = new BigDecimal("10000");
                tasaInteres = new BigDecimal("0.03");
            } else if (salario.compareTo(new BigDecimal("600")) < 0) {
                montoMaximo = new BigDecimal("25000");
                tasaInteres = new BigDecimal("0.03");
            } else if (salario.compareTo(new BigDecimal("900")) < 0) {
                montoMaximo = new BigDecimal("35000");
                tasaInteres = new BigDecimal("0.04");
            } else {
                montoMaximo = new BigDecimal("50000");
                tasaInteres = new BigDecimal("0.05");
            }

            // VALIDAR QUE EL MONTO SOLICITADO NO SE PASE DEL MAXIMO
            if (montoSolicitado.compareTo(montoMaximo) > 0) {
                throw new IllegalArgumentException("Monto máximo permitido para su salario: $" + montoMaximo);
            }

            // CALCULAR EL MAXIMO DE CUOTA SIN QUE PASE DEL 30% DEL SALARIO
            cuotaMensalMaxima = salario.multiply(new BigDecimal("0.30"));

            // CALCULO DE PLAZO MINIMO (BASE 5)
            int plazoAnios = 5;
            BigDecimal cuota = calcularCuota(montoSolicitado, tasaInteres, plazoAnios);

            // AJUSTAR EL PLAZO SI LA CUOTA SE PASA DEL MAX
            while (cuota.compareTo(cuotaMensalMaxima) > 0 && plazoAnios < 10) {
                plazoAnios++;
                cuota = calcularCuota(montoSolicitado, tasaInteres, plazoAnios);
            }

            if (cuota.compareTo(cuotaMensalMaxima) > 0) {
                throw new IllegalArgumentException("No se puede aprobar el prestamo. Cuota mínima: $" + cuota);
            }

            // CREAR EL PRESTAMO
            Prestamo nuevoPrestamo = new Prestamo();
            nuevoPrestamo.setMonto_solicitado(montoSolicitado);
            nuevoPrestamo.setTasa_interes(tasaInteres);
            nuevoPrestamo.setPlazo_anios(plazoAnios);
            nuevoPrestamo.setCuota_mensual(cuota);
            nuevoPrestamo.setEstado(Prestamo.EstadoPrestamo.ESPERA); // UN GERENTE DEBE CAMBIAR EL ESTADO
            nuevoPrestamo.setUsuario(cliente);
            nuevoPrestamo.setCajero(cajero);

            // GUARDAR EL PRESTAMO
            return ResponseEntity.ok(prestamoRepository.save(nuevoPrestamo));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of("Error interno del servidor"));
        }
    }

    // OBTENER TODOS LOS PRESTAMOS
    @GetMapping
    public List<Prestamo> getAllPrestamos() {
        return prestamoRepository.findAll();
    }

    // OBTENER TODOS LOS PRESTAMOS CON ESTADO PENDIENTE
    @GetMapping("/pendientes")
    public ResponseEntity<List<Prestamo>> getPrestamosPendientes() {
        List<Prestamo> prestamos = prestamoRepository.findByEstado(Prestamo.EstadoPrestamo.ESPERA);
        return ResponseEntity.ok(prestamos);
    }

    // CAMBIAR ESTADO DE PRESTAMO
    @PutMapping("/{idPrestamo}/estado")
    public ResponseEntity<?> cambiarEstadoPrestamo(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long idPrestamo,
            @RequestParam Prestamo.EstadoPrestamo nuevoEstado) {
        try {
            // OBTENER EL GERENTE QUE ABRE EL PRESTAMO
            String token = authHeader.replace("Bearer ", "");
            Usuario gerente = usuarioRepository.findById(extractUserIdFromSimpleToken(token))
                    .orElseThrow(() -> new IllegalArgumentException("Gerente no encontrado"));

            // OBTENER EL PRESTAMO
            Prestamo prestamo = prestamoRepository.findById(idPrestamo)
                    .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

            // VALIDAR QUE SEA UN PRESTAMO EN ESPERA
            if (prestamo.getEstado() != Prestamo.EstadoPrestamo.ESPERA) {
                throw new IllegalArgumentException("Solo se puede cambiar el estado de préstamos en espera");
            }

            // VALIDAR QUE CAMBIE A APROBADO O RECHAZADO
            if (nuevoEstado != Prestamo.EstadoPrestamo.APROBADO && nuevoEstado
                    != Prestamo.EstadoPrestamo.RECHAZADO) {
                throw new IllegalArgumentException("El nuevo estado debe ser APROBADO o RECHAZADO");
            }

            // ACTUALIZAR EL ESTADO DEL PRESTAMO Y EL GERENTE QUE CAMBIO EL ESTADO
            prestamo.setEstado(nuevoEstado);
            prestamo.setGerente(gerente);

            // GUARDAR CAMBIOS
            return ResponseEntity.ok(prestamoRepository.save(prestamo));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of("Error interno del servidor"));
        }
    }

    // CALCULAR CUOTA MENSUAL
    private BigDecimal calcularCuota(BigDecimal monto, BigDecimal tasaAnual, int plazoAnios) {
        BigDecimal tasaMensual = tasaAnual.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
        int plazoMeses = plazoAnios * 12;
        BigDecimal factor = BigDecimal.ONE.add(tasaMensual).pow(plazoMeses);
        return monto.multiply(tasaMensual)
                .multiply(factor)
                .divide(factor.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
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