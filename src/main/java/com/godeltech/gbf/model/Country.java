package com.godeltech.gbf.model;

import com.godeltech.gbf.LocaleMessageSource;

import java.util.List;

public enum Country {
    BELARUS("Belarus", List.of("Brest", "Vitebsk", "Gomel", "Grodno", "Mogilev", "Minsk")),

    POLAND("Poland", List.of("Bialystok", "Gdansk", "Krakow", "Lodz", "Poznan", "Warsaw", "Wroclaw")),

    BULGARIA("Bulgaria", List.of("Sofia")),

    GEORGIA("Georgia", List.of("Tbilisi")),

    UK("UK", List.of("London", "Manchester")),

    UKRAINE("Ukraine", List.of("Kyiv")),

    UAE("UAE", List.of("Dubai"));

    private final String countryNameDescription;
    private final List<String> citiesDescription;

    Country(String countryNameDescription, List<String> citiesDescription) {
        this.countryNameDescription = countryNameDescription;
        this.citiesDescription = citiesDescription;
    }

    public String getCountryName(LocaleMessageSource localeMessageSource) {
        return localeMessageSource.getLocaleMessage(this.countryNameDescription);
    }

    public List<String> getCities(LocaleMessageSource localeMessageSource) {
        return this.citiesDescription.stream().map(localeMessageSource::getLocaleMessage).toList();
    }
}
