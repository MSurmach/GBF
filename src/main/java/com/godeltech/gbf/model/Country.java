package com.godeltech.gbf.model;

import com.godeltech.gbf.LocaleMessageSource;
import lombok.Getter;

import java.util.List;

@Getter
public enum Country {
    BELARUS("Belarus", List.of("Brest", "Vitebsk", "Gomel", "Grodno", "Mogilev", "Minsk")),

    POLAND("Poland", List.of("Bialystok", "Gdansk", "Krakow", "Lodz", "Poznan", "Warsaw", "Wroclaw")),

    BULGARIA("Bulgaria", List.of("Sofia")),

    GEORGIA("Georgia", List.of("Tbilisi")),

    UK("UK", List.of("London", "Manchester")),

    UKRAINE("Ukraine", List.of("Kyiv")),

    UAE("UAE", List.of("Dubai"));

    private final String countryName;

    private final List<String> cities;

    Country(String countryName, List<String> citiesDescription) {
        this.countryName = countryName;
        this.cities = citiesDescription;
    }

    public String getCountryName(LocaleMessageSource localeMessageSource) {
        return localeMessageSource.getLocaleMessage(this.countryName);
    }

    public List<String> getCities(LocaleMessageSource localeMessageSource) {
        return this.cities.stream().map(localeMessageSource::getLocaleMessage).toList();
    }
}
