package com.turingtech.corp.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private String display = "";
    private TextView screen;
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.result);
        screen.setText(display);
    }

    public void updateScreen(){
        screen.setText(display);
    }

    public void clear(){
        display = "";
        currentOperator = "";
        result = "";
    }
    public void onClickClearScreen(View v){
        clear();
        updateScreen();
    }

    public void onClickNumber(View v){

        if(!result.equals("")){
            clear();
            updateScreen();
        }

        Button b = (Button) v;
        display +=b.getText();
        updateScreen();

    }

    private boolean isOperator(char op){
        switch (op){
            case '+':
            case '-':
            case 'x':
            case '÷':return true;
            default: return false;
        }
    }

    public double operate(String a,String b,String op){
        switch(op){
            case "+": return Double.valueOf(a)+Double.valueOf(b);
            case "-": return Double.valueOf(a)-Double.valueOf(b);
            case "*": return Double.valueOf(a)*Double.valueOf(b);
            case "÷": try{
                return Double.valueOf(a)/Double.valueOf(b);
            }catch (Exception e){
                Log.i("Calculator",e.getMessage());
            }
            case "%": return Double.valueOf(a)%Double.valueOf(b);
            case "^": return Math.pow(Double.valueOf(a),Double.valueOf(b));
            default: return -1;

        }
    }

    private boolean getDisplay(){
        if(currentOperator.equals("")) return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return false;
        result = String.valueOf(operate(operation[0], operation[1], currentOperator));
        return true;
    }

    public void onClickOperator(View v){

        if(display.equals("")) return;

        Button b = (Button) v;

        if(!result.equals("")){
            String _display = result;
            clear();
            display = _display;
        }

        if(!currentOperator.equals("")){
            Log.d("CalcX", ""+ display.charAt(display.length()-1));
            if(isOperator(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getDisplay();
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }


        display +=b.getText();
        currentOperator = b.getText().toString();
        updateScreen();

    }

    public void onClickEqual(View v){

        if(display.equals("")) return;
        if(!getDisplay()) return;
        screen.setText(display + "\n" + String.valueOf(result));
    }


}
