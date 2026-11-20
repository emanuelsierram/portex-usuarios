package com.portex.usuarios.infraestructura.seguridad.jwt;

import com.portex.usuarios.infraestructura.seguridad.servicio.ServicioSeguridadUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtCustomFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final ServicioSeguridadUsuario servicioSeguridadUsuario;

    public JwtCustomFilter(JwtTokenManager jwtTokenManager, ServicioSeguridadUsuario servicioSeguridadUsuario) {
        this.jwtTokenManager = jwtTokenManager;
        this.servicioSeguridadUsuario = servicioSeguridadUsuario;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = authHeader.substring("Bearer ".length()).trim();
        if (!jwtTokenManager.esValido(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        String usuario = jwtTokenManager.obtenerUsuario(jwt);
        User user = (User) servicioSeguridadUsuario.loadUserByUsername(usuario);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
