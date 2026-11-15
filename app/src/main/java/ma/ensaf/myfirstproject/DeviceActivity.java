package ma.ensaf.myfirstproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;

public class DeviceActivity extends AppCompatActivity {

    private TextInputEditText etMontant;
    private AutoCompleteTextView spinnerFrom, spinnerTo;
    private Button btnConvert;
    private TextView tvResultat;

    // Liste des devises
    private final String[] devises = {"MAD (Dirham)", "EUR (Euro)", "USD (Dollar)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Convertisseur");
        }


        etMontant = findViewById(R.id.etMontant);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        btnConvert = findViewById(R.id.btnConvert);
        tvResultat = findViewById(R.id.tvResultat);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, devises);
        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);


        spinnerFrom.setText(devises[0], false);
        spinnerTo.setText(devises[1], false);


        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertirDevise();
            }
        });
    }


    private void convertirDevise() {

        String montantStr = etMontant.getText().toString();
        if (montantStr.isEmpty()) {
            Toast.makeText(this, "Veuillez entrer un montant", Toast.LENGTH_SHORT).show();
            return;
        }

        double montant = Double.parseDouble(montantStr);
        String from = spinnerFrom.getText().toString();
        String to = spinnerTo.getText().toString();
        double resultat = 0;

        // Taux de change fixes (par rapport au MAD)
        // 1 MAD = 0.092 EUR
        // 1 MAD = 0.10 USD
        double madToEur = 0.092;
        double madToUsd = 0.10;


        if (from.equals(to)) {
            resultat = montant;
        }

        else if (from.contains("MAD") && to.contains("EUR")) {
            resultat = montant * madToEur;
        } else if (from.contains("MAD") && to.contains("USD")) {
            resultat = montant * madToUsd;
        }

        else if (from.contains("EUR") && to.contains("MAD")) {
            resultat = montant / madToEur;
        } else if (from.contains("USD") && to.contains("MAD")) {
            resultat = montant / madToUsd;
        }

        else if (from.contains("EUR") && to.contains("USD")) {
            resultat = montant * (madToUsd / madToEur);
        } else if (from.contains("USD") && to.contains("EUR")) {
            resultat = montant * (madToEur / madToUsd);
        }


        tvResultat.setText(String.format("Résultat : %.2f", resultat));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu); // On utilise le même menu que les autres pages
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == android.R.id.home) {
            finish();
            return true;
        }


        if (id == R.id.Calc_standard) {
            startActivity(new Intent(this, MainActivity2.class));
            return true;
        } else if (id == R.id.Calc_scientifique) {
            startActivity(new Intent(this, ScientificActivity.class));
            return true;
        } else if (id == R.id.Calc_devise) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}