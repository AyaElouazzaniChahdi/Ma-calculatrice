package ma.ensaf.myfirstproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    com.google.android.material.textfield.TextInputEditText etLogin, etPassword;
    Button btnValider;
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        rootView = findViewById(android.R.id.content);

        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        etLogin = findViewById(R.id.et_login);
        etPassword = findViewById(R.id.et_password);
        btnValider = findViewById(R.id.btn_valider);


        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String login = etLogin.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (login.isEmpty() || password.isEmpty()) {

                    // Snackbar d'erreur (Rouge)
                    Snackbar.make(rootView, "Veuillez remplir tous les champs !", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(Color.parseColor("#D32F2F"))
                            .setTextColor(Color.WHITE)
                            .show();

                } else {


                    Snackbar.make(rootView, "Connexion réussie. Bienvenue " + login, Snackbar.LENGTH_SHORT)
                            .setBackgroundTint(Color.parseColor("#388E3C"))
                            .setTextColor(Color.WHITE)
                            .show();


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("username", login);
                            startActivity(intent);
                        }
                    }, 1500);
                }
            }
        });
    }
}