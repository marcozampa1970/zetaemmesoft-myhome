package com.zetaemmesoft.myhome.integration.repository;

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

import com.zetaemmesoft.myhome.integration.dto.Switch;
import com.zetaemmesoft.myhome.integration.mapper.SwitchRowMapper;

@Repository
public class SwitchRepository {

    private static final Logger logger = LoggerFactory.getLogger(SwitchRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Switch> findAll() {
	List<Switch> dtos = new ArrayList<Switch>();
	String sql = "SELECT ID, VALUE, UNIT, ITEM_TYPE, TIME, NAME, NODE, TOPIC FROM MONITOR.SWITCH ORDER BY NODE ASC, ID ASC";
	dtos = jdbcTemplate.query(sql, new SwitchRowMapper());
	return dtos;
    }

    public Switch find(Integer switchId) {
	Switch dto = null;
	String sql = "SELECT ID, VALUE, UNIT, ITEM_TYPE, TIME, NAME, NODE, TOPIC FROM MONITOR.SWITCH WHERE ID = ? ORDER BY ID ASC";

	try {
	    dto = jdbcTemplate.queryForObject(sql, new Integer[] { switchId }, new SwitchRowMapper());
	} catch (EmptyResultDataAccessException e) {
	    logger.warn(e.getMessage());
	}

	return dto;
    }

    public void insert(Switch dto) {
	String sql = "INSERT INTO MONITOR.SWITCH (ID, VALUE, UNIT, ITEM_TYPE, TIME, NAME, NODE) VALUES (:switchId,:value,:unit,:type,:time,:name,:node)  ";
	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	namedParameters.addValue("switchId", dto.getId());
	namedParameters.addValue("value", dto.getValue());
	namedParameters.addValue("unit", dto.getUnit());
	namedParameters.addValue("type", dto.getType());
	namedParameters.addValue("time", dto.getTime());
	namedParameters.addValue("name", dto.getName());
	namedParameters.addValue("node", dto.getNode());
	namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public void update(Switch dto) {
	String sql = "UPDATE MONITOR.SWITCH SET VALUE=:value, UNIT=:unit, ITEM_TYPE=:type, NAME=:name, NODE=:node, TIME=:time WHERE ID = :switchId";
	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	namedParameters.addValue("switchId", dto.getId());
	namedParameters.addValue("value", dto.getValue());
	namedParameters.addValue("unit", dto.getUnit());
	namedParameters.addValue("type", dto.getType());
	namedParameters.addValue("time", dto.getTime());
	namedParameters.addValue("name", dto.getName());
	namedParameters.addValue("node", dto.getNode());
	namedParameterJdbcTemplate.update(sql, namedParameters);
    }
}
