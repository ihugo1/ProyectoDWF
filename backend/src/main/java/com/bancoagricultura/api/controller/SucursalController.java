package com.bancoagricultura.api.controller;

import com.bancoagricultura.api.model.Sucursal;
import com.bancoagricultura.api.model.Usuario;
import com.bancoagricultura.api.repository.SucursalRepository;
import com.bancoagricultura.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalRepository sucursalRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // OBTENER TODAS LAS SUCURSALES
    @GetMapping
    public List<Sucursal> getAllSucursales() {
        return sucursalRepository.findAll();
    }

    // CREAR UNA NUEVA SUCURSAL
    @PostMapping
    public ResponseEntity<?> createSucursal (@RequestHeader("Authorization") String authHeader,
            @RequestBody Sucursal sucursalRequest){
        try{
            // OBTENER EL GERENTE QUE CREA LA SUCURSAL
            String token = authHeader.replace("Bearer ", "");
            Usuario usuario = usuarioRepository.findById(extractUserIdFromSimpleToken(token))
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

           // VALIDAR QUE LA CREACION ES SOLICITADA POR UN GERENTE GENERAL
            if(usuario.getRol() != Usuario.RolUsuario.GERENTE_GENERAL){
                throw new IllegalArgumentException("Solo los gerentes generales pueden crear sucursales");
            }

           // VALIDAR QUE EL NOMBRE EXISTA
           if(sucursalRequest.getNombre()==null || sucursalRequest.getNombre().isBlank()){
               throw new IllegalArgumentException("El nombre de la sucursal es obligatorio");
           }

           // VALIDAR QUE LA DIRECCION EXISTA
           if(sucursalRequest.getDireccion()==null || sucursalRequest.getDireccion().isBlank()){
                throw new IllegalArgumentException("La dirección de la sucursal es obligatoria");
           }

           // CREAR LA SUCURSAL
           Sucursal nuevaSucursal = new Sucursal();
           nuevaSucursal.setNombre(sucursalRequest.getNombre());
           nuevaSucursal.setDireccion(sucursalRequest.getDireccion());

           // GUARDAR SUCURSAL
           return ResponseEntity.ok(sucursalRepository.save(nuevaSucursal));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of("Error interno del servidor"));
        }
    }

    // OBTENER LA SUCURSAL POR SU ID
    @GetMapping("/{id}")
    public Sucursal getSucursalById(@PathVariable Long id) {
        return sucursalRepository.findById(id).orElseThrow();
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