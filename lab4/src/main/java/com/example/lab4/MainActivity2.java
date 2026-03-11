package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

public class MainActivity2 extends AppCompatActivity {

    private EditText editTextNume;
    private EditText editTextVarsta;
    private SeekBar seekBarGreutate;
    private TextView textViewGreutate;
    private Switch switchEsteAdult;
    private Spinner spinnerSpecie;
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
        saveButton = findViewById(R.id.button2);
        textViewGreutate = findViewById(R.id.textView_greutate);
        textViewGreutate.setText(seekBarGreutate.getProgress() + " tone");

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

            Bundle bundle = new Bundle();
            Intent it = new Intent(getApplicationContext(), MainActivity.class);
            // De ce Serializable in loc de Parcelable:
            // https://stackoverflow.com/questions/10975239/use-parcelable-to-pass-an-object-from-one-android-activity-to-another
            bundle.putSerializable("balena", new Balena(nume, varsta, greutate, esteAdult, specie));
            it.putExtras(bundle);

            setResult(RESULT_OK, it);
            finish();
        });
    }
}