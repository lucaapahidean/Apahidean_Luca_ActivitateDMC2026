package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
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

public class MainActivity2 extends AppCompatActivity {

    private EditText editTextNume;
    private EditText editTextVarsta;
    private SeekBar seekBarGreutate;
    private TextView textViewGreutate;
    private Switch switchEsteAdult;
    private Spinner spinnerSpecie;
    private CalendarView calendarViewAnulNasterii;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextNume = findViewById(R.id.editTextText);
        editTextVarsta = findViewById(R.id.editTextText2);
        seekBarGreutate = findViewById(R.id.seekBar);
        switchEsteAdult = findViewById(R.id.switch1);
        spinnerSpecie = findViewById(R.id.spinner);
        textViewGreutate = findViewById(R.id.textView_greutate);
        calendarViewAnulNasterii = findViewById(R.id.calendarView);
        saveButton = findViewById(R.id.button2);

        textViewGreutate.setText(seekBarGreutate.getProgress() + " tone");

        Date currentTime = Calendar.getInstance().getTime();
        calendarViewAnulNasterii.setMaxDate(currentTime.getTime());

        // Actualizeaza textView-ul de langa seekBar ca sa informeze user-ul cu privire la valoarea aleasa
        seekBarGreutate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textViewGreutate.setText(progress + " tone");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar){}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Daca activitatea a fost deschisa pentru editare, precompletam campurile
        Balena balenaDeEditat = getIntent().getParcelableExtra("balena");
        if (balenaDeEditat != null) {
            editTextNume.setText(balenaDeEditat.getNume());
            editTextVarsta.setText(String.valueOf(balenaDeEditat.getVarsta()));
            seekBarGreutate.setProgress(balenaDeEditat.getGreutate());
            textViewGreutate.setText(balenaDeEditat.getGreutate() + " tone");
            switchEsteAdult.setChecked(balenaDeEditat.isEsteAdult());
            spinnerSpecie.setSelection(balenaDeEditat.getSpecie().ordinal());
            calendarViewAnulNasterii.setDate(balenaDeEditat.getAnulNasterii().getTime());
        }

        // Trimite raspuns activitatii parinte
        saveButton.setOnClickListener(v -> {
            String nume = editTextNume.getText().toString();
            String varstaStr = editTextVarsta.getText().toString().trim();
            int varsta;
            if (varstaStr.isEmpty())
                varsta = 0;
            else
                varsta = Integer.parseInt(varstaStr);
            int greutate = seekBarGreutate.getProgress();
            boolean esteAdult = switchEsteAdult.isChecked();
            // Claude a venit cu acest one-liner frumos
            // pentru maparea pozitiilor itemilor din spinner pe valorile din enum :)
            Balena.Specie specie = Balena.Specie.values()[spinnerSpecie.getSelectedItemPosition()];
            Date anulNasterii = new Date(calendarViewAnulNasterii.getDate());

            Intent it = new Intent(getApplicationContext(), MainActivity.class);
            it.putExtra("balena", new Balena(nume, varsta, greutate, esteAdult, specie, anulNasterii));

            setResult(RESULT_OK, it);
            finish();
        });
    }
}
