package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import fr.equiwatch.R;
import fr.equiwatch.controller.EquidesController;

public class EquidesCreateActivity extends MenuEquiwatch  {

    // propriétés
    private EquidesController equidesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_equides_create, dynamicContent, true);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_enclos_create);
        this.equidesController = EquidesController.getInstance(this);

        findViewById(R.id.btnEquidesCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomEquides = (EditText) findViewById(R.id.iptNomEquides);
                String nomEquides = inpNomEquides.getText().toString();

                EditText inpIdEnclos = (EditText) findViewById(R.id.iptIdEnclos);
                int idEnclos = Integer.parseInt(inpIdEnclos.getText().toString());

                EditText inpIdCapteur = (EditText) findViewById(R.id.iptIdCapteur);
                int idCapteur = Integer.parseInt(inpIdCapteur.getText().toString());

                equidesController.creerEquides(nomEquides,idEnclos,idCapteur);
//              @todo refrech le fragment

//              @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                Snackbar snackbarSupr = Snackbar.make(view, "Vous venez de créer l'équidés : " + nomEquides, Snackbar.LENGTH_LONG);
                View viewEquides = snackbarSupr.getView();
                viewEquides.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();
                Intent nextAct = new Intent(equidesController.getContext(), EquidesActivity.class);
                startActivity(nextAct);
                finish();
            }
        });
    }
}
