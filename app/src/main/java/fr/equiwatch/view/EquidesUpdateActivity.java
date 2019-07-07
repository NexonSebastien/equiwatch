package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import fr.equiwatch.R;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.model.EquidesClass;

public class EquidesUpdateActivity extends MenuEquiwatch  {

    // propriétés
    private EquidesController equidesController;
    private EquidesClass equidesUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_equides_update, dynamicContent, true);
        super.onCreate(savedInstanceState);

        this.equidesController = EquidesController.getInstance(this.getBaseContext());
        EditText inpNomEquides = (EditText) findViewById(R.id.iptNomEquides);
        this.equidesUpdate = equidesController.getEquidesUpdate();
        inpNomEquides.setText(equidesUpdate.getNom());

        findViewById(R.id.btnEnclosUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomEquides = (EditText) findViewById(R.id.iptNomEquides);
                equidesUpdate.setNom(inpNomEquides.getText().toString());
                equidesController.updateEquides(equidesUpdate);

//                @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                Snackbar snackbarSupr = Snackbar.make(view, "Vous venez de modifier l'equidées : " + equidesUpdate.getNom(), Snackbar.LENGTH_LONG);
                View viewEquides = snackbarSupr.getView();
                viewEquides.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();

//              @todo refrech le fragment
                Intent nextAct = new Intent(equidesController.getContext(), EquidesActivity.class);
                startActivity(nextAct);
                finish();
            }
        });
    }
}
