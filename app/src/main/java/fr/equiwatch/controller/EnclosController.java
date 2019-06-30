package fr.equiwatch.controller;

import android.content.Context;

import org.json.JSONArray;

import fr.equiwatch.model.AccesBdd;
import fr.equiwatch.model.EnclosClass;

public final class EnclosController {

    private static EnclosController instance = null;
    private EnclosClass enclos;
    private static AccesBdd accesBdd;

    /**
     * constructeur private
     */
    private EnclosController(){
        super();
    }

    public static final EnclosController getInstance(Context context){
        if(EnclosController.instance == null){
            EnclosController.instance = new EnclosController();
            accesBdd = new AccesBdd();
            accesBdd.envoie("dernier", new JSONArray());
        }
        return EnclosController.instance;
    }

    public void creerEnclos(String label){
        enclos = new EnclosClass(label);
        accesBdd.envoie("enreg", enclos.convertToJSONArray());
    }
}


