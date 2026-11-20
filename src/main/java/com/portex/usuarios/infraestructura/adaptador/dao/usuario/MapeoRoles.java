package com.portex.usuarios.infraestructura.adaptador.dao.usuario;

import com.portex.usuarios.dominio.modelo.dto.DtoRol;
import com.portex.usuarios.infraestructura.jbdc.MapperResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MapeoRoles implements RowMapper<DtoRol>, MapperResult {

    @Override
    public DtoRol mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DtoRol(
                rs.getString("usuario"),
                rs.getString("rol"),
                extraerLocalDateTime(rs, "fecha_creacion")
        );
    }
}
