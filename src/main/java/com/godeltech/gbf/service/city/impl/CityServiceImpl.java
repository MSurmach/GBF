package com.godeltech.gbf.service.city.impl;

import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.repository.CityRepository;
import com.godeltech.gbf.service.city.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    @Override
    public List<City> findCitiesByCountryId(Integer countryId) {
        return cityRepository.findCitiesByCountry_CountryId(countryId);
    }

    @Override
    public City findCityByName(String name) {
        return cityRepository.findCityByName(name);
    }
}
