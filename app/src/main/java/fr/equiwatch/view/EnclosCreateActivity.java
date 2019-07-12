package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.EnclosClass;
import fr.equiwatch.model.PointsGpsClass;

public class EnclosCreateActivity extends MenuEquiwatch  {

    // propriétés
    private EnclosController enclosController;
    private ArrayList<PointsGpsClass> listPoints;
    static final int Get_Points_REQUEST = 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos_create, dynamicContent, true);
        super.onCreate(savedInstanceState);
        this.enclosController = EnclosController.getInstance(this);

        setEventFormButton();
    }

    public void setEventFormButton() {
        findViewById(R.id.btnEnclosCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            EditText inpNomEnclos = (EditText) findViewById(R.id.iptNomEnclos);
            String nomEnclos = inpNomEnclos.getText().toString();
            if(nomEnclos.length() > 0 && !listPoints.isEmpty()) {
                EnclosClass enclos = new EnclosClass(nomEnclos, listPoints);
                enclosController.creerEnclos(enclos);
                enclosController.getLesEnclos().add(enclos);

//              @todo Ajouter le snackbar d'information d'insetion(mauvaise fenetre)
                Snackbar snackbarSupr = Snackbar.make(view, "Vous venez de créer l'enclos : " + nomEnclos, Snackbar.LENGTH_LONG);
                View viewEnclos = snackbarSupr.getView();
                viewEnclos.setBackgroundResource(R.color.colorPrimary);
                snackbarSupr.show();
                Intent nextAct = new Intent(enclosController.getContext(), EnclosActivity.class);
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
                setListPoints(result);
            }
        }
    }

    public ArrayList<PointsGpsClass> getListPoints() {
        return listPoints;
    }

    public void setListPoints(ArrayList<PointsGpsClass> listPoints) {
        this.listPoints = listPoints;
        TextView textView = findViewById(R.id.tv_nb_points);
        textView.setText( String.format(getString(R.string.txt_nb_points), this.listPoints.size()));
        textView.setVisibility(View.VISIBLE);
    }
}
