package fr.equiwatch.view;

import android.os.Bundle;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import fr.equiwatch.R;
import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.model.CapteursClass;

public class CapteursViewActivity extends MenuEquiwatch  {

    // propriétés
    /**
     * Capteur controller
     */
    private CapteursController capteursController;

    /**
     * Le capteur que l'ont consulte
     */
    private CapteursClass capteursView;

    /**
     * Lancement de la fonction au moment du lancement de l'activité (déclanché pas le click sur l'oeil dans CapteursActivity)
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_capteurs_view, dynamicContent, true);
        super.onCreate(savedInstanceState);

        this.capteursController = CapteursController.getInstance(this.getBaseContext());
        capteursView = capteursController.getCapteursView();

        TextView txtLabelCapteurs = (TextView) findViewById(R.id.txtLabelCapteurs);
        TextView txtTypeCapteurs = (TextView) findViewById(R.id.txtTypeCapteurs);

        txtLabelCapteurs.setText(capteursView.getLabel());
        txtTypeCapteurs.setText(capteursView.getType());
    }
}
