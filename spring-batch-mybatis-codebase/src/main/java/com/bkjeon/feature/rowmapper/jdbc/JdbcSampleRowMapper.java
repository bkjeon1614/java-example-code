package com.bkjeon.feature.rowmapper.jdbc;

import com.bkjeon.feature.entity.jdbc.JdbcSample;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class JdbcSampleRowMapper implements RowMapper<JdbcSample> {

    @Override
    public JdbcSample mapRow(ResultSet rs, int rowNum) throws SQLException {
        return JdbcSample.builder()
            .id(rs.getLong("id"))
            .amount(rs.getLong("amount"))
            .txName(rs.getString("tx_name"))
            .txDateTime(rs.getTimestamp("tx_date_time").toLocalDateTime())
            .build();
    }

}
