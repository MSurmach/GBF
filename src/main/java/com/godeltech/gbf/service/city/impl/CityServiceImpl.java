package com.godeltech.gbf.service.city.impl;

import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import com.godeltech.gbf.repository.CityRepository;
import com.godeltech.gbf.service.city.CityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {
    private CityRepository cityRepository;

    @Override
    @Transient
    public List<City> findCitiesByCountry(Country country) {
        log.info("Find all cities");
        return cityRepository.findAll();
    }

    @Override
    public City findCityByName(String name) {
        log.info("Find city by name : {}",name);
        return cityRepository.findCityByName(name);
    }

    @Override
    public List<City> findAll() {
        log.info("Find all cities ");
        return cityRepository.findAll();
    }
}
