package fr.equiwatch.controller;

import android.content.Context;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

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
    private static Context context;

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

    public void creerEnclos(String label){
        int id = 0;
        enclos = new EnclosClass(id, label);
        lesEnclos.add(enclos);
        accesBdd.envoie("createEnclos", enclos.convertToJSONArray());
    }

    public void deleteEnclos(EnclosClass unEnclos){
        accesBdd.envoie("deleteEnclos", unEnclos.convertToJSONArray());
        lesEnclos.remove(unEnclos);
    }
    public void setEnclos(EnclosClass enclos){
        EnclosController.enclos = enclos;
    }

    public ArrayList<EnclosClass> getLesEnclos() {
        return lesEnclos;
    }

    public void setLesEnclos(ArrayList<EnclosClass> lesEnclos) {
        EnclosController.lesEnclos = lesEnclos;
    }


}


