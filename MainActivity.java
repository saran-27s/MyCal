//Complete Source code in java
package com.example.mycal;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private String currentInput = "";
    private String expression = "";
    private String lastOperation = "";
    private double firstOperand = 0;
    private boolean isResultShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextNumberDecimal);

        Button[] numberButtons = new Button[]{
                findViewById(R.id.zero),
                findViewById(R.id.one),
                findViewById(R.id.two),
                findViewById(R.id.three),
                findViewById(R.id.four),
                findViewById(R.id.five),
                findViewById(R.id.six),
                findViewById(R.id.seven),
                findViewById(R.id.eight),
                findViewById(R.id.nine)
        };

        for (Button button : numberButtons) {
            button.setOnClickListener(this::onNumberClick);
        }

        findViewById(R.id.plus).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.minus).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.mul).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.div).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.mod).setOnClickListener(this::onOperatorClick);
        findViewById(R.id.power).setOnClickListener(this::onOperatorClick);

        findViewById(R.id.clear).setOnClickListener(v -> clearInput());
        findViewById(R.id.equal).setOnClickListener(v -> calculateResult());
        findViewById(R.id.decimal).setOnClickListener(this::onDotClick);
    }

    private void onNumberClick(View v) {
        Button button = (Button) v;

        // Reset the input if a result is already shown
        if (isResultShown) {
            clearInput();
            isResultShown = false;
        }

        currentInput += button.getText().toString();
        expression += button.getText().toString();
        editText.setText(expression);
    }

    private void onOperatorClick(View v) {
        Button button = (Button) v;

        if (currentInput.isEmpty() && lastOperation.isEmpty()) {
            return; // No operation can be performed without numbers
        }

        if (!currentInput.isEmpty()) {
            // Handle continuous operations (e.g., 5 + 3 - 2)
            if (!lastOperation.isEmpty()) {
                calculateResult();
            } else {
                firstOperand = Double.parseDouble(currentInput);
            }
        }

        lastOperation = button.getText().toString();
        expression += " " + lastOperation + " ";
        currentInput = "";
        editText.setText(expression);
    }

    private void calculateResult() {
        if (!currentInput.isEmpty() && !lastOperation.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            double result = 0;

            switch (lastOperation) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        editText.setText("Error");
                        return;
                    }
                    break;
                case "%":
                    result = firstOperand % secondOperand;
                    break;
                case "^":
                    result = Math.pow(firstOperand, secondOperand);
                    break;
            }

            // Update state and display result
            firstOperand = result;
            currentInput = String.valueOf(result);
            expression = String.valueOf(result);
            editText.setText(expression);
            lastOperation = "";
            isResultShown = true;
        }
    }

    private void clearInput() {
        currentInput = "";
        expression = "";
        firstOperand = 0;
        lastOperation = "";
        editText.setText("");
        isResultShown = false;
    }

    private void onDotClick(View v) {
        // Prevent entering multiple decimal points in the same number
        if (currentInput.isEmpty()) {
            currentInput = "0."; // Add a leading zero for decimal numbers
            expression += "0.";
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
            expression += ".";
        }
        editText.setText(expression);
    }
}
