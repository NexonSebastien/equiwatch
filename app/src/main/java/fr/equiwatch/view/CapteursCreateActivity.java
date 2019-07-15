package fr.equiwatch.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import fr.equiwatch.R;
import fr.equiwatch.controller.CapteursController;

public class CapteursCreateActivity extends MenuEquiwatch  {

    // propriétés
    /**
     * Capteur controller
     */
    private CapteursController capteursController;
    /**
     * spinner type capteur
     */
    private Spinner spinner;

    /**
     * Lancement de la fonction au moment du lancement de l'activité (déclanché pas le click sur le + de CapteursActivity)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_capteurs_create, dynamicContent, true);
        super.onCreate(savedInstanceState);

        this.capteursController = CapteursController.getInstance(this);

        addItemsOnSpinner();

        findViewById(R.id.btnCapteursCreate).setOnClickListener(new View.OnClickListener() {
            /**
             * Lancement au moment du click sur le bouton créer
             * @param view
             */
            @Override
            public void onClick(View view) {
                EditText inpLabelCapteurs = (EditText) findViewById(R.id.iptLabelCapteurs);
                String labelCapteurs = inpLabelCapteurs.getText().toString();
                if(labelCapteurs.equals("")){
                    Snackbar snackbarSupr = Snackbar.make(view, R.string.capteur_warning_label, Snackbar.LENGTH_LONG);
                    View viewCapteurs = snackbarSupr.getView();
                    viewCapteurs.setBackgroundResource(R.color.colorError);

//                    Permet de fermer le clavier si il est afficher sinon ouvre le clavier
                    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    snackbarSupr.show();
                }
                else{
                    Spinner spinTypeCapteurs = findViewById(R.id.spinnerTypeCapteurs);
                    String typeCapteurs = spinTypeCapteurs.getSelectedItem().toString();

                    capteursController.creerCapteurs(labelCapteurs,typeCapteurs);

//                  @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                    Snackbar snackbarSupr = Snackbar.make(view, R.string.capteur_information_create + labelCapteurs, Snackbar.LENGTH_LONG);
                    View viewCapteurs = snackbarSupr.getView();
                    viewCapteurs.setBackgroundResource(R.color.colorPrimary);
                    snackbarSupr.show();

                    Intent nextAct = new Intent(capteursController.getContext(), CapteursActivity.class);
                    startActivity(nextAct);
                    finish();
                }
            }
        });
    }

    /**
     * Incrémentation de la liste déroulante (spinner type capteurs)
     */
    public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinnerTypeCapteurs);
        List<String> list = new ArrayList<String>();
        list.add("Vide");
        list.add("GPS");
        list.add("Hydraulique");
        list.add("Electrique");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }
}
