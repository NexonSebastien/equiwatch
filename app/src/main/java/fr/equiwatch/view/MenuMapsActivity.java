package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import fr.equiwatch.R;

public class MenuMapsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MapsEquiwatch mMapFragment;
    private ArrayList<Marker> listMarkerEnclos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapFragment = new MapsEquiwatch();
        listMarkerEnclos = new ArrayList<>();

        setContentView(R.layout.activity_menu_maps_equiwatch);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String app_name = getString(R.string.app_name);
        getSupportActionBar().setTitle(app_name.toUpperCase());
        DrawerLayout drawer = findViewById(R.id.drawer_maps_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        // On ouvre le première item du menu
        navigationView.setCheckedItem(R.id.nav_home);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));

//        // Code pour les boutons flottants
        Log.d("MenuEquiwatch","*********** "+ R.id.fab_add);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Marker marker = placeMarkerInMapOnPositionUser("marqueur enclos");
                listMarkerEnclos.add(marker);

                if (listMarkerEnclos.size() > 3) {

                }
            }
        });

        FloatingActionButton fabDelete = findViewById(R.id.fab_delete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listMarkerEnclos.size() < 3) {

                }
            }
        });

        FloatingActionButton fabValidate = findViewById(R.id.fab_delete);
        fabValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_maps_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equiwatch, menu);
        // Hide three dot button
        MenuItem item= menu.findItem(R.id.action_settings);
        item.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chevaux) {
            // Handle the camera action
        } else if (id == R.id.nav_enclos) {
//            showFragment(new EnclosFragment());
            Intent intent = new Intent(this, EnclosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_capteurs) {

        } else if (id == R.id.nav_parametre) {

        } else if (id == R.id.nav_home) {
            showFragment(mMapFragment);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_maps_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Permet de remplacer le le contenu du container du DrawerNavigation par le fragment passé en paramètre
     *
     * @param fragment
     */
    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container_menu_maps, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    /**
     * Permet de placer un marqueur sur la map en faisant en propriété du MenuEquiwatch grâce a une fonction de MapsEquiwatch
     *
     * @param title
     */
    private Marker placeMarkerInMapOnPositionUser(String title) {
        Marker marker = null;
        if (mMapFragment != null) {
            marker = mMapFragment.placeMarkerOnUserPosition(title);
        }

        return marker;
    }
}
