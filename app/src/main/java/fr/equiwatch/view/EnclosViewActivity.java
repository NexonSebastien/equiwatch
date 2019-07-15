package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.maps.model.LatLng;

import fr.equiwatch.R;
import fr.equiwatch.model.EnclosClass;

public class EnclosViewActivity extends MenuEquiwatch  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos_view, dynamicContent, true);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if(intent != null && intent.getExtras() != null) {
            EnclosClass enclos =(EnclosClass) intent.getSerializableExtra("enclos");
            TextView dynamicLabel = findViewById(R.id.dynamycLabel);
            dynamicLabel.setText(enclos.getLabel());
        }
    }
}
