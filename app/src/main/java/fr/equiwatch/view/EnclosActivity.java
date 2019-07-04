package fr.equiwatch.view;

<<<<<<< HEAD
import android.os.Bundle;
import android.widget.ListView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;

public class EnclosActivity extends MenuEquiwatch {

    // propriétés
    private EnclosController enclosController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos, dynamicContent, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_enclos_create);
        this.enclosController = EnclosController.getInstance(this);
        findViewById(R.id.btnEnclosCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomEnclos = (EditText) findViewById(R.id.iptNomEnclos);
                String nomEnclos = inpNomEnclos.getText().toString();
//                Log.v("***********",nomEnclos);
                enclosController.creerEnclos(nomEnclos);
//              @todo refrech le fragment
                MenuEquiwatch.getMenuEquiwatch().simultationClickMenu(R.id.nav_enclos);

//              @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                Snackbar snackbarSupr = Snackbar.make(view, "Vous venez de créer l'enclos : " + nomEnclos, Snackbar.LENGTH_LONG);
                View viewEnclos = snackbarSupr.getView();
                viewEnclos.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();
                finish();
            }
        });
    }
}
