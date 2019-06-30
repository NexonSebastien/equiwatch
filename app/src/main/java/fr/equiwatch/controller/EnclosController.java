package fr.equiwatch.controller;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;

import java.util.ArrayList;

import fr.equiwatch.model.AccesBdd;
import fr.equiwatch.model.EnclosClass;
import fr.equiwatch.view.EnclosActivity;
import fr.equiwatch.view.MainActivity;

public final class EnclosController {

    private static EnclosController instance = null;
    private static EnclosClass enclos;
    private static ArrayList<EnclosClass> lesEnclos =  new ArrayList<EnclosClass>();
    private static AccesBdd accesBdd;
    private static  Context context;

    /**
     * constructeur private
     */
    private EnclosController(){
        super();
    }

    public static final EnclosController getInstance(Context context){
        if(context != null){
            EnclosController.context = context;
        }
        if(EnclosController.instance == null){
            EnclosController.instance = new EnclosController();
            accesBdd = new AccesBdd();
            accesBdd.envoie("listeEnclos", new JSONArray());
        }
        return EnclosController.instance;
    }

    public void creerEnclos(int id, String label){
        enclos = new EnclosClass(id, label);
        lesEnclos.add(enclos);
        accesBdd.envoie("enreg", enclos.convertToJSONArray());
    }

    public void deleteEnclos(EnclosClass unEnclos){
        accesBdd.envoie("delete", unEnclos.convertToJSONArray());
        lesEnclos.remove(unEnclos);
    }
    public void setEnclos(EnclosClass enclos){
        EnclosController.enclos = enclos;
        ((EnclosActivity)context).recupEnclos();
    }

    public ArrayList<EnclosClass> getLesEnclos() {
        return lesEnclos;
    }

    public void setLesEnclos(ArrayList<EnclosClass> lesEnclos) {
        EnclosController.lesEnclos = lesEnclos;
    }


}


