package com.zetaemmesoft.myhome.integration.mapper;

import java.sql.ResultSet;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.zetaemmesoft.myhome.integration.dto.Role;

public class RoleRowMapper implements RowMapper<Role> {

    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
	Role dto = new Role();
	dto.setId(rs.getString("ID"));
	dto.setRolename(rs.getString("ROLENAME"));
	return dto;
    }
}
