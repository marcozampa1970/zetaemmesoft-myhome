package com.zetaemmesoft.myhome.integration.mapper;

import java.sql.ResultSet;


import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zetaemmesoft.myhome.integration.dto.User;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	User dto = new User();
	dto.setId(rs.getString("ID"));
	dto.setUsername(rs.getString("USERNAME"));
	dto.setPassword(rs.getString("PASSWORD"));
	return dto;
    }
}
