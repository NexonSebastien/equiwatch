package fr.equiwatch.model;

import java.io.Serializable;

public class PointsGpsClass implements Serializable {
    // attributs
    private int id;
//    private int idEnclos;
    private double latitude;
    private double longitude;
    private int ordre;

    public PointsGpsClass(int id, double latitude, double longitude, int ordre) {
        this.id = id;
//        this.idEnclos = idEnclos;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ordre = ordre;
    }

    public PointsGpsClass(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public int getIdEnclos() {
//        return idEnclos;
//    }
//
//    public void setIdEnclos(int idEnclos) {
//        this.idEnclos = idEnclos;
//    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }
}
