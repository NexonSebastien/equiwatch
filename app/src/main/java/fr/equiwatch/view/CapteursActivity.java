package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import java.util.ArrayList;
import fr.equiwatch.R;
import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.model.CapteursClass;

public class CapteursActivity extends MenuEquiwatch  {

    // propriétés
    /**
     * Capteur controller
     */
    private CapteursController capteursController;

    /**
     * Capteur activity
     */
    private static CapteursActivity capteursActivity;

    /**
     * Listes des capteurs
     */
    private ArrayList<CapteursClass> lesCapteurs;

    /**
     * Lancement de la fonction au moment du lancement de l'activité (déclanché pas le click sur Mes capteurs dans MenuEquiwatch ou MenuMapsActivity)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_capteurs, dynamicContent, true);
        super.onCreate(savedInstanceState);

        capteursActivity = this;
        capteursController = CapteursController.getInstance(this);
        lesCapteurs = capteursController.getLesCapteurs();
        this.capteursController = CapteursController.getInstance(this);
        ListView lvListeCapteurs = findViewById(R.id.lvListeCapteurs);
        TextView textVide = findViewById(R.id.txtVide);
        if (lesCapteurs.size() != 0) {
            CapteursListAdapter adapter = new CapteursListAdapter(this, lesCapteurs);
            lvListeCapteurs.setAdapter(adapter);
        } else {
            textVide.setText(R.string.capteur_information_vide);
        }
        findViewById(R.id.imgBtnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextAct = new Intent(capteursActivity, CapteursCreateActivity.class);
                startActivity(nextAct);
                finish();
            }
        });
    }

    /**
     * Permet la recupération de capteursActivity
     * @return
     */
    public static CapteursActivity getCapteursActivity() {
        return capteursActivity;
    }
}