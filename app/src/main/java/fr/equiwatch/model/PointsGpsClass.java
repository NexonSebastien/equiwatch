package fr.equiwatch.model;

import java.io.Serializable;

public class PointsGpsClass implements Serializable {
    // attributs
    private double latitude;
    private double longitude;
    private int ordre;

    public PointsGpsClass(double latitude, double longitude, int ordre) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ordre = ordre;
    }

    public PointsGpsClass(){};

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
