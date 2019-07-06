package fr.equiwatch.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.os.Bundle;

import fr.equiwatch.R;

public class EnclosViewActivity extends MenuEquiwatch  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_menu_equiwatch);
        CoordinatorLayout dynamicContent = findViewById(R.id.dynamic_content);
        getLayoutInflater().inflate(R.layout.activity_enclos_view, dynamicContent, true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enclos_view);
    }
}
