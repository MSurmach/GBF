package com.godeltech.gbf.service.country.impl;

import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.repository.CountryRepository;
import com.godeltech.gbf.service.country.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findCountryByName(String countryName) {
        return countryRepository.findCountryByName(countryName);
    }
}
