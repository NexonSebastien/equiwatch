package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.model.EquidesClass;

public class EquidesActivity extends MenuEquiwatch  {

    // propriétés
    private EquidesController equidesController;
    private static EquidesActivity equidesActivity;
    private ArrayList<EquidesClass> lesEquides;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_equides, dynamicContent, true);
        super.onCreate(savedInstanceState);
        equidesActivity = this;
        equidesController = EquidesController.getInstance(this);
        lesEquides = equidesController.getLesEquides();
        this.equidesController = EquidesController.getInstance(this);
        ListView lvListeEquides = findViewById(R.id.lvListeEquides);
        TextView textVide = findViewById(R.id.txtVide);
        if (lesEquides.size() != 0) {
            EquidesListAdapter adapter = new EquidesListAdapter(this, lesEquides);
            lvListeEquides.setAdapter(adapter);
        } else {
            textVide.setText("Vous n'avez aucun équidés pour le moment, cliquez sur le + pour en ajouter.");
        }
        findViewById(R.id.imgBtnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextAct = new Intent(equidesActivity, EquidesCreateActivity.class);
                startActivity(nextAct);
                finish();
            }
        });
    }

    public static EquidesActivity getEquidesActivity() {
        return equidesActivity;
    }
}