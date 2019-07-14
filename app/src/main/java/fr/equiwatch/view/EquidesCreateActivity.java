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
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.model.CapteursClass;
import fr.equiwatch.model.EnclosClass;

public class EquidesCreateActivity extends MenuEquiwatch  {

    // propriétés
    /**
     * Equides controller
     */
    private EquidesController equidesController;

    /**
     * Enclos controller
     */
    private EnclosController enclosController;

    /**
     * Spinner enclos
     */
    private Spinner spinnerEnclos;

    /**
     * Lancement de la fonction au moment du lancement de l'activité (déclanché pas le click sur le + de EquideActivity)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_equides_create, dynamicContent, true);
        super.onCreate(savedInstanceState);

        this.equidesController = EquidesController.getInstance(this);

        addItemsOnSpinnerEnclos();
        addItemsOnSpinnerCapteurs();

        findViewById(R.id.btnEquidesCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomEquides = (EditText) findViewById(R.id.iptNomEquides);
                String nomEquides = inpNomEquides.getText().toString();
                if(nomEquides.equals("")){
                    Snackbar snackbarSupr = Snackbar.make(view, R.string.equide_warning_label, Snackbar.LENGTH_LONG);
                    View viewCapteurs = snackbarSupr.getView();
                    viewCapteurs.setBackgroundResource(R.color.colorError);

//                    Permet de fermer le clavier si il est afficher sinon ouvre le clavier
                    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    snackbarSupr.show();
                }
                else{
                    Spinner spinEnclos = findViewById(R.id.spinnerIdEnclos);
                    String enclos = spinEnclos.getSelectedItem().toString();

                    Spinner spinCapteur = findViewById(R.id.spinnerIdCapteurs);
                    String capteurs = spinCapteur.getSelectedItem().toString();



                    equidesController.creerEquides(nomEquides,enclos,capteurs);

//                  @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                    Snackbar snackbarSupr = Snackbar.make(view, R.string.equide_information_create+ nomEquides, Snackbar.LENGTH_LONG);
                    View viewEquides = snackbarSupr.getView();
                    viewEquides.setBackgroundResource(R.color.colorPrimary);
                    snackbarSupr.show();
                    Intent nextAct = new Intent(equidesController.getContext(), EquidesActivity.class);
                    startActivity(nextAct);
                    finish();
                }
            }
        });
    }

    /**
     * Incrémentation de la liste déroulante (spinner Enclos)
     */
    public void addItemsOnSpinnerEnclos() {

        spinnerEnclos = (Spinner) findViewById(R.id.spinnerIdEnclos);
        List<String> list = new ArrayList<String>();
        list.add("Vide");
        ArrayList<EnclosClass> lesEnclos = EnclosController.getInstance(null).getLesEnclos();
        for (EnclosClass enclo: lesEnclos) {
            list.add(enclo.getLabel());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEnclos.setAdapter(dataAdapter);
    }

    /**
     * Incrémentation de la liste déroulante (spinner Capteurs)
     */
    public void addItemsOnSpinnerCapteurs() {

        /**
         * Spinner enclos
         */
        Spinner spinnerCapteurs = findViewById(R.id.spinnerIdCapteurs);
        List<String> list = new ArrayList<>();
        ArrayList<CapteursClass> lesCapteurs = CapteursController.getInstance(null).getLesCapteurs();
        list.add("Vide");
        for (CapteursClass capteur: lesCapteurs) {
            if (capteur.getType().equals("GPS")) {
                list.add(capteur.getLabel());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCapteurs.setAdapter(dataAdapter);
    }
}
