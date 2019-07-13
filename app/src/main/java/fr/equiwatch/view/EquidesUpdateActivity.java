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
import fr.equiwatch.model.EquidesClass;

public class EquidesUpdateActivity extends MenuEquiwatch  {

    // propriétés
    /**
     * Equides controller
     */
    private EquidesController equidesController;

    /**
     * L'equides à modifier
     */
    private EquidesClass equidesUpdate;

    /**
     * Spinner enclos
     */
    private Spinner spinnerEnclos;

    /**
     * Spinner capteur
     */
    private Spinner spinnerCapteurs;

    /**
     * Lancement de la fonction au moment du lancement de l'activité (déclanché pas le click sur le stylo dans CapteursActivity)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_equides_update, dynamicContent, true);
        super.onCreate(savedInstanceState);

        this.equidesController = EquidesController.getInstance(this.getBaseContext());

        final EditText inpNomEquides = (EditText) findViewById(R.id.iptNomEquides);
        final Spinner spinEnclos = findViewById(R.id.spinnerIdEnclos);
        final Spinner spinCapteurs = findViewById(R.id.spinnerIdCapteurs);

        equidesUpdate = equidesController.getEquidesUpdate();

        inpNomEquides.setText(equidesUpdate.getNom());
        addItemsOnSpinnerEnclos(equidesUpdate.getIdEnclos());
        addItemsOnSpinnerCapteurs(equidesUpdate.getIdCapteur());

        findViewById(R.id.btnEquidesUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inpNomEquides.getText().toString().equals("")){
                    Snackbar snackbarSupr = Snackbar.make(view, R.string.equide_warning_label, Snackbar.LENGTH_LONG);
                    View viewEquide= snackbarSupr.getView();
                    viewEquide.setBackgroundResource(R.color.colorError);

//                    Permet de fermer le clavier si il est afficher sinon ouvre le clavier
                    final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    snackbarSupr.show();
                }
                else{
                    EditText inpNomEquides = (EditText) findViewById(R.id.iptNomEquides);
                    equidesUpdate.setNom(inpNomEquides.getText().toString());
                    equidesUpdate.setIdEnclos(spinEnclos.getSelectedItem().toString());
                    equidesUpdate.setIdCapteur(spinCapteurs.getSelectedItem().toString());
                    equidesController.updateEquides(equidesUpdate);

//                @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                    Snackbar snackbarSupr = Snackbar.make(view, R.string.equide_information_update + equidesUpdate.getNom(), Snackbar.LENGTH_LONG);
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
     * @param selectedItem
     */
    public void addItemsOnSpinnerEnclos(String selectedItem) {

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
        spinnerEnclos.setSelection(dataAdapter.getPosition(selectedItem));
    }

    /**
     * Incrémentation de la liste déroulante (spinner Capteurs)
     * @param selectedItem
     */
    public void addItemsOnSpinnerCapteurs(String selectedItem) {

        spinnerCapteurs = (Spinner) findViewById(R.id.spinnerIdCapteurs);
        List<String> list = new ArrayList<String>();
        ArrayList<CapteursClass> lesCapteurs = CapteursController.getInstance(null).getLesCapteurs();
        list.add("Vide");
        for (CapteursClass capteur: lesCapteurs) {
            list.add(capteur.getLabel());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCapteurs.setAdapter(dataAdapter);
        spinnerCapteurs.setSelection(dataAdapter.getPosition(selectedItem));
    }
}
