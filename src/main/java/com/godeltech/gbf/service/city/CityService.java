package com.godeltech.gbf.service.city;

import com.godeltech.gbf.model.db.City;

import java.util.List;

public interface CityService {
    List<City> findByCountry(String countryName);
}
