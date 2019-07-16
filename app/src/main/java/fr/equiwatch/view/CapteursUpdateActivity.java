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
import fr.equiwatch.model.CapteursClass;

public class CapteursUpdateActivity extends MenuEquiwatch  {

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
     * le capteur que l'ont modifie
     */
    private CapteursClass capteursUpdate;

    /**
     * Lancement de la fonction au moment du lancement de l'activité (déclanché pas le click sur le stylo dans CapteursActivity)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_capteurs_update, dynamicContent, true);
        super.onCreate(savedInstanceState);

        this.capteursController = CapteursController.getInstance(this.getBaseContext());



        final EditText inpLabelCapteurs = (EditText) findViewById(R.id.iptLabelCapteurs);
        final Spinner spinTypeCapteurs = findViewById(R.id.spinnerTypeCapteurs);

        capteursUpdate = capteursController.getCapteursUpdate();

        inpLabelCapteurs.setText(capteursUpdate.getLabel());
        addItemsOnSpinner(capteursUpdate.getType());

        findViewById(R.id.btnCapteursUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inpLabelCapteurs.getText().toString().equals("")){
                    Snackbar snackbarSupr = Snackbar.make(view, R.string.capteur_warning_label, Snackbar.LENGTH_LONG);
                    View viewCapteurs = snackbarSupr.getView();
                    viewCapteurs.setBackgroundResource(R.color.colorError);

//                    Permet de fermer le clavier si il est afficher sinon ouvre le clavier
                    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    snackbarSupr.show();
                }
                else{
                    capteursUpdate.setLabel(inpLabelCapteurs.getText().toString());
                    capteursUpdate.setType(spinTypeCapteurs.getSelectedItem().toString());

                    capteursController.updateCapteurs(capteursUpdate);

//                  @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                    Snackbar snackbarSupr = Snackbar.make(view, R.string.capteur_information_update + capteursUpdate.getLabel(), Snackbar.LENGTH_LONG);
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
    public void addItemsOnSpinner(String selectedItem) {

        spinner = (Spinner) findViewById(R.id.spinnerTypeCapteurs);
        List<String> list = capteursController.getListTypeCapteur();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setSelection(dataAdapter.getPosition(selectedItem));
    }
}
