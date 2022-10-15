package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findCitiesByCountry_Id(Integer countryId);

    City findCityByName(String cityName);
}
