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
    public List<City> findByCountry(String countryName) {
        return cityRepository.findAllByCountryName(countryName);
    }
}
