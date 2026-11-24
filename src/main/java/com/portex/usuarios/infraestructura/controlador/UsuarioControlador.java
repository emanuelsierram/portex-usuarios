package com.portex.usuarios.infraestructura.controlador;

import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;
import com.portex.usuarios.dominio.servicio.ServicioGestionarUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final ServicioGestionarUsuario servicioGestionarUsuario;
    private final PasswordEncoder passwordEncoder;

    public UsuarioControlador(ServicioGestionarUsuario servicioGestionarUsuario,
                              PasswordEncoder passwordEncoder) {
        this.servicioGestionarUsuario = servicioGestionarUsuario;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody DtoUsuario usuario) {
        DtoUsuario usuarioSeguridad = new DtoUsuario(
                usuario.getUsuario(),
                passwordEncoder.encode(usuario.getContrasena()),
                usuario.getEmail(),
                false,
                false
        );
        servicioGestionarUsuario.crear(usuarioSeguridad);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/{usuario}/roles/{rol}")
    public ResponseEntity<Void> asignarRol(@PathVariable String usuario,
                                           @PathVariable String rol) {
        servicioGestionarUsuario.asignarRol(usuario, rol);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{usuario}/roles/{rol}")
    public ResponseEntity<Void> eliminarRol(@PathVariable String usuario,
                                            @PathVariable String rol) {
        servicioGestionarUsuario.eliminarRol(usuario, rol);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuario}")
    public ResponseEntity<Void> eliminar(@PathVariable String usuario) {
        servicioGestionarUsuario.eliminar(usuario);
        return ResponseEntity.noContent().build();
    }
}
