package fr.equiwatch.model;

import java.io.Serializable;
import java.util.ArrayList;

public class EnclosClass implements Serializable{

    // attributs
    private String id;
    private String label;
    private ArrayList<PointsGpsClass> pointsGps;
    private ArrayList<String> listIdCapteur;

    public EnclosClass(String id, String label, ArrayList<PointsGpsClass> pointsGps, ArrayList<String> listIdCapteur){
        this.id = id;
        this.label = label;
        this.pointsGps = pointsGps;
        this.listIdCapteur = listIdCapteur;
    }

    public EnclosClass(String label, ArrayList<PointsGpsClass> pointsGps, ArrayList<String> listIdCapteur){
        this.label = label;
        this.pointsGps = pointsGps;
        this.listIdCapteur = listIdCapteur;
    }

    /**
     * Constructeur vide necéssaire pour que l'objet soit utilisable par Firestore
     */
    public EnclosClass(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<PointsGpsClass> getPointsGps() {
        return pointsGps;
    }

    public void setPointsGps(ArrayList<PointsGpsClass> pointsGps) {
        this.pointsGps = pointsGps;
    }

    @Override
    public String toString() {
        return this.getLabel();
    }

    public ArrayList<String> getListIdCapteur() {
        return listIdCapteur;
    }

    public void setListIdCapteur(ArrayList<String> listIdCapteur) {
        this.listIdCapteur = listIdCapteur;
    }
}
