package fr.equiwatch.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import fr.equiwatch.R;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.model.EquidesClass;

public class EquidesViewActivity extends MenuEquiwatch  {

    // propriétés
    /**
     * Equides Controller
     */
    private EquidesController equidesController;

    /**
     * L'equidés consulté
     */
    private EquidesClass equidesView;

    /**
     * Lancement de la fonction au moment du lancement de l'activité (déclanché pas le click sur l'oeil dans EquidesActivity)
     * @param savedInstanceState
     */
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
        txtIdEnclos.setText(equidesView.getIdEnclos());
        txtIdCapteur.setText(equidesView.getIdCapteur());
    }
}
