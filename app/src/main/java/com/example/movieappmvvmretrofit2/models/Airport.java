package com.example.movieappmvvmretrofit2.models;

public class Airport {
    private String IATA_code;
    private Town town;

    public Airport(String IATA_code, Town town, String name) {
        this.IATA_code = IATA_code;
        this.town = town;
        this.name = name;
    }

    private String name;

    public String getIATA_code() {
        return IATA_code;
    }

    public void setIATA_code(String IATA_code) {
        this.IATA_code = IATA_code;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
