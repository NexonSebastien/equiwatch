package fr.equiwatch.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;

public class EnclosUpdateActivity extends AppCompatActivity {

    // propriétés
    private EnclosController enclosController;
    private EnclosClass enclosUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enclos_update);
        this.enclosController = EnclosController.getInstance(this);
        EditText inpNomEnclos = (EditText) findViewById(R.id.iptNomEnclos);
        this.enclosUpdate = enclosController.getEnclosUpdate();
        inpNomEnclos.setText(enclosUpdate.getLabel());

        findViewById(R.id.btnEnclosUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomEnclos = (EditText) findViewById(R.id.iptNomEnclos);
                enclosUpdate.setLabel(inpNomEnclos.getText().toString());
                enclosController.updateEnclos(enclosUpdate);

//              @todo refrech le fragment
                MenuEquiwatch.getMenuEquiwatch().simultationClickMenu(R.id.nav_enclos);

//              @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                Snackbar snackbarSupr = Snackbar.make(view, "Vous venez de modifier l'enclos : " + enclosUpdate.getLabel(), Snackbar.LENGTH_LONG);
                View viewEnclos = snackbarSupr.getView();
                viewEnclos.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();
                finish();
            }
        });
    }
}
