package fr.equiwatch.model;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//inport java.util.Date;

public class EnclosClass implements Serializable{

    // attributs
    private int id;
    private String label;
    private ArrayList<PointsGpsClass> pointsGps;

    public EnclosClass(int id, String label, ArrayList<PointsGpsClass> pointsGps){
        this.id = id;
        this.label = label;
        this.pointsGps = pointsGps;
    }

    public EnclosClass(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
