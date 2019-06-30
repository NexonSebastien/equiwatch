package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import fr.equiwatch.R;
import fr.equiwatch.view.MapsEquiwatch;

public class MainActivity extends AppCompatActivity {
    private Button bt_maps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.activity_main);

        bt_maps = findViewById(R.id.bt_maps);
        bt_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMaps();
            }
        });
    }

    public void openMaps() {
        Intent intent = new Intent(this, MenuEquiwatch.class);
        startActivity(intent);
    }
}
