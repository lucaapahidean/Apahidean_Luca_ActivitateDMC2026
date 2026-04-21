package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import android.content.SharedPreferences;

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

            Balena balena = new Balena(nume, varsta, greutate, esteAdult, specie, anulNasterii);
            salveazaInFisier(balena);
            citesteDinFisier();

            it.putExtra("balena", (Parcelable)balena);

            setResult(RESULT_OK, it);
            finish();
        });

        aplicaSetariText();
    }

    public void salveazaInFisier(Balena balena) {
        try {
            File file = new File(getFilesDir(), "fisier");
            FileOutputStream fos = new FileOutputStream(file, true); // append mode

            ObjectOutputStream os;
            if (file.length() > 0) {
                // File already has a header, so skip writing a new one
                os = new ObjectOutputStream(fos) {
                    @Override
                    protected void writeStreamHeader() throws IOException {
                        reset(); // optional: prevents back-references across appends
                    }
                };
            } else {
                // First write — write the header normally
                os = new ObjectOutputStream(fos);
            }

            os.writeObject(balena);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void citesteDinFisier() {
        try {
            FileInputStream fis = openFileInput("fisier");
            ObjectInputStream is = new ObjectInputStream(fis);
            Balena balena;

            ArrayList<String> result = new ArrayList<String>();
            try {
                for (;;) {
                    result.add(((Balena)is.readObject()).getNume());
                }
            } catch (EOFException e) {
                // End of stream
            }
            Toast.makeText(this,
                    result.toString(),
                    Toast.LENGTH_SHORT).show();

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void aplicaSetariText() {
        SharedPreferences prefs = getSharedPreferences(
                SettingsActivity.PREFS_NAME, MODE_PRIVATE);

        int   color = prefs.getInt(SettingsActivity.KEY_COLOR, SettingsActivity.DEFAULT_COLOR);
        float size  = prefs.getFloat(SettingsActivity.KEY_SIZE,  SettingsActivity.DEFAULT_SIZE);

        // Lista tuturor TextView-urilor pe care dorim sa le stilizam
        int[] labelIds = {
                R.id.textView,   // "Personalizează"
                R.id.textView2,  // "Nume"
                R.id.textView3,  // "Vârstă"
                R.id.textView4,  // "Greutate"
                R.id.textView_greutate,
                R.id.textView5,  // "Este adult"
                R.id.textView6   // "Specie"
        };

        for (int id : labelIds) {
            TextView tv = findViewById(id);
            if (tv != null) {
                tv.setTextColor(color);
                tv.setTextSize(size);
            }
        }
    }
}
