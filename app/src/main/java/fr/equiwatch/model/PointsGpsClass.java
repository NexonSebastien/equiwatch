package fr.equiwatch.model;

import java.io.Serializable;

public class PointsGpsClass implements Serializable {
    // attributs
    private double latitude;
    private double longitude;
    private int ordre;

    /**
     * Constructeur
     *
     * @param latitude  latitude du point
     * @param longitude longitude du point
     * @param ordre     ordre de création du point
     */
    public PointsGpsClass(double latitude, double longitude, int ordre) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ordre = ordre;
    }

    /**
     * Constructeur vide necéssaire pour que l'objet soit utilisable par Firestore
     */
    public PointsGpsClass(){};

    /**
     * Permet de récupérer la latitude du pointGps
     *
     * @return
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Permet de modifier la latitude du pointGps
     *
     * @return
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Permet de récupérer la longitude du pointGps
     *
     * @return
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Permet de modifier la longitude du pointGps
     *
     * @return
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Permet de récupérer l'ordre de création du pointGps
     *
     * @return
     */
    public int getOrdre() {
        return ordre;
    }

    /**
     * Permet de modifier l'ordre du pointGps
     *
     * @return
     */
    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }
}
