package com.godeltech.gbf.model;

import lombok.Getter;

import java.util.List;

@Getter
public enum Country {
    BELARUS("Belarus", List.of("Brest", "Vitebsk", "Gomel", "Grodno", "Mogilev", "Minsk")),
    POLAND("Poland", List.of("Bialystok", "Gdansk", "Krakow", "Lodz", "Poznan", "Warsaw", "Wroclaw")),
    BULGARIA("Bulgaria", List.of("Sofia")),
    GEORGIA("Georgia", List.of("Tbilisi")),
    UK("Britain", List.of("London", "Manchester")),
    UKRAINE("Ukraine", List.of("Kyiv")),
    UAE("Emirates", List.of("Dubai"));
    private final List<String> cities;
    private final String countryName;

    Country(String countryName, List<String> cities) {
        this.countryName = countryName;
        this.cities = cities;
    }
}
