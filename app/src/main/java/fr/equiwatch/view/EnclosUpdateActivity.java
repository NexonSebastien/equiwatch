package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;
import fr.equiwatch.model.PointsGpsClass;

import static fr.equiwatch.view.EnclosCreateActivity.Get_Points_REQUEST;

public class EnclosUpdateActivity extends MenuEquiwatch  {

    // propriétés
    private EnclosController enclosController;
    private EnclosClass enclosUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos_update, dynamicContent, true);
        super.onCreate(savedInstanceState);
        this.enclosController = EnclosController.getInstance(this.getBaseContext());
        EditText inpNomEnclos = findViewById(R.id.iptNomEnclos);
        this.enclosUpdate = enclosController.getEnclosUpdate();

        // On insère le label de l'enclos dans le champs de texte
        inpNomEnclos.setText(enclosUpdate.getLabel());

        // On indique le nombre de points gps dont l'enclos est constitué
        TextView textView = findViewById(R.id.tv_nb_points);
        textView.setText( String.format(getString(R.string.txt_nb_points), enclosUpdate.getPointsGps().size()));

        /**
         * Lors du click sur btnEnclosUpdate, on récupère les données dans les champs et on met à jour le marker concerné
         */
        findViewById(R.id.btnEnclosUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText inpNomEnclos = findViewById(R.id.iptNomEnclos);
                enclosUpdate.setLabel(inpNomEnclos.getText().toString());
                enclosController.updateEnclos(enclosUpdate);

                Intent nextAct = new Intent(EnclosUpdateActivity.this, EnclosActivity.class);
                startActivity(nextAct);
                finish();
            }
        });

        /**
         * Lors du click sur bt_delimiter_enclos, ouvre la map afin de modifier la délimitation de enclosUpdate.
         */
        findViewById(R.id.bt_delimiter_enclos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EnclosUpdateActivity.this, MenuMapsActivity.class);
                intent.putExtra("id_key", view.getId()); // Set your ID as a Intent Extra
                startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == Get_Points_REQUEST && resultCode == RESULT_OK) {
            // Make sure the request was successful
            ArrayList<PointsGpsClass> result = (ArrayList<PointsGpsClass>) data.getSerializableExtra("listPointsGps");
            // The user picked a contact.
            // The Intent's data Uri identifies which contact was selected.
            setListPoints(result, enclosUpdate);

            // Do something with the contact here (bigger example below)
        }
    }

    /**
     * Affiche le nombre de point gps de l'enclos
     *
     * @param listPoints
     * @param enclos
     */
    public void setListPoints(ArrayList<PointsGpsClass> listPoints, EnclosClass enclos) {
        enclos.setPointsGps(listPoints);
        TextView textView = findViewById(R.id.tv_nb_points);
        textView.setText( String.format(getString(R.string.txt_nb_points), enclos.getPointsGps().size()));
        textView.setVisibility(View.VISIBLE);
    }
}
