package com.godeltech.gbf.service.country;

import com.godeltech.gbf.model.db.Country;

import java.util.List;

public interface CountryService {
    List<Country> findAll();
    Country findCountryByName(String countryName);
}
