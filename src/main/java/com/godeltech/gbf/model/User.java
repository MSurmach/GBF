package com.godeltech.gbf.model;

import java.util.Date;
import java.util.List;

public class User {
    private long id;
    private String username;
    private Country countryFrom;
    private Country countryTo;
    private Country cityFrom;
    private Country cityTo;
    private Date dateFrom;
    private Date dateTo;
    private List<Cargo> cargo;
}
