package com.chensy.socialnetwork.dao.impl;

import com.chensy.socialnetwork.dao.CountryDao;
import com.chensy.socialnetwork.mapper.CountryMapper;
import com.chensy.socialnetwork.model.Country;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CountryDaoImpl implements CountryDao {

    private static final String SQL_SELECT_ALL_COUNTRIES = "SELECT id as country_id, name as country_name FROM countries ";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CountryDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Country> getAllCountries() {
        return namedParameterJdbcTemplate.query(SQL_SELECT_ALL_COUNTRIES, new CountryMapper());
    }
}
