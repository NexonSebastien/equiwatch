package fr.equiwatch.model;

import android.util.Log;

import org.json.JSONArray;

import fr.equiwatch.tools.AccesHTTP;
import fr.equiwatch.tools.AsyncResponse;

public class AccesBdd implements AsyncResponse {

    //constante
    private static final String SERVERADDR = "http://192.168.1.38/equiwatch/serveurEquiwatch.php"; //snexon Home

    public AccesBdd() {
        super();
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
        // dans message[0] : "dernier", "enreg", "Erreur !"
        // dans message[1] : reste du message

        // s'il y a 2 case
        if(message.length > 1){
            if(message[0].equals("enreg")){
                Log.d("enreg","**********"+message[1]);
            }
            else{
                if(message[0].equals("dernier")){
                    Log.d("dernier","**********"+message[1]);
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
