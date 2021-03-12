package com.example.se2_0_einzelbeispiel;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Main Activity of MatrikelMagic (aka SE2-Einzelbeispiel)
 * @author Manuel Simon #00326348
 */
public class MainActivity extends AppCompatActivity {

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

        ServerRequest request = new ServerRequest("se2-isys.aau.at:53212", matrikelnummer);
        Thread t = new Thread(request);
        t.start();

        TextView result = findViewById(R.id.responseField);
        try {
            t.join();
            result.setText(request.getResponse());

        } catch(InterruptedException ex) {
            result.setText("Verbindungsfehler");
        }
    }
}