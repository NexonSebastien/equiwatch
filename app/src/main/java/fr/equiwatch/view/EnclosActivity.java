package fr.equiwatch.view;

import android.os.Bundle;
import android.widget.ListView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;

public class EnclosActivity extends MenuEquiwatch {

    // propriétés
    private EnclosController enclosController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos, dynamicContent, true);
        super.onCreate(savedInstanceState);
        init();
    }

    private void init(){
        this.enclosController = EnclosController.getInstance(this);
        //Log.d("EnclosController", "**********"+this.enclosController.toString());
        creerListe();

    }

    /**
     * Création de la liste des enclos
     */
    private void creerListe()
    {
        ArrayList<EnclosClass> lesEnclos = enclosController.getLesEnclos();
        if(lesEnclos != null){
            ListView lvListeEnclos = findViewById(R.id.lvListeEnclos);
            EnclosListAdapter adapter = new EnclosListAdapter(this, lesEnclos);
            lvListeEnclos.setAdapter(adapter);
        }
        else{
            TextView textVide = (TextView) findViewById(R.id.txtVide);
            textVide.setText("Vous n'avez aucun enclos pour le moment cliqué sur le plus pour en ajouter.");
        }
    }

    public void recupEnclos(){

    }
}
