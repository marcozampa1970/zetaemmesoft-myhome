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

import com.zetaemmesoft.myhome.integration.dto.Sensor;
import com.zetaemmesoft.myhome.integration.mapper.SensorRowMapper;

@Repository
public class SensorRepository {

    private static final Logger logger = LoggerFactory.getLogger(SensorRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Sensor> findAll() {
	List<Sensor> dtos = new ArrayList<Sensor>();
	String sql = "SELECT ID, VALUE, UNIT, ITEM_TYPE, TIME, NAME, NODE, TOPIC FROM MONITOR.SENSOR ORDER BY NODE ASC, ID ASC";
	dtos = jdbcTemplate.query(sql, new SensorRowMapper());
	return dtos;
    }

    public Sensor find(Integer sensorId) {
	Sensor dto = null;
	String sql = "SELECT ID, VALUE, UNIT, ITEM_TYPE, TIME, NAME, NODE, TOPIC FROM MONITOR.SENSOR WHERE ID = ? ORDER BY ID ASC";

	try {
	    dto = jdbcTemplate.queryForObject(sql, new Integer[] { sensorId }, new SensorRowMapper());
	} catch (EmptyResultDataAccessException e) {
	    logger.warn(e.getMessage());
	}

	return dto;
    }

    public int insert(Sensor dto) {
	String sql = "INSERT INTO MONITOR.SENSOR (ID, VALUE, UNIT, ITEM_TYPE, TIME, NAME, NODE) VALUES (:sensorId,:value,:unit,:type,:time,:name,:node)  ";
	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	namedParameters.addValue("sensorId", dto.getId());
	namedParameters.addValue("value", dto.getValue());
	namedParameters.addValue("unit", dto.getUnit());
	namedParameters.addValue("type", dto.getType());
	namedParameters.addValue("time", dto.getTime());
	namedParameters.addValue("name", dto.getName());
	namedParameters.addValue("node", dto.getNode());
	return namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public int update(Sensor dto) {
	String sql = "UPDATE MONITOR.SENSOR SET VALUE=:value, UNIT=:unit, ITEM_TYPE=:type, NAME=:name, NODE=:node, TIME=:time WHERE ID = :sensorId";
	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	namedParameters.addValue("sensorId", dto.getId());
	namedParameters.addValue("value", dto.getValue());
	namedParameters.addValue("unit", dto.getUnit());
	namedParameters.addValue("type", dto.getType());
	namedParameters.addValue("time", dto.getTime());
	namedParameters.addValue("name", dto.getName());
	namedParameters.addValue("node", dto.getNode());
	return namedParameterJdbcTemplate.update(sql, namedParameters);
    }
}
