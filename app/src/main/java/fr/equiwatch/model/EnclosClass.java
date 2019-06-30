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

    public EnclosClass(int id, String label){
        this.id = id;
        this.label = label;
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


    /**
     * Convertion de l'enclos en format JSONArray
     * @return
     */
    public JSONArray convertToJSONArray(){
        List liste = new ArrayList();
        liste.add(id);
        liste.add(label);

        return new JSONArray(liste);
    }
}
