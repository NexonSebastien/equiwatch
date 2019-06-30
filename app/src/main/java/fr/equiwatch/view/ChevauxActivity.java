package fr.equiwatch.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fr.equiwatch.R;
import fr.equiwatch.controller.EnclosController;

public class ChevauxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chevaux);
        init();
    }

    // propriétés
    private EnclosController enclosController;

    private void init(){
        this.enclosController = EnclosController.getInstance(this);
        tableauEnclos();
    }

    /**
     * Création de la liste des enclos
     */
    private void tableauEnclos()
    {
        // données du tableau
        String [] entete = {"Label","Action"};
        final String [] col1 = {"Manege","Carriere","Pre","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A","A"};
        final String col2 = "VMS";

        TableLayout table = (TableLayout) findViewById(R.id.idTableEnclos); // on prend le tableau défini dans le layout
        TableRow row; // création d'un élément : ligne
        TextView tv1,tv2; // création des cellules
        ImageView iv1,iv2,iv3;

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
        row.setBackgroundColor(5);

        table.addView(row);
        // pour chaque ligne
        for(int i=0;i<col1.length;i++) {
            row = new TableRow(this); // création d'une nouvelle ligne

            tv1 = new TextView(this); // création cellule
            tv1.setText(col1[i]); // ajout du texte
            tv1.setGravity(Gravity.CENTER); // centrage dans la cellule
            // adaptation de la largeur de colonne à l'écran :
            tv1.setLayoutParams( new TableRow.LayoutParams( 0, android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 2 ) );

            // idem 2ème cellule
            iv1 = new ImageView(this);
            iv1.setImageResource(R.drawable.eye);
            iv1.setLayoutParams( new TableRow.LayoutParams( 0, ViewGroup.LayoutParams.MATCH_PARENT, 2 ) );

            // pour des ligne cliquable
            final int id = i;
            iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // action on click
                    Log.d("Eye Id","i="+id);
                }
            });


            // idem 2ème cellule
            iv2 = new ImageView(this);
            iv2.setImageResource(R.drawable.pencil);
            iv2.setLayoutParams( new TableRow.LayoutParams( 0, ViewGroup.LayoutParams.MATCH_PARENT, 2 ) );
            // pour des ligne cliquable

            iv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // action on click
                    Log.d("Modifier Id","i="+id);
                }
            });


            // idem 2ème cellule
            iv3 = new ImageView(this);
            iv3.setImageResource(R.drawable.delete);
            iv3.setLayoutParams( new TableRow.LayoutParams( 0, ViewGroup.LayoutParams.MATCH_PARENT, 2 ) );

            iv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // action on click
                    Log.d("delete Id","i="+id);
                }
            });

            // ajout des cellules à la ligne
            row.addView(tv1);
            row.addView(iv1);
            row.addView(iv2);
            row.addView(iv3);

            // ajout de la ligne au tableau
            table.addView(row);
        }
    }
}
