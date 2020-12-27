package com.zetaemmesoft.services.integration.repository;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zetaemmesoft.services.integration.dto.Role;
import com.zetaemmesoft.services.integration.dto.User;
import com.zetaemmesoft.services.integration.mapper.RoleRowMapper;
import com.zetaemmesoft.services.integration.mapper.UserRowMapper;

@Repository
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public User findUser(String username) {
	User user = null;

	String sql = "SELECT ID, USERNAME, PASSWORD FROM MONITOR.APP_USER WHERE UPPER(USERNAME) = UPPER(:username)";

	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	namedParameters.addValue("username", username);

	try {
	    user = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new UserRowMapper());
	} catch (EmptyResultDataAccessException e) {
	    logger.warn(e.getMessage());
	}

	return user;
    }

    public List<Role> findRoles(String username) {

	List<Role> roles = null;
	StringBuilder sb = new StringBuilder();

	sb.append("SELECT r.ID, r.ROLENAME ");
	sb.append("FROM MONITOR.APP_ROLE r ");
	sb.append("WHERE r.ID IN ( ");
	sb.append("SELECT ur.ROLE_ID ");
	sb.append("FROM MONITOR.APP_USER_ROLE ur ");
	sb.append("WHERE ur.USER_ID IN ( ");
	sb.append("SELECT u.ID ");
	sb.append("FROM MONITOR.APP_USER u ");
	sb.append("WHERE UPPER(u.USERNAME) = UPPER(:username))) ");

	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	namedParameters.addValue("username", username);

	try {
	    roles = namedParameterJdbcTemplate.query(sb.toString(), namedParameters, new RoleRowMapper());
	} catch (EmptyResultDataAccessException e) {
	    logger.warn(e.getMessage());
	}

	return roles;
    }

    public List<User> findAll() {
	List<User> users = new ArrayList<User>();
	String sql = "SELECT ID, USERNAME, PASSWORD FROM MONITOR.APP_USER ORDER BY USERNAME ASC";
	users = jdbcTemplate.query(sql, new UserRowMapper());
	return users;
    }

}
