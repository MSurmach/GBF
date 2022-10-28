package com.godeltech.gbf.service.city.impl;

import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.repository.CityRepository;
import com.godeltech.gbf.service.city.CityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    @Override
    @Transient
    public List<City> findCitiesByCountry(Country country) {
//        return cityRepository.findCitiesByCountry_Id(country.getId());
        return cityRepository.findAll();
    }

    @Override
    public City findCityByName(String name) {
        return cityRepository.findCityByName(name);
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
