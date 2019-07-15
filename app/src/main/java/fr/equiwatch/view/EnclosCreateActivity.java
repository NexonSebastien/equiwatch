package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.equiwatch.R;
import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.CapteursClass;
import fr.equiwatch.model.EnclosClass;
import fr.equiwatch.model.PointsGpsClass;

public class EnclosCreateActivity extends MenuEquiwatch {

    // propriétés
    private EnclosController enclosController;
    private ArrayList<PointsGpsClass> listPoints = new ArrayList<>();
    static final int Get_Points_REQUEST = 1;  // The request code
    private ArrayList<CapteursClass> listCapteurs;
    private ArrayList<CapteursClass> listEnclosCapteur = new ArrayList<>();
    private ArrayAdapter<CapteursClass> listEnclosCapteurAdapter;
    private ListView listCapteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos_create, dynamicContent, true);
        super.onCreate(savedInstanceState);
        this.enclosController = EnclosController.getInstance(this);

        setEventFormButton();
        listCapteurs = CapteursController.getInstance(null).getLesCapteurs();

        // On veut tout les capteurs sauf GPS
        for (CapteursClass capteur : listCapteurs) {
            if(!capteur.getType().equals("GPS")) {
                listEnclosCapteur.add(capteur);
            }
        }

        listCapteur = findViewById(R.id.enclos_list_capteurs);
        ListView listViewEnclosCapteurs = findViewById(R.id.enclos_list_capteurs);
        listEnclosCapteurAdapter = new ArrayAdapter<>(this, R.layout.enclos_create_list_row, listEnclosCapteur);
        listViewEnclosCapteurs.setAdapter(listEnclosCapteurAdapter);
        listViewEnclosCapteurs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
            if (listCapteur.isItemChecked(pos)) {
                listCapteur.setItemChecked(pos, true);
            } else {
                listCapteur.setItemChecked(pos, false);
            }
            }
        });
    }

    /**
     * Créer les evenements onClick des boutons
     */
    public void setEventFormButton() {
        findViewById(R.id.btnEnclosCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomEnclos = findViewById(R.id.iptNomEnclos);
                String nomEnclos = inpNomEnclos.getText().toString();
                if(nomEnclos.length() > 0 && !listPoints.isEmpty()) {
                    ArrayList<String> listIdCapteurs = new ArrayList<>();

                    SparseBooleanArray checkedItemPositionsList = listCapteur.getCheckedItemPositions();
                    for (int i=0; i < checkedItemPositionsList.size(); i++) {
                        if (checkedItemPositionsList.valueAt(i)) {
                            listIdCapteurs.add(listEnclosCapteur.get(checkedItemPositionsList.keyAt(i)).getId());
                        }
                    }

                    EnclosClass enclos = new EnclosClass(nomEnclos, listPoints, listIdCapteurs);
                    enclosController.creerEnclos(enclos);
                    enclosController.getLesEnclos().add(enclos);

                    Intent nextAct = new Intent(EnclosCreateActivity.this, EnclosActivity.class);
                    startActivity(nextAct);
                    finish();
                } else {
                    Snackbar snackbarSupr = Snackbar.make(view, R.string.form_empty, Snackbar.LENGTH_SHORT);
                    View viewEnclos = snackbarSupr.getView();
                    viewEnclos.setBackgroundResource(R.color.colorRed);
                    snackbarSupr.show();
                }
            }
        });

        findViewById(R.id.bt_delimiter_enclos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(enclosController.getContext(), MenuMapsActivity.class);
                intent.putExtra("id_key", 1); // Set your ID as a Intent Extra
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Get_Points_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                ArrayList<PointsGpsClass> result = (ArrayList<PointsGpsClass>) data.getSerializableExtra("listPointsGps");
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                setListPoints(result);
            }
        }
    }

    /**
     * Affiche le nombre de point gps de l'enclos
     *
     * @param listPoints
     */
    public void setListPoints(ArrayList<PointsGpsClass> listPoints) {
        this.listPoints = listPoints;
        TextView textView = findViewById(R.id.tv_nb_points);
        textView.setText( String.format(getString(R.string.txt_nb_points), this.listPoints.size()));
        textView.setVisibility(View.VISIBLE);
    }
}
