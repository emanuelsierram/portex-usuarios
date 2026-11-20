package com.portex.usuarios.infraestructura.adaptador.dao.usuario;

import com.portex.usuarios.dominio.modelo.dto.DtoRol;
import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;
import com.portex.usuarios.dominio.puerto.dao.DaoUsuario;
import com.portex.usuarios.infraestructura.jbdc.CustomNamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DaoUsuarioMysql implements DaoUsuario {

    private static final String SQL_USUARIO = "SELECT usuario, contrasena, email, bloqueado, desabilitado FROM portafolio.users WHERE usuario = :usuario";
    private static final String SQL_ROLES = "SELECT usuario, rol, fecha_creacion FROM portafolio.user_role WHERE usuario = :usuario";

    private final CustomNamedParameterJdbcTemplate jdbc;

    public DaoUsuarioMysql(CustomNamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public DtoUsuario consultarUsuarioPorId(String usuario) {
        MapSqlParameterSource params = new MapSqlParameterSource("usuario", usuario);
        return jdbc.getNamedParameterJdbcTemplate().queryForObject(SQL_USUARIO, params, new MapeoUsuario());
    }

    @Override
    public List<DtoRol> consultarRolesUsuarios(String usuario) {
        MapSqlParameterSource params = new MapSqlParameterSource("usuario", usuario);
        return jdbc.getNamedParameterJdbcTemplate().query(SQL_ROLES, params, new MapeoRoles());
    }
}
