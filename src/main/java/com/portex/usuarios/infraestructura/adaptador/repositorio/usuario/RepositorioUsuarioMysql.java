package com.portex.usuarios.infraestructura.adaptador.repositorio.usuario;

import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;
import com.portex.usuarios.dominio.puerto.repositorio.RepositorioUsuario;
import com.portex.usuarios.infraestructura.jbdc.CustomNamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class RepositorioUsuarioMysql implements RepositorioUsuario {

    private static final String SQL_CREAR = "INSERT INTO portafolio.users (usuario, contrasena, email, bloqueado, desabilitado) VALUES (:usuario, :contrasena, :email, 0, 0)";
    private static final String SQL_ASIGNAR_ROL = "INSERT INTO portafolio.user_role (usuario, rol, fecha_creacion) VALUES (:usuario, :rol, :fecha)";
    private static final String SQL_ELIMINAR_ROL = "DELETE FROM portafolio.user_role WHERE usuario = :usuario AND rol = :rol";
    private static final String SQL_ELIMINAR_ROLES_USUARIO = "DELETE FROM portafolio.user_role WHERE usuario = :usuario";
    private static final String SQL_ELIMINAR_USUARIO = "DELETE FROM portafolio.users WHERE usuario = :usuario";

    private final CustomNamedParameterJdbcTemplate jdbc;

    public RepositorioUsuarioMysql(CustomNamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void crear(DtoUsuario usuario) {
        jdbc.actualizar(usuario, SQL_CREAR);
    }

    @Override
    public void asignarRol(String usuario, String rol) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("usuario", usuario)
                .addValue("rol", rol)
                .addValue("fecha", LocalDateTime.now());
        jdbc.getNamedParameterJdbcTemplate().update(SQL_ASIGNAR_ROL, params);
    }

    @Override
    public void eliminarRol(String usuario, String rol) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("usuario", usuario)
                .addValue("rol", rol);
        jdbc.getNamedParameterJdbcTemplate().update(SQL_ELIMINAR_ROL, params);
    }

    @Override
    public void eliminar(String usuario) {
        MapSqlParameterSource params = new MapSqlParameterSource("usuario", usuario);
        jdbc.getNamedParameterJdbcTemplate().update(SQL_ELIMINAR_ROLES_USUARIO, params);
        jdbc.getNamedParameterJdbcTemplate().update(SQL_ELIMINAR_USUARIO, params);
    }
}
