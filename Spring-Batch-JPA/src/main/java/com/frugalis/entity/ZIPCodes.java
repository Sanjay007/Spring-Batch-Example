package com.frugalis.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ZIPCodes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String zipCode ;
    private String cityName;
    private String stateName;

    public ZIPCodes(String zipCode, String cityName, String stateName) {
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.stateName = stateName;
    }

    public ZIPCodes(Long id, String zipCode, String cityName, String stateName) {
        this.id = id;
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.stateName = stateName;
    }
    public ZIPCodes(){}
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
