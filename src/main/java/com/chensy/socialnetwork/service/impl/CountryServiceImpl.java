package com.chensy.socialnetwork.service.impl;

import com.chensy.socialnetwork.dao.CountryDao;
import com.chensy.socialnetwork.model.Country;
import com.chensy.socialnetwork.service.CountryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryDao countryDao;

    public CountryServiceImpl(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public List<Country> getAllCountries() {
        return countryDao.getAllCountries();
    }
}
