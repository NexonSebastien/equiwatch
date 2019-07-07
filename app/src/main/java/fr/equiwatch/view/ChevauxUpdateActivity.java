package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import fr.equiwatch.R;
import fr.equiwatch.controller.ChevauxController;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.ChevauxClass;
import fr.equiwatch.model.EnclosClass;

public class ChevauxUpdateActivity extends MenuEquiwatch  {

    // propriétés
    private ChevauxController chevauxController;
    private ChevauxClass chevauxUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_chevaux_update, dynamicContent, true);
        super.onCreate(savedInstanceState);

        this.chevauxController = ChevauxController.getInstance(this.getBaseContext());
        EditText inpNomChevaux = (EditText) findViewById(R.id.iptNomChevaux);
        this.chevauxUpdate = chevauxController.getChevauxUpdate();
        inpNomChevaux.setText(chevauxUpdate.getNom());

        findViewById(R.id.btnChevauxUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomChevaux = (EditText) findViewById(R.id.iptNomChevaux);
                chevauxUpdate.setNom(inpNomChevaux.getText().toString());
                chevauxController.updateChevaux(chevauxUpdate);

//                @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                Snackbar snackbarSupr = Snackbar.make(view, "Vous venez de modifier le cheval : " + chevauxUpdate.getNom(), Snackbar.LENGTH_LONG);
                View viewChevaux = snackbarSupr.getView();
                viewChevaux.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();

//              @todo refrech le fragment
                Intent nextAct = new Intent(chevauxController.getContext(), ChevauxActivity.class);
                startActivity(nextAct);
                finish();
            }
        });
    }
}
