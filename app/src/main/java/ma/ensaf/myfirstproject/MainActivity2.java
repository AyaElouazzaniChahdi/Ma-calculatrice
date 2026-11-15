package ma.ensaf.myfirstproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity{

    TextView tv1;
    EditText et1;
    Button btn_1, btn_2, btn_3, btn_4;
    Button btn_5, btn_6, btn_7, btn_8;
    Button btn_9, btn_10, btn_11, btn_12;
    Button btn_13, btn_14, btn_15, btn_16;
    Button btn_17, btn_18, btn_19, btn_20;

    double numEnter1 = 0;
    double numEnter2 = 0;
    double result = 0;
    String operator = "";
    boolean justCalculated = false; // true juste après "=" ou une erreur

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        tv1 = findViewById(R.id.tv1);

        setSupportActionBar(findViewById(R.id.toolbar));

        et1 = findViewById(R.id.et1);

        btn_1 = findViewById(R.id.btn1);
        btn_2 = findViewById(R.id.btn2);
        btn_3 = findViewById(R.id.btn3);
        btn_4 = findViewById(R.id.btn4);

        btn_5 = findViewById(R.id.btn5);
        btn_6 = findViewById(R.id.btn6);
        btn_7 = findViewById(R.id.btn7);
        btn_8 = findViewById(R.id.btn8);

        btn_9 = findViewById(R.id.btn9);
        btn_10 = findViewById(R.id.btn10);
        btn_11 = findViewById(R.id.btn11);
        btn_12 = findViewById(R.id.btn12);

        btn_13 = findViewById(R.id.btn13);
        btn_14 = findViewById(R.id.btn14);
        btn_15 = findViewById(R.id.btn15);
        btn_16 = findViewById(R.id.btn16);

        btn_17 = findViewById(R.id.btn17);
        btn_18 = findViewById(R.id.btn18);
        btn_19 = findViewById(R.id.btn19);
        btn_20 = findViewById(R.id.btn20);

        // Boutons des chiffres
        View.OnClickListener numberlistener = v -> {
            if (justCalculated) {
                et1.setText("");
                justCalculated = false;
            }
            Button button = (Button) v;
            et1.append(button.getText().toString());
        };

        // btn_1 : effacer le dernier caractère (⌫)
        btn_1.setOnClickListener(v -> {
            if (justCalculated) {
                et1.setText("");
                justCalculated = false;
                return;
            }
            String currentText = et1.getText().toString();
            if (!currentText.isEmpty()) {
                et1.setText(currentText.substring(0, currentText.length() - 1));
                et1.setSelection(et1.getText().length());
            }
        });

        // btn_17 : changer le signe (+/-)
        btn_17.setOnClickListener(v -> {
            String value = et1.getText().toString();
            if (!value.isEmpty() && isNumber(value)) {
                double number = Double.parseDouble(value) * -1;
                et1.setText(format(number));
                et1.setSelection(et1.getText().length());
            }
        });


        btn_5.setOnClickListener(numberlistener);
        btn_6.setOnClickListener(numberlistener);
        btn_7.setOnClickListener(numberlistener);
        btn_9.setOnClickListener(numberlistener);
        btn_10.setOnClickListener(numberlistener);
        btn_11.setOnClickListener(numberlistener);
        btn_13.setOnClickListener(numberlistener);
        btn_14.setOnClickListener(numberlistener);
        btn_15.setOnClickListener(numberlistener);
        btn_18.setOnClickListener(numberlistener);

        // btn_2 : CE (effacer l'entrée courante)
        btn_2.setOnClickListener(v -> {
            et1.setText("");
            justCalculated = false;
        });

        // btn_3 : C (tout remettre à zéro)
        btn_3.setOnClickListener(v -> {
            et1.setText("");
            tv1.setText("");
            numEnter1 = 0;
            numEnter2 = 0;
            result = 0;
            operator = "";
            justCalculated = false;
        });

        // Opérateurs
        btn_4.setOnClickListener(v -> setOperator("/"));
        btn_8.setOnClickListener(v -> setOperator("*"));
        btn_12.setOnClickListener(v -> setOperator("-"));
        btn_16.setOnClickListener(v -> setOperator("+"));

        // btn_19 : le point décimal
        btn_19.setOnClickListener(v -> {
            if (justCalculated) {
                et1.setText("");
                justCalculated = false;
            }
            String text = et1.getText().toString();

            if (!text.contains(".")) {
                if (text.isEmpty()) {
                    text = "0.";
                } else if (text.equals("-")) {
                    text = "-0.";
                } else {
                    text = text + ".";
                }
                et1.setText(text);
                et1.setSelection(text.length());
            }
        });

        // btn_20 : égal (=)
        btn_20.setOnClickListener(v -> {
            String secondNumber = et1.getText().toString();

            if (secondNumber.isEmpty() || operator.isEmpty() || !isNumber(secondNumber)) {
                return;
            }

            numEnter2 = Double.parseDouble(secondNumber);

            if (operator.equals("/") && numEnter2 == 0) {
                showError();
                return;
            }

            result = calculate(numEnter1, numEnter2, operator);

            tv1.setText(format(numEnter1) + " " + operator + " " + format(numEnter2) + " =");
            et1.setText(format(result));
            et1.setSelection(et1.getText().length());

            operator = "";
            numEnter1 = result;
            justCalculated = true;
        });
    }

    private void setOperator(String op) {
        String value = et1.getText().toString();

        if (!value.isEmpty() && isNumber(value)) {
            double current = Double.parseDouble(value);


            if (!operator.isEmpty() && !justCalculated) {
                if (operator.equals("/") && current == 0) {
                    showError();
                    return;
                }
                numEnter1 = calculate(numEnter1, current, operator);
            } else {
                numEnter1 = current;
            }

            operator = op;
            justCalculated = false;
            tv1.setText(format(numEnter1) + " " + operator);
            et1.setText("");
        } else if (value.isEmpty() && !operator.isEmpty()) {

            operator = op;
            tv1.setText(format(numEnter1) + " " + operator);
        }
    }

    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default:  return b;
        }
    }

    private void showError() {
        et1.setText("Erreur");
        tv1.setText("");
        numEnter1 = 0;
        numEnter2 = 0;
        result = 0;
        operator = "";
        justCalculated = true; // le prochain chiffre efface "Erreur"
    }

    private boolean isNumber(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    private String format(double d) {

        if (d == (long) d) {
            return String.valueOf((long) d);
        }


        String formatted = String.format("%.4f", d);


        formatted = formatted.replaceAll("0*$", "").replaceAll("\\.$", "");

        return formatted;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
            return true;
        } else if (id == R.id.Calc_scientifique) {
            startActivity(new Intent(this, ScientificActivity.class));
            return true;
        } else if (id == R.id.Calc_devise) {
            startActivity(new Intent(this, DeviceActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}