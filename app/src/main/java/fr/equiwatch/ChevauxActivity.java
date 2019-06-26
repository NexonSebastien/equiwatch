package fr.equiwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ChevauxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tableauEnclos();
        setContentView(R.layout.activity_enclos);

    }

    private void tableauEnclos()
    {
        // données du tableau
        String [] entete = {"Label","Action"};
        final String [] col1 = {"Manege","Carriere","Pre"};
        final String col2 = "VMS";

        TableLayout table = (TableLayout) findViewById(R.id.idTableEnclos); // on prend le tableau défini dans le layout
        TableRow row; // création d'un élément : ligne
        TextView tv1,tv2; // création des cellules

        // entete

        row = new TableRow(this);
        tv1 = new TextView(this);
        tv1.setText(entete[0]);
        tv1.setGravity(Gravity.CENTER);
        tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

        tv2 = new TextView(this);
        tv2.setText(entete[1]);
        tv2.setGravity(Gravity.CENTER);
        tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

        row.addView(tv1);
        row.addView(tv2);

        table.addView(row);
        // pour chaque ligne
        for(int i=0;i<col1.length;i++) {
            row = new TableRow(this); // création d'une nouvelle ligne

            // pour des ligne cliquable
            final int id = i;
            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // faites ici ce que vous voulez
                    Log.d("Id","i="+id);
                }
            });

            tv1 = new TextView(this); // création cellule
            tv1.setText(col1[i]); // ajout du texte
            tv1.setGravity(Gravity.CENTER); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

            // idem 2ème cellule
            tv2 = new TextView(this);
            tv2.setText(col2);
            tv2.setGravity(Gravity.CENTER);
            tv2.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1 ) );

            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(tv2);

            // ajout de la ligne au tableau
            table.addView(row);
        }
    }
}
