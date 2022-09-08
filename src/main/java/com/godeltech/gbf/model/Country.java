package com.godeltech.gbf.model;

import lombok.Getter;

import java.util.List;

@Getter
public enum Country {
    BELARUS(List.of("Brest", "Vitebsk", "Gomel", "Grodno", "Mogilev", "Minsk")),
    POLAND(List.of("Bialystok", "Gdansk", "Krakow", "Lodz", "Poznan", "Warsaw", "Wroclaw")),
    BULGARIA(List.of("Sofia")),
    GEORGIA(List.of("Tbilisi")),
    UK(List.of("London", "Manchester")),
    UKRAINE(List.of("Kyiv"));
    private final List<String> cities;

    Country(List<String> cities) {
        this.cities = cities;
    }
}
