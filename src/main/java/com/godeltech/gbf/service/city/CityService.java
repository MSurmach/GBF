package com.godeltech.gbf.service.city;

import com.godeltech.gbf.model.db.City;

import java.util.List;

public interface CityService {
    List<City> findCitiesByCountryId(Integer countryId);

    City findCityByName(String name);
}
