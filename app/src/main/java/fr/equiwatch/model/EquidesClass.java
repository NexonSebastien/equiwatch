package fr.equiwatch.model;

import java.io.Serializable;

public class EquidesClass implements Serializable{

    // attributs
    private String id;
    private String nom;
    private int idEnclos;
    private int idCapteur;

    public EquidesClass(String id, String nom, int idEnclos, int idCapteur){
        this.id = id;
        this.nom = nom;
        this.idEnclos = idEnclos;
        this.idCapteur = idCapteur;
    }

    public EquidesClass(String nom, int idEnclos, int idCapteur){
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

    public int getIdEnclos() {
        return idEnclos;
    }

    public void setIdEnclos(int idEnclos) {
        this.idEnclos = idEnclos;
    }

    public int getIdCapteur() {
        return idCapteur;
    }

    public void setIdCapteur(int idCapteur) {
        this.idCapteur = idCapteur;
    }
}
