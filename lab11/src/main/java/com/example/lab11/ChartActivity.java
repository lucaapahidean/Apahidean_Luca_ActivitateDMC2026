package com.example.lab11;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle    = getIntent().getExtras();
        float[] valori   = bundle.getFloatArray("valori");
        String tipGrafic = bundle.getString("tipGrafic");

        // Alegem view-ul corespunzator tipului de grafic
        View grafic;
        switch (tipGrafic) {
            case "COLUMN":
                grafic = new DesenColumnChart(this, valori);
                break;
            case "BAR":
                grafic = new DesenBarChart(this, valori);
                break;
            case "PIE":
            default:
                grafic = new DesenPieChart(this, valori);
                break;
        }

        setContentView(grafic);
    }
}