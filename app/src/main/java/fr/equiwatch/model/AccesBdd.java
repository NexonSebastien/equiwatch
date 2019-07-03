package fr.equiwatch.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.tools.AccesHTTP;
import fr.equiwatch.tools.AsyncResponse;

public class AccesBdd implements AsyncResponse {

    //constante
    private static final String SERVERADDR = "http://172.16.97.228/equiwatch/serveurEquiwatch.php"; //snexon Home
    private EnclosController enclosController;
    public AccesBdd() {
        enclosController = EnclosController.getInstance(null);
    }

    /**
     * retour du serveur distant
     * @param output
     */
    @Override
    public void processFinish(String output) {
        Log.d("serveur", "**********"+output);
        // decoupage message recus
        String[] message = output.split("%");
        // dans message[0] : "liste", "enreg", delete, "Erreur !"
        // dans message[1] : reste du message

        // s'il y a 2 case
        if(message.length > 1){
            if(message[0].equals("enreg")){
                Log.d("enreg","**********"+message[1]);
            }
            else{
                if(message[0].equals("listeEnclos")){
                    Log.d("listeEnclos","**********"+message[1]);
                    try {
                        JSONArray jSonData = new JSONArray(message[1]);
                        ArrayList<EnclosClass> lesEnclos = new ArrayList<EnclosClass>();
                        for(int i=0; i<jSonData.length();i++){
                            JSONObject data = new JSONObject(jSonData.get(i).toString());
                            int id = data.getInt("id");
                            String label = data.getString("label");
                            EnclosClass enclos = new EnclosClass(id,label);
                            lesEnclos.add(enclos);
                        }
                        enclosController.setLesEnclos(lesEnclos);
                    } catch (JSONException e) {
                        Log.d("Erreur !","**********Conversion JSON impossible"+e.toString());
                    }
                }
                else{
                    if(message[0].equals("Erreur !")){
                        Log.d("Erreur !","**********"+message[1]);
                    }
                }
            }
        }
    }

    public void envoie(String operation, JSONArray datasJson){
        AccesHTTP accesDatas = new AccesHTTP();

        // liens de délégation
        accesDatas.delegate = this;

        // Ajout parametre
        accesDatas.addParams("operation",operation);
        accesDatas.addParams("datas",datasJson.toString());
        // Appel au serveur
        accesDatas.execute(SERVERADDR);
    }
}
