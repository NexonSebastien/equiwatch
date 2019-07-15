package fr.equiwatch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fr.equiwatch.R;

import fr.equiwatch.controller.CapteursController;
import fr.equiwatch.controller.EquidesController;
import fr.equiwatch.controller.EnclosController;
import fr.equiwatch.notifications.NotificationReceiver;

public class MainActivity extends AppCompatActivity {
    private EnclosController enclosController;
    private static final String TAG = "EmailPassword";



    private TextView mStatusTextView;

    private TextView mDetailTextView;

    private EditText mEmailField;

    private EditText mPasswordField;

    private androidx.appcompat.widget.Toolbar toolbars;
    private ProgressBar progressBar;
    private EditText email;
    private EditText password;
    private Button signup;
    private Button login;


    // [START declare_auth]
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signup = findViewById(R.id.btnSignup);
        login = findViewById(R.id.btnLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //        Permet d'ajouter dans les differentes liste les enclos, equides et capteurs existant en base de donnée.
                EnclosController enclosController = EnclosController.getInstance(MainActivity.this);
                EquidesController equidesController = EquidesController.getInstance(MainActivity.this);
                CapteursController capteursController = CapteursController.getInstance(MainActivity.this);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (email.getText().toString().isEmpty() | password.getText().toString().isEmpty()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "E-Mail ou mot de passe manquant", Toast.LENGTH_LONG).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),
                            password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(MainActivity.this, MenuMapsActivity.class));
                                    } else {
                                        Toast.makeText(MainActivity.this, task.getException().getMessage()
                                                , Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public void openMaps() {
        NotificationReceiver.setupAlarm(getApplicationContext());
        Intent intent = new Intent(this, MenuMapsActivity.class);
        startActivity(intent);
    }
}
