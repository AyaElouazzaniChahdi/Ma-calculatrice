package ma.ensaf.myfirstproject;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

public class ScientificActivity extends AppCompatActivity {

    EditText et1;
    TextView tv1;

    double num1 = 0;
    String operator = "";
    boolean newInput = true;
    boolean degreeMode = true;
    String currentInput = "0";
    String expression = "";
    boolean unaryPending = false;
    String unaryResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        et1 = findViewById(R.id.et1);
        tv1 = findViewById(R.id.tv1);

        et1.setShowSoftInputOnFocus(false);

        int[] numberIds = {
                R.id.b0, R.id.b1, R.id.b2, R.id.b3,
                R.id.b4, R.id.b5, R.id.b6, R.id.b7,
                R.id.b8, R.id.b9
        };

        for (int id : numberIds) {
            Button b = findViewById(id);
            b.setOnClickListener(v -> addNumber(b.getText().toString()));
        }

        findViewById(R.id.bDot).setOnClickListener(v -> addDot());
        findViewById(R.id.bClear).setOnClickListener(v -> clear());
        findViewById(R.id.bDelete).setOnClickListener(v -> delete());
        findViewById(R.id.bSign).setOnClickListener(v -> unary("±"));

        findViewById(R.id.bPlus).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.bMinus).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.bMultiply).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.bDivide).setOnClickListener(v -> setOperator("/"));
        findViewById(R.id.bMod).setOnClickListener(v -> setOperator("mod"));
        findViewById(R.id.bPower).setOnClickListener(v -> setOperator("^"));

        findViewById(R.id.bEqual).setOnClickListener(v -> calculate());

        findViewById(R.id.bSin).setOnClickListener(v -> unary("sin"));
        findViewById(R.id.bCos).setOnClickListener(v -> unary("cos"));
        findViewById(R.id.bTan).setOnClickListener(v -> unary("tan"));

        findViewById(R.id.bAsin).setOnClickListener(v -> unary("asin"));
        findViewById(R.id.bAcos).setOnClickListener(v -> unary("acos"));
        findViewById(R.id.bAtan).setOnClickListener(v -> unary("atan"));

        findViewById(R.id.bSqrt).setOnClickListener(v -> unary("sqrt"));
        findViewById(R.id.bCbrt).setOnClickListener(v -> unary("cbrt"));
        findViewById(R.id.bSquare).setOnClickListener(v -> unary("square"));
        findViewById(R.id.bCube).setOnClickListener(v -> unary("cube"));
        findViewById(R.id.bInverse).setOnClickListener(v -> unary("inverse"));
        findViewById(R.id.bFactorial).setOnClickListener(v -> unary("factorial"));

        findViewById(R.id.bLog).setOnClickListener(v -> unary("log"));
        findViewById(R.id.bLn).setOnClickListener(v -> unary("ln"));
        findViewById(R.id.bAbs).setOnClickListener(v -> unary("abs"));
        findViewById(R.id.bPercent).setOnClickListener(v -> unary("percent"));

        findViewById(R.id.bPi).setOnClickListener(v -> constant(Math.PI, "π"));
        findViewById(R.id.bE).setOnClickListener(v -> constant(Math.E, "e"));

        findViewById(R.id.bMode).setOnClickListener(v -> {
            degreeMode = !degreeMode;
            ((Button) v).setText(degreeMode ? "DEG" : "RAD");
        });
    }

    private double getValue() {
        return Double.parseDouble(currentInput);
    }

    private void updateDisplay() {
        if (newInput) {
            et1.setText(expression.isEmpty() ? currentInput : expression);
        } else {
            et1.setText(expression + currentInput);
        }
    }

    private void addNumber(String number) {
        if (newInput || currentInput.equals("0")) {
            currentInput = number;
            newInput = false;
        } else {
            currentInput += number;
        }
        updateDisplay();
    }

    private void addDot() {
        if (newInput) {
            currentInput = "0.";
            newInput = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
        updateDisplay();
    }

    private void clear() {
        num1 = 0;
        operator = "";
        currentInput = "0";
        expression = "";
        newInput = true;
        unaryPending = false;
        unaryResult = "";

        et1.setText("0");
        tv1.setText("");
    }

    private void delete() {
        if (newInput) return;

        if (currentInput.length() > 1) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        } else {
            currentInput = "0";
        }

        updateDisplay();
    }

    private void setOperator(String op) {
        try {
            if (!operator.isEmpty() && !newInput) {
                calculate();
            }

            num1 = getValue();
            operator = op;

            String displayOp;
            switch (op) {
                case "*":   displayOp = "×"; break;
                case "/":   displayOp = "÷"; break;
                default:    displayOp = op;  break;
            }

            expression = format(num1) + " " + displayOp + " ";
            currentInput = "";
            newInput = true;
            unaryPending = false;

            updateDisplay();

        } catch (Exception e) {
            error();
        }
    }

    private void calculate() {

        // Cas : résultat unaire en attente → on le valide
        if (unaryPending) {
            currentInput = unaryResult;
            expression = "";
            operator = "";
            unaryPending = false;
            newInput = true;

            et1.setText(currentInput);
            tv1.setText("");
            return;
        }

        if (operator.isEmpty() || newInput) return;

        try {
            double num2 = getValue();
            double result;

            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;

                case "-":
                    result = num1 - num2;
                    break;

                case "*":
                    result = num1 * num2;
                    break;

                case "/":
                    if (num2 == 0) throw new ArithmeticException("Division par zéro");
                    result = num1 / num2;
                    break;

                case "mod":
                    if (num2 == 0) throw new ArithmeticException("Modulo par zéro");
                    result = num1 % num2;
                    break;

                case "^":
                    result = Math.pow(num1, num2);
                    break;

                default:
                    return;
            }

            String completeExpression = expression + currentInput;
            tv1.setText(completeExpression + " =");

            expression = "";
            currentInput = format(result);
            operator = "";
            num1 = result;
            newInput = true;

            et1.setText(currentInput);

        } catch (Exception e) {
            error();
        }
    }

    private void unary(String function) {
        try {
            double x = getValue();
            double result;

            switch (function) {
                case "sin":
                    result = Math.sin(angle(x));
                    break;

                case "cos":
                    result = Math.cos(angle(x));
                    break;

                case "tan":
                    if (Math.abs(Math.cos(angle(x))) < 1E-12)
                        throw new ArithmeticException("tan indéfini");
                    result = Math.tan(angle(x));
                    break;

                case "asin":
                    if (Math.abs(x) > 1) throw new ArithmeticException("asin hors domaine");
                    result = Math.asin(x);
                    if (degreeMode) result = Math.toDegrees(result);
                    break;

                case "acos":
                    if (Math.abs(x) > 1) throw new ArithmeticException("acos hors domaine");
                    result = Math.acos(x);
                    if (degreeMode) result = Math.toDegrees(result);
                    break;

                case "atan":
                    result = Math.atan(x);
                    if (degreeMode) result = Math.toDegrees(result);
                    break;

                case "sqrt":
                    if (x < 0) throw new ArithmeticException("racine d'un négatif");
                    result = Math.sqrt(x);
                    break;

                case "cbrt":
                    result = Math.cbrt(x);
                    break;

                case "square":
                    result = x * x;
                    break;

                case "cube":
                    result = x * x * x;
                    break;

                case "inverse":
                    if (x == 0) throw new ArithmeticException("inverse de 0");
                    result = 1 / x;
                    break;

                case "log":
                    if (x <= 0) throw new ArithmeticException("log hors domaine");
                    result = Math.log10(x);
                    break;

                case "ln":
                    if (x <= 0) throw new ArithmeticException("ln hors domaine");
                    result = Math.log(x);
                    break;

                case "abs":
                    result = Math.abs(x);
                    break;

                case "percent":
                    result = x / 100;
                    break;

                case "factorial":
                    if (x < 0 || x != Math.floor(x) || x > 170)
                        throw new ArithmeticException("factorielle invalide");

                    result = 1;
                    for (int i = 2; i <= (int) x; i++) {
                        result *= i;
                    }
                    break;

                case "±":
                    result = -x;
                    break;

                default:
                    return;
            }


            String operation = function + "(" + format(x) + ")";

            unaryResult = format(result);
            unaryPending = true;

            expression = "";
            operator = "";
            newInput = true;

            et1.setText(operation);
            tv1.setText("");

        } catch (Exception e) {
            error();
        }
    }

    private double angle(double x) {
        return degreeMode ? Math.toRadians(x) : x;
    }

    private void constant(double value, String name) {
        currentInput = format(value);
        expression = "";
        operator = "";
        newInput = true;
        unaryPending = false;
        unaryResult = "";

        et1.setText(currentInput);
        tv1.setText("");
    }

    private String format(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return "Error";
        }

        if (Math.abs(value) < 1e15 && value == Math.rint(value)) {
            return String.format(Locale.US, "%.0f", value);
        }

        return String.format(Locale.US, "%.10f", value)
                .replaceAll("0+$", "")
                .replaceAll("\\.$", "");
    }

    private void error() {
        currentInput = "0";
        expression = "";
        operator = "";
        newInput = true;
        unaryPending = false;
        unaryResult = "";

        et1.setText("Error");
        tv1.setText("");
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