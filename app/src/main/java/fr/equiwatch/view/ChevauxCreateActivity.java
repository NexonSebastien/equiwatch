package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import fr.equiwatch.R;
import fr.equiwatch.controller.ChevauxController;
import fr.equiwatch.controller.EnclosController;

public class ChevauxCreateActivity extends MenuEquiwatch  {

    // propriétés
    private ChevauxController chevauxController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_chevaux_create, dynamicContent, true);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_enclos_create);
        this.chevauxController = ChevauxController.getInstance(this);
        chevauxController.setLastInsertId();

        findViewById(R.id.btnChevauxCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomChevaux = (EditText) findViewById(R.id.iptNomChevaux);
                String nomChevaux = inpNomChevaux.getText().toString();

                EditText inpIdEnclos = (EditText) findViewById(R.id.iptIdEnclos);
                int idEnclos = Integer.parseInt(inpIdEnclos.getText().toString());

                EditText inpIdCapteur = (EditText) findViewById(R.id.iptIdCapteur);
                int idCapteur = Integer.parseInt(inpIdCapteur.getText().toString());

                chevauxController.creerChevaux(nomChevaux,idEnclos,idCapteur);
//              @todo refrech le fragment

//              @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                Snackbar snackbarSupr = Snackbar.make(view, "Vous venez de créer le cheval : " + nomChevaux, Snackbar.LENGTH_LONG);
                View viewChevaux = snackbarSupr.getView();
                viewChevaux.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();
                Intent nextAct = new Intent(chevauxController.getContext(), ChevauxActivity.class);
                startActivity(nextAct);
                finish();
            }
        });
    }
}
