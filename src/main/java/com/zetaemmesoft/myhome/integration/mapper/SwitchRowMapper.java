package com.zetaemmesoft.myhome.integration.mapper;

import java.sql.ResultSet;


import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zetaemmesoft.myhome.integration.dto.Switch;

public class SwitchRowMapper implements RowMapper<Switch> {

    @Override
    public Switch mapRow(ResultSet rs, int rowNum) throws SQLException {
	Switch dto = new Switch();
	dto.setId(rs.getInt("ID"));
	dto.setName(rs.getString("NAME"));
	dto.setType(rs.getInt("ITEM_TYPE"));
	dto.setValue(rs.getInt("VALUE"));
	dto.setUnit(rs.getString("UNIT"));
	dto.setNode(rs.getString("NODE"));
	dto.setTime(rs.getTimestamp("TIME"));
	dto.setTopic(rs.getString("TOPIC"));
	return dto;
    }
}
