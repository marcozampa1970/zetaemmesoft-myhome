package com.zetaemmesoft.myhome.integration.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zetaemmesoft.myhome.integration.dto.ItemType;

public class ItemTypeRowMapper implements RowMapper<ItemType> {

    @Override
    public ItemType mapRow(ResultSet rs, int rowNum) throws SQLException {
	ItemType dto = new ItemType();
	dto.setId(rs.getInt("ID"));
	dto.setName(rs.getString("NAME"));	
	return dto;
    }
}
