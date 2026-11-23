package com.portex.usuarios.infraestructura.seguridad.controlador;

import com.portex.usuarios.dominio.modelo.dto.DtoRol;
import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;
import com.portex.usuarios.dominio.puerto.dao.DaoUsuario;
import com.portex.usuarios.infraestructura.seguridad.jwt.JwtTokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/internal/v1/auth")
public class IntrospeccionControlador {

    public record ResultadoIntrospeccion(boolean active,
                                         String usuario,
                                         List<String> roles) {
    }

    private final JwtTokenManager jwtTokenManager;
    private final DaoUsuario daoUsuario;
    private final String apiKeyInterna;

    public IntrospeccionControlador(JwtTokenManager jwtTokenManager,
                                    DaoUsuario daoUsuario,
                                    @Value("${app.internal-api-key}") String apiKeyInterna) {
        this.jwtTokenManager = jwtTokenManager;
        this.daoUsuario = daoUsuario;
        this.apiKeyInterna = apiKeyInterna;
    }

    @PostMapping("/introspect")
    public ResponseEntity<ResultadoIntrospeccion> introspect(
            @RequestHeader("X-Internal-Api-Key") String apiKey,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        if (!apiKeyInterna.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ResponseEntity.ok(new ResultadoIntrospeccion(false, null, List.of()));
        }

        String jwt = authorization.substring("Bearer ".length()).trim();
        if (!jwtTokenManager.esValido(jwt)) {
            return ResponseEntity.ok(new ResultadoIntrospeccion(false, null, List.of()));
        }

        String usuario = jwtTokenManager.obtenerUsuario(jwt);
        DtoUsuario dtoUsuario = daoUsuario.consultarUsuarioPorId(usuario);
        if (Boolean.TRUE.equals(dtoUsuario.getEstaBloqueado())
                || Boolean.TRUE.equals(dtoUsuario.getEstaDesabilitado())) {
            return ResponseEntity.ok(new ResultadoIntrospeccion(false, null, List.of()));
        }

        List<String> roles = daoUsuario.consultarRolesUsuarios(usuario).stream()
                .map(DtoRol::getRol)
                .toList();
        return ResponseEntity.ok(new ResultadoIntrospeccion(true, usuario, roles));
    }
}
