package com.example.se2_0_einzelbeispiel;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity of MatrikelMagic (aka SE2-Einzelbeispiel)
 * @author Manuel Simon #00326348
 */
public class MainActivity extends AppCompatActivity {
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Handles the server request when clicking request button.
     * @param view
     */
    public void serverRequest(View view) {
        TextView input = findViewById(R.id.matrikelnummer);
        String matrikelnummer = input.getText().toString();

        // risk of leaks but for practise reasons it will do
        handler = new Handler() {
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                TextView result = findViewById(R.id.responseField);
                result.setText(b.getString("response"));
            }
        };

        ServerRequest request = new ServerRequest(
                "se2-isys.aau.at:53212", matrikelnummer, handler);
        Thread t = new Thread(request);
        t.start();
    }

    /**
     * Calculates and displays the index of any combination of two numbers
     * that share a common divisor.
     * 0 is discarded
     * @param view
     */
    public void checkDivisors(View view) {
        TextView tv = findViewById(R.id.matrikelnummer);
        String input = tv.getText().toString();
        int[] digits = new int[input.length()];
        for(int i=0; i < input.length(); i++) {
            digits[i] = Integer.parseInt(String.valueOf(input.charAt(i)));
        }

        int index1 = 0;
        int index2 = 0;
        boolean found = false;
        for(int i=0; i < digits.length && !found; i++) {
            List<Integer> divisors = calculateDivisors(digits[i]);
            for(int j=i+1; j < digits.length && !found; j++) {
                if(isDividableByAny(digits[j], divisors)) {
                    index1 = i;
                    index2 = j;
                    found = true;
                }
            }
        }

        TextView result = findViewById(R.id.responseField);
        String resultText = found ? "Index "+index1+" and "+index2+" haben gemeinsame Teiler!"
                : "Keine gemeinsamen Teiler gefunden!";
        result.setText(resultText);
    }

    private List<Integer> calculateDivisors(int digit) {
        ArrayList<Integer> divisors = new ArrayList<>();
        for(int i=2; i <= digit/2; i++) {
            if(digit % i == 0)
                divisors.add(i);
        }
        // + dividable by itself but discard 1
        if(digit > 1)
            divisors.add(digit);

        return divisors;
    }

    private boolean isDividableByAny(int dividend, List<Integer> divisors) {
        // discard 0
        if(dividend == 0)
            return false;

        for(int divisor : divisors) {
            if(dividend % divisor == 0 && dividend != 0)
                return true;
        }

        return false;
    }
}