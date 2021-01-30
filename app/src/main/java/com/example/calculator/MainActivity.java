package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatedResult = processText = numberHolder = operation = "";
        resultText = (TextView) findViewById(R.id.hukoResultText);
        calculationProcessText = (TextView) findViewById(R.id.hukoProcessText);
    }

    //REQUIRED VARIABLES & OBJECTS FOR CALCULATION
    private String calculatedResult;
    private String processText;
    private String numberHolder;
    private String operation;
    private TextView calculationProcessText, resultText;

    //RESETS ALL THE SAVED DATA
    private void CalculationEnds() {
        numberHolder = "";
        processText = "";
        calculatedResult = "";
        operation = "";
    }

    //[BUTTON EVENT]: DEL . 0 1 2 3 4 5 6 7 8 9
    public void NonFunctionalButtonEvents(View v) {
        Button pressedButton = (Button) findViewById(v.getId());
        if (pressedButton.getText().toString().compareToIgnoreCase("del") == 0) {
            resultText.setText("\n\nC a l c u l a t i o n s\nD e l e t e d");
            calculationProcessText.setText("D e l e t e d");
            CalculationEnds();
        } else if ((pressedButton.getText().toString().compareTo(".") == 0 && numberHolder.length() != 0 && numberHolder.compareTo("-") != 0 && !numberHolder.contains(".")) || pressedButton.getText().toString().compareTo(".") != 0) {
            numberHolder += pressedButton.getText().toString();
            processText += pressedButton.getText().toString();
            calculationProcessText.setText(processText);
            resultText.setText("\n\n\n" + numberHolder);
        }
    }

    //[BUTTON EVENT]: Sin Cos Tan Cot Log10 Sqrt Pow2
    public void FunctionalButtonEvents(View v) {
        Button pressedButton = (Button) findViewById(v.getId());
        if (numberHolder.length() != 0 && numberHolder.compareTo("-") != 0 && operation.length() == 0 && !numberHolder.endsWith(".")) {
            String funcResult = "";
            switch (v.getId()) {
                case R.id.hukoSin:
                    if (Math.abs(Double.parseDouble(numberHolder)) % 180 == 0 || Math.abs(Double.parseDouble(numberHolder)) == 0)
                        funcResult = "0.0";
                    else
                        funcResult = String.valueOf(Math.sin(Math.toRadians(Double.parseDouble(numberHolder))));
                    break;
                case R.id.hukoCos:
                    if (Math.abs(Double.parseDouble(numberHolder)) % 90 == 0 && Math.abs(Double.parseDouble(numberHolder)) % 180 != 0)
                        funcResult = "0.0";
                    else
                        funcResult = String.valueOf(Math.cos(Math.toRadians(Double.parseDouble(numberHolder))));
                    break;
                case R.id.hukoTan:
                    if (Math.abs(Double.parseDouble(numberHolder)) % 90 == 0 && Math.abs(Double.parseDouble(numberHolder)) % 180 != 0)
                        funcResult = "Undefined";
                    else if(Math.abs(Double.parseDouble(numberHolder)) % 180 == 0 || Math.abs(Double.parseDouble(numberHolder)) == 0)
                        funcResult = "0.0";
                    else
                        funcResult = String.valueOf(Math.tan(Math.toRadians(Double.parseDouble(numberHolder))));
                    break;
                case R.id.hukoCot:
                    if (Math.abs(Double.parseDouble(numberHolder)) % 180 == 0 || Math.abs(Double.parseDouble(numberHolder)) == 0)
                        funcResult = "Undefined";
                    else if(Math.abs(Double.parseDouble(numberHolder)) % 90 == 0 && Math.abs(Double.parseDouble(numberHolder)) % 180 != 0)
                        funcResult = "0.0";
                    else
                        funcResult = String.valueOf(1 / Math.tan(Math.toRadians(Double.parseDouble(numberHolder))));
                    break;
                case R.id.hukoButtonSqrt:
                    if (Double.parseDouble(numberHolder) < 0)
                        funcResult = "Undefined";
                    else
                        funcResult = String.valueOf(Math.sqrt(Double.parseDouble(numberHolder)));
                    break;
                case R.id.hukoButtonPower:
                    funcResult = String.valueOf(Math.pow(Double.parseDouble(numberHolder), 2));
                    break;
                case R.id.hukoButtonLog:
                    if (Double.parseDouble(numberHolder) < 0 || Double.parseDouble(numberHolder) == 0)
                        funcResult = "Undefined";
                    else
                        funcResult = String.valueOf(Math.log10(Double.parseDouble(numberHolder)));
                    break;
            }
            resultText.setText("C a l c u l a t i o n\nR e s u l t\n\n" + funcResult);
            if(v.getId() == R.id.hukoButtonPower)
                calculationProcessText.setText("Pow(" + String.valueOf(numberHolder) + ") = " + funcResult);
            else
                calculationProcessText.setText(pressedButton.getText().toString() + "(" + String.valueOf(numberHolder) + ") = " + funcResult);
            CalculationEnds();
        }
    }

    //[BUTTON EVENT]: + - * / =
    public void ArithmeticButtonEvents(View v) {
        Button pressedButton = (Button) findViewById(v.getId());
        if (pressedButton.getText().toString().compareTo("=") != 0 && numberHolder.length() != 0 && numberHolder.compareTo("-") != 0 && !numberHolder.endsWith(".") && operation.length() == 0) {
            calculatedResult = numberHolder;
            numberHolder = "";
            processText += " " + pressedButton.getText().toString() + " ";
            calculationProcessText.setText(processText);
            resultText.setText("\n\n\n" + pressedButton.getText().toString());
            operation = pressedButton.getText().toString();
        }
        else if (pressedButton.getText().toString().compareTo("-") == 0) {
            if (numberHolder.length() == 0 && operation.compareTo("-") != 0 && !numberHolder.endsWith(".")) {
                numberHolder += pressedButton.getText().toString();
                processText += " " + pressedButton.getText().toString();
                calculationProcessText.setText(processText);
                resultText.setText("\n\n\n" + numberHolder);
            }
        } else if (pressedButton.getText().toString().compareTo("=") == 0) {
            if (numberHolder.length() != 0 && operation.length() != 0 && !numberHolder.endsWith(".") && !numberHolder.endsWith("-")) {
                switch (operation) {
                    case "+":
                        calculatedResult = String.valueOf(Double.parseDouble(calculatedResult) + Double.parseDouble(numberHolder));
                        break;
                    case "-":
                        calculatedResult = String.valueOf(Double.parseDouble(calculatedResult) - Double.parseDouble(numberHolder));
                        break;
                    case "*":
                        calculatedResult = String.valueOf(Double.parseDouble(calculatedResult) * Double.parseDouble(numberHolder));
                        break;
                    case "/":
                        if (Double.parseDouble(calculatedResult) != 0 && Double.parseDouble(numberHolder) == 0)
                            calculatedResult = "Cannot divide by zero";
                        else if(Double.parseDouble(calculatedResult) == 0 && Double.parseDouble(numberHolder) == 0)
                            calculatedResult = "Undefined";
                        else
                            calculatedResult = String.valueOf(Double.parseDouble(calculatedResult) / Double.parseDouble(numberHolder));
                        break;
                }
                processText += " " + pressedButton.getText().toString() + " " + calculatedResult;
                calculationProcessText.setText(processText);
                resultText.setText("C a l c u l a t i o n\nR e s u l t\n\n" + calculatedResult);
                CalculationEnds();
            }
        }
    }
}
