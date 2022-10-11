package com.godeltech.gbf.repository;

import com.godeltech.gbf.model.db.City;
import com.godeltech.gbf.model.db.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    List<City> findAllByCountry(Country country);
}
