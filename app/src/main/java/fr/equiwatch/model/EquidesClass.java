package fr.equiwatch.model;

import java.io.Serializable;

public class EquidesClass implements Serializable{

    // attributs
    private String id;
    private String nom;
    private String idEnclos;
    private String idCapteur;

    public EquidesClass(String id, String nom, String idEnclos, String idCapteur){
        this.id = id;
        this.nom = nom;
        this.idEnclos = idEnclos;
        this.idCapteur = idCapteur;
    }

    public EquidesClass(String nom, String idEnclos, String idCapteur){
        this.id = "";
        this.nom = nom;
        this.idEnclos = idEnclos;
        this.idCapteur = idCapteur;
    }

    public EquidesClass(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdEnclos() {
        return idEnclos;
    }

    public void setIdEnclos(String idEnclos) {
        this.idEnclos = idEnclos;
    }

    public String getIdCapteur() {
        return idCapteur;
    }

    public void setIdCapteur(String idCapteur) {
        this.idCapteur = idCapteur;
    }
}
