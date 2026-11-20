package com.portex.usuarios.infraestructura.adaptador.dao.usuario;

import com.portex.usuarios.dominio.modelo.dto.DtoUsuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapeoUsuario implements RowMapper<DtoUsuario> {

    @Override
    public DtoUsuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DtoUsuario(
                rs.getString("usuario"),
                rs.getString("contrasena"),
                rs.getString("email"),
                rs.getBoolean("desabilitado"),
                rs.getBoolean("bloqueado")
        );
    }
}
