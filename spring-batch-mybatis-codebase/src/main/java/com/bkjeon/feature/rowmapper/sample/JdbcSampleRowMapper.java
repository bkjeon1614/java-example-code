package com.bkjeon.feature.rowmapper.sample;

import com.bkjeon.feature.entity.sample.Sample;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class JdbcSampleRowMapper implements RowMapper<Sample> {

    @Override
    public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Sample.builder()
            .id(rs.getLong("id"))
            .amount(rs.getLong("amount"))
            .txName(rs.getString("tx_name"))
            .txDateTime(rs.getTimestamp("tx_date_time").toLocalDateTime())
            .build();
    }

}
