package com.godeltech.gbf.service.city;

import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;

import java.util.List;

public interface CityService {
    List<City> findCitiesByCountry(Country country);

    City findCityByName(String name);

    List<City> findAll();
}
