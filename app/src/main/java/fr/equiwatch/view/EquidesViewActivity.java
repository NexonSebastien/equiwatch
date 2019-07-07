package fr.equiwatch.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import fr.equiwatch.R;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.model.EquidesClass;

public class EquidesViewActivity extends MenuEquiwatch  {

    // propriétés
    private EquidesController equidesController;
    private EquidesClass equidesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_equides_view, dynamicContent, true);
        super.onCreate(savedInstanceState);

        this.equidesController = EquidesController.getInstance(this.getBaseContext());
        equidesView = equidesController.getEquidesView();

        TextView txtNomEquides = (TextView) findViewById(R.id.txtNomEquides);
        TextView txtIdEnclos = (TextView) findViewById(R.id.txtIdEnclos);
        TextView txtIdCapteur = (TextView) findViewById(R.id.txtIdCapteur);

        txtNomEquides.setText(equidesView.getNom());
        txtIdEnclos.setText(Integer.toString(equidesView.getIdEnclos()));
        txtIdCapteur.setText(Integer.toString(equidesView.getIdCapteur()));
    }
}
