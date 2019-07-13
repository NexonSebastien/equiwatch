package fr.equiwatch.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.model.PointsGpsClass;

public class MenuMapsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MapsEquiwatch mMapFragment;
    private ArrayList<Marker> listMarkerEnclos;
    private EnclosController enclosController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapFragment = new MapsEquiwatch();
        listMarkerEnclos = new ArrayList<>();
        enclosController = EnclosController.getInstance(this);

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

        setEventOnFloatingMapsButtons();
        Intent intent = getIntent();

        if(intent != null && intent.getExtras() != null) {
            if ((int)intent.getExtras().get("id_key") == 1) {
                enableCreateEnclos(); // Run the method with the ID Value passed through the Intent Extra
            } else if ((int)intent.getExtras().get("id_key") == 2) {
                LatLng cameraPos =  intent.getParcelableExtra("latlong");
                if (cameraPos != null) {
                    mMapFragment.moveMapCameraEnclos(cameraPos);
                }
            }
        }
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
        // Handle action bar item clicks here.
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
            Intent intent = new Intent(this, EquidesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_enclos) {
            Intent intent = new Intent(this, EnclosActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_capteurs) {
            Intent intent = new Intent(this, CapteursActivity.class);
            startActivity(intent);
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
    private void placeMarkerInMapOnPositionUser(String title, ArrayList<Marker> listMarkerEnclos, FloatingActionButton fabValidate, FloatingActionButton fabDelete) {
        if (mMapFragment != null) {
             mMapFragment.placeMarkerOnUserPosition(title, listMarkerEnclos, fabValidate, fabDelete);
        }
    }

    /**
     * Créer les evenemnts OnClick pour les boutons
     */
    public  void setEventOnFloatingMapsButtons() {
        final FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        final FloatingActionButton fabDelete = findViewById(R.id.fab_delete);
        final FloatingActionButton fabValidate = findViewById(R.id.fab_validate);
        final Button btQuit = findViewById(R.id.button_quit);
        final MenuMapsActivity context = this;

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeMarkerInMapOnPositionUser("marqueur enclos", listMarkerEnclos, fabValidate, fabDelete);
            }
        });


        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listMarkerEnclos.size() > 0) {
                    Marker marker = listMarkerEnclos.get(listMarkerEnclos.size() - 1);
                    marker.remove();
                    listMarkerEnclos.remove(marker);
                }
                if (listMarkerEnclos.size() < 3) {
                    fabValidate.hide();
                }

                if (listMarkerEnclos.size() == 0) {
                    fabDelete.hide();
                }
            }
        });


        fabValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<PointsGpsClass> listPointsGps = new ArrayList<>();
                for (int i = 0; listMarkerEnclos.size() > i; i ++){
                    Marker marker = listMarkerEnclos.get(i);
                    listPointsGps.add(new PointsGpsClass(marker.getPosition().latitude, marker.getPosition().longitude, i));
                }

                Intent returnIntent = getIntent();
                returnIntent.putExtra("listPointsGps", listPointsGps);

                setResult(Activity.RESULT_OK, returnIntent);
                context.finish();
            }
        });

        btQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllFloatingButtonsGone();
                listMarkerEnclos.clear();
                context.finish();
            }
        });
    }

    public void setAllFloatingButtonsGone() {
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.hide();
        FloatingActionButton fabDelete = findViewById(R.id.fab_delete);
        fabDelete.hide();
        FloatingActionButton fabValidate = findViewById(R.id.fab_validate);
        fabValidate.hide();
        Button btQuit = findViewById(R.id.button_quit);
        btQuit.setVisibility(View.GONE);
    }

    public void enableCreateEnclos() {
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.show();
        Button btQuit = findViewById(R.id.button_quit);
        btQuit.setVisibility(View.VISIBLE);
    }
}
