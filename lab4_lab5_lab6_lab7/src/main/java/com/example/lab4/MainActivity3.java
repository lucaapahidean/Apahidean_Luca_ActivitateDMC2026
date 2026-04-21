package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    private EditText editTextNume;
    private EditText editTextVarsta;
    private SeekBar seekBarGreutate;
    private TextView textViewGreutate;
    private Switch switchEsteAdult;
    private TextView textViewAdultStatus;
    private Spinner spinnerSpecie;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNume     = findViewById(R.id.editTextNume3);
        editTextVarsta   = findViewById(R.id.editTextVarsta3);
        seekBarGreutate  = findViewById(R.id.seekBarGreutate3);
        textViewGreutate = findViewById(R.id.textViewGreutate3);
        switchEsteAdult  = findViewById(R.id.switchEsteAdult3);
        spinnerSpecie    = findViewById(R.id.spinnerSpecie3);
        saveButton       = findViewById(R.id.saveButton3);
        textViewAdultStatus = findViewById(R.id.textViewAdultStatus3);

        // Setează valoarea initiala a textViewGreutate
        textViewGreutate.setText(seekBarGreutate.getProgress() + " t");

        seekBarGreutate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewGreutate.setText(progress + " t");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        switchEsteAdult.setOnCheckedChangeListener((buttonView, isChecked) -> {
            textViewAdultStatus.setText(isChecked ? "Da!" : "Nu!");
            textViewAdultStatus.setTextColor(isChecked
                    ? android.graphics.Color.parseColor("#00D4FF")
                    : android.graphics.Color.parseColor("#7FA8C9"));
        });

        saveButton.setOnClickListener(v -> {
            String nume = editTextNume.getText().toString().trim();

            String varstaStr = editTextVarsta.getText().toString().trim();
            if (varstaStr.isEmpty()) {
                editTextVarsta.setError("Câmp obligatoriu");
                return;
            }
            int varsta = Integer.parseInt(varstaStr);

            int greutate = seekBarGreutate.getProgress();
            boolean esteAdult = switchEsteAdult.isChecked();
            Balena.Specie specie = Balena.Specie.values()[spinnerSpecie.getSelectedItemPosition()];

            Date anulNasterii = Calendar.getInstance().getTime();

            Intent it = new Intent(getApplicationContext(), MainActivity.class);
            it.putExtra("balena", (Parcelable)(new Balena(nume, varsta, greutate, esteAdult, specie, anulNasterii)));

            setResult(RESULT_OK, it);
            finish();
        });
    }
}