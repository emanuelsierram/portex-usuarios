package com.portex.usuarios.infraestructura.jbdc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface MapperResult {

    default LocalDateTime extraerLocalDateTime(ResultSet resultSet, String label) throws SQLException {
        Timestamp fecha = resultSet.getTimestamp(label);
        return resultSet.wasNull() ? null : fecha.toLocalDateTime();
    }
}
