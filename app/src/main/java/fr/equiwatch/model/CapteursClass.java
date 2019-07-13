package fr.equiwatch.model;

import java.io.Serializable;

public class CapteursClass implements Serializable{

    // attributs
    private String id;
    private String label;
    private String type;

    public CapteursClass(String id, String label, String type){
        this.id = id;
        this.label = label;
        this.type = type;
    }

    public CapteursClass(String label, String type){
        this.id = "";
        this.label = label;
        this.type = type;
    }

    /**
     * Constructeur vide necéssaire pour que l'objet soit utilisable par Firestore
     */
    public CapteursClass(){
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
