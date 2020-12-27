package com.zetaemmesoft.myhome.integration.repository;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.zetaemmesoft.myhome.integration.dto.ItemType;
import com.zetaemmesoft.myhome.integration.mapper.ItemTypeRowMapper;

@Repository
public class ItemTypeRepository {

    private static final Logger logger = LoggerFactory.getLogger(ItemTypeRepository.class);

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ItemType find(String name) {
	ItemType dto = null;

	String sql = "SELECT ID, NAME FROM MONITOR.ITEM_TYPE WHERE UPPER(NAME) = UPPER(:name)";

	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	namedParameters.addValue("name", name);

	try {
	    dto = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new ItemTypeRowMapper());
	} catch (EmptyResultDataAccessException e) {
	    logger.warn(e.getMessage());
	}

	return dto;
    }
    
    public ItemType find(int id) {
	ItemType dto = null;

	String sql = "SELECT ID, NAME FROM MONITOR.ITEM_TYPE WHERE ID = :id";

	MapSqlParameterSource namedParameters = new MapSqlParameterSource();
	namedParameters.addValue("id", id);

	try {
	    dto = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new ItemTypeRowMapper());
	} catch (EmptyResultDataAccessException e) {
	    logger.warn(e.getMessage());
	}

	return dto;
    }

}
