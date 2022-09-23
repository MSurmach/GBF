package com.godeltech.gbf.model;

import com.godeltech.gbf.LocalMessageSource;
import lombok.Getter;

import java.util.List;

@Getter
public enum Country {
    BELARUS("Belarus", List.of("Brest", "Vitebsk", "Gomel", "Grodno", "Mogilev", "Minsk")),
    POLAND("Poland", List.of("Bialystok", "Gdansk", "Krakow", "Lodz", "Poznan", "Warsaw", "Wroclaw")),
    BULGARIA("Bulgaria", List.of("Sofia")),
    GEORGIA("Georgia", List.of("Tbilisi")),
    UAE("UAE", List.of("Dubai")),
    UKRAINE("Ukraine", List.of("Kyiv")),
    UK("UK", List.of("London", "Manchester")),
    LITHUANIA("Lithuania", List.of("Vilnius"));


    private final String label;

    private final List<String> cities;

    Country(String label, List<String> citiesDescription) {
        this.label = label;
        this.cities = citiesDescription;
    }

    public String getLocalLabel(LocalMessageSource localMessageSource) {
        return localMessageSource.getLocaleMessage(this.label);
    }

    public List<String> getLocalCities(LocalMessageSource localMessageSource) {
        return this.cities.stream().map(localMessageSource::getLocaleMessage).toList();
    }
}
