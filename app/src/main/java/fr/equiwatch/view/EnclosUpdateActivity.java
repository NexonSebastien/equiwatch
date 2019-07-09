package fr.equiwatch.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

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

//                @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                Snackbar snackbarSupr = Snackbar.make(view, "Vous venez de modifier l'enclos : " + enclosUpdate.getLabel(), Snackbar.LENGTH_LONG);
                View viewEnclos = snackbarSupr.getView();
                viewEnclos.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();

                Intent nextAct = new Intent(enclosController.getContext(), EnclosActivity.class);
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
                Intent intent = new Intent(enclosController.getContext(), MenuMapsActivity.class);
                intent.putExtra("id_key", view.getId()); // Set your ID as a Intent Extra
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
//                Bundle extra = data.getBundleExtra("extra");
                ArrayList<PointsGpsClass> result = (ArrayList<PointsGpsClass>) data.getSerializableExtra("listPointsGps");
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                setListPoints(result, enclosUpdate);

                // Do something with the contact here (bigger example below)
            }
        }
    }

    public void setListPoints(ArrayList<PointsGpsClass> listPoints, EnclosClass enclos) {
        enclos.setPointsGps(listPoints);
        TextView textView = findViewById(R.id.tv_nb_points);
        textView.setText( String.format(getString(R.string.txt_nb_points), enclos.getPointsGps().size()));
        textView.setVisibility(View.VISIBLE);
    }
}
