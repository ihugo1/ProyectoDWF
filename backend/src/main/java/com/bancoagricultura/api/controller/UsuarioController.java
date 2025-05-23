package com.bancoagricultura.api.controller;

import com.bancoagricultura.api.model.Prestamo;
import com.bancoagricultura.api.model.Usuario;
import com.bancoagricultura.api.repository.UsuarioRepository;
import com.bancoagricultura.api.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.math.BigDecimal;
import java.util.Arrays;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SucursalRepository sucursalRepository;

    // OBTENER TODOS LOS USUARIOS
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // OBTENER TODOS LOS USUARIOS QUE TENGAN ESTADO DISTINTO A ACTIVO
    @GetMapping("/usuarios-no-activos")
    public ResponseEntity<List<Usuario>> getUsuariosNoActivos() {
        try {
            List<Usuario.EstadoUsuario> estadosNoActivos = Arrays.asList(
                    Usuario.EstadoUsuario.PENDIENTE_APROBACION,
                    Usuario.EstadoUsuario.INACTIVO
            );
            List<Usuario> usuarios = usuarioRepository.findByRolNotAndEstadoIn(
                    Usuario.RolUsuario.GERENTE_GENERAL,
                    estadosNoActivos
            );
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // OBTENER TODOS LOS EMPLEADOS QUE ESTEN ACTIVOS
    @GetMapping("/empleados-activos")
    public ResponseEntity<List<Usuario>> getEmpleadosActivos() {
        try {
            List<Usuario.RolUsuario> rolesExcluidos = Arrays.asList(
                    Usuario.RolUsuario.CLIENTE,
                    Usuario.RolUsuario.DEPENDIENTE,
                    Usuario.RolUsuario.GERENTE_SUCURSAL,
                    Usuario.RolUsuario.GERENTE_GENERAL
            );
            List<Usuario> empleados = usuarioRepository.findByRolNotInAndEstado(
                    rolesExcluidos,
                    Usuario.EstadoUsuario.ACTIVO
            );
            return ResponseEntity.ok(empleados);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // CREAR UN USUARIO
    @PostMapping
    public ResponseEntity<?> createUsuario(@RequestHeader("Authorization") String authHeader,
            @RequestBody Usuario usuarioRequest) {
        try {
            // VALIDAR QUE EL DUI EXISTA Y QUE NO ESTE REPETIDO
            if (usuarioRequest.getDui() == null || usuarioRequest.getDui().isBlank()) {
                throw new IllegalArgumentException("El DUI es obligatorio");
            }
            if (usuarioRepository.findUserByDui(usuarioRequest.getDui()) != null) {
                throw new IllegalArgumentException("Ya existe un usuario con el mismo DUI");
            }

            // VALIDAR QUE EL NOMBRE EXISTA
            if (usuarioRequest.getNombre() == null || usuarioRequest.getNombre().isBlank()) {
                throw new IllegalArgumentException("El nombre es obligatorio");
            }

            // VALIDAR QUE LA CONTRASEÑA EXISTA
            if (usuarioRequest.getPassword() == null || usuarioRequest.getPassword().isBlank()){
                throw new IllegalArgumentException("La contraseña no puede estar vacia");
            }

            // VALIDAR QUE EL ROL EXISTA
            if (usuarioRequest.getRol() == null) {
                throw new IllegalArgumentException("El rol es obligatorio");
            }

            // OBTENER EL EMPLEADO QUE CREA AL USUARIO
            String token = authHeader.replace("Bearer ", "");
            Usuario empleado = usuarioRepository.findById(extractUserIdFromSimpleToken(token))
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));

            // ASIGNAR ROL Y ESTADO SEGUN EL EMPLEADO QUE CREA EL USUARIO

            // CAJERO: CLIENTES/DEPENDIENTES (ESTADO: ACTIVO)
            if (empleado.getRol() == Usuario.RolUsuario.CAJERO) {
                if (usuarioRequest.getRol() != Usuario.RolUsuario.CLIENTE &&
                        usuarioRequest.getRol() != Usuario.RolUsuario.DEPENDIENTE) {
                    throw new IllegalArgumentException("Solo puedes crear clientes o dependientes");
                }
                usuarioRequest.setEstado(Usuario.EstadoUsuario.ACTIVO);
            }

            // GERENTE_SUCURSAL: CLIENTES/DEPENDIENTES (ACTIVO) Y EMPLEADOS (PENDIENTE_APROBACION)
            else if (empleado.getRol() == Usuario.RolUsuario.GERENTE_SUCURSAL) {
                if (usuarioRequest.getRol() == Usuario.RolUsuario.GERENTE_GENERAL ||
                        usuarioRequest.getRol() == Usuario.RolUsuario.GERENTE_SUCURSAL) {
                    throw new IllegalArgumentException("No tienes permisos para crear este rol");
                }
                if (usuarioRequest.getRol() == Usuario.RolUsuario.CLIENTE ||
                        usuarioRequest.getRol() == Usuario.RolUsuario.DEPENDIENTE) {
                    usuarioRequest.setEstado(Usuario.EstadoUsuario.ACTIVO);
                } else {
                    usuarioRequest.setEstado(Usuario.EstadoUsuario.PENDIENTE_APROBACION);
                }
            }

            // GERENTE_GENERAL: CUALQUIER ROL (ACTIVO)
            else if (empleado.getRol() == Usuario.RolUsuario.GERENTE_GENERAL) {
                usuarioRequest.setEstado(Usuario.EstadoUsuario.ACTIVO);
            } else {
                throw new IllegalArgumentException("No tienes permisos para crear usuarios");
            }

            // CREAR USUARIO Y ASIGNAR CAMPOS OBLIGATORIOS
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setDui(usuarioRequest.getDui());
            nuevoUsuario.setNombre(usuarioRequest.getNombre());
            nuevoUsuario.setPassword((usuarioRequest.getPassword()));
            nuevoUsuario.setRol(usuarioRequest.getRol());
            nuevoUsuario.setEstado(usuarioRequest.getEstado());

            // ASIGNAR Y VALIDAR QUE SALARIO EXISTE (CLIENTES)
            if (usuarioRequest.getRol() == Usuario.RolUsuario.CLIENTE) {
                if (usuarioRequest.getSalario().compareTo(new BigDecimal("0.1")) < 0) {
                    throw new IllegalArgumentException("El salario para clientes debe ser mayor a 0.1");
                }
                nuevoUsuario.setSalario(usuarioRequest.getSalario());
            }

            // ASIGNAR Y VALIDAR NOMBRE DE COMERCIO EXISTE (DEPENDIENTE)
            else if (usuarioRequest.getRol() == Usuario.RolUsuario.DEPENDIENTE) {
                if (usuarioRequest.getNombre_comercio() == null) {
                    throw new IllegalArgumentException("Todos los dependientes deben tener una negocio asignado");
                }
                nuevoUsuario.setNombre_comercio(usuarioRequest.getNombre_comercio());
            }

            // ASIGNAR Y VALIDAR QUE TIENE UNA SUCURSAL ASIGNADA (EMPLEADOS)
            else{
                if (usuarioRequest.getSucursal() == null || usuarioRequest.getSucursal().getId_sucursal() == null) {
                    throw new IllegalArgumentException("Todos los empleados deben tener una sucursal asignada");
                }
                nuevoUsuario.setSucursal(usuarioRequest.getSucursal());
            }

            // GUARDAR EL USUARIO
            return ResponseEntity.ok(usuarioRepository.save(nuevoUsuario));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of("Error interno del servidor"));
        }
    }

    // CAMBIAR ESTADO DE USUARIO
    @PutMapping("/{idUsuario}/estado")
    public ResponseEntity<?> cambiarEstadoUsuario(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable Long idUsuario,
            @RequestParam Usuario.EstadoUsuario nuevoEstado) {
        try {
            //OBTENER EL GERENTE QUE QUIERE CAMBIAR EL ESTADO DE UN USUARIO
            String token = authHeader.replace("Bearer ", "");
            Usuario gerente = usuarioRepository.findById(extractUserIdFromSimpleToken(token))
                    .orElseThrow(() -> new IllegalArgumentException("Gerente no encontrado"));

            // OBTENER EL USUARIO
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

            // VALIDAR QUE EL ESTADO ESTE CAMBIADO
            if(usuario.getEstado() == nuevoEstado) {
                throw new IllegalArgumentException("El usuario ya tiene este estado asignado");
            }

            // VERIFICAR QUE ES UN GERENTE GENERAL EN CASO DE ACTIVACION
            if(nuevoEstado == Usuario.EstadoUsuario.ACTIVO){
                if(gerente.getRol() != Usuario.RolUsuario.GERENTE_GENERAL){
                    throw new IllegalArgumentException(
                            "Solo el gerente general puede realizar activacion de usuarios"
                    );
                }
            }

            // ACTUALIZAR EL ESTADO
            usuario.setEstado(nuevoEstado);

            // Guardar los cambios
            return ResponseEntity.ok(usuarioRepository.save(usuario));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(List.of(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(List.of("Error interno del servidor"));
        }
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