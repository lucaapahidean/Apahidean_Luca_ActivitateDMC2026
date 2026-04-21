package com.example.lab8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static BalenaDatabase database;

    private EditText etNume, etVarsta, etCautaNume,
            etMin, etMax, etPrag, etLitera;
    private Spinner  spinnerSpecie;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(
                this,
                BalenaDatabase.class,
                "BalenaDatabase"
        ).allowMainThreadQueries().build();

        etNume       = findViewById(R.id.etNume);
        etVarsta     = findViewById(R.id.etVarsta);
        spinnerSpecie= findViewById(R.id.spinnerSpecie);
        etCautaNume  = findViewById(R.id.etCautaNume);
        etMin        = findViewById(R.id.etMin);
        etMax        = findViewById(R.id.etMax);
        etPrag       = findViewById(R.id.etPrag);
        etLitera     = findViewById(R.id.etLitera);
        listView     = findViewById(R.id.listView);
    }

    // ── 1. Insert ──
    public void insertMethod(View view) {
        String numeStr   = etNume.getText().toString().trim();
        String varstaStr = etVarsta.getText().toString().trim();

        if (numeStr.isEmpty() || varstaStr.isEmpty()) {
            Toast.makeText(this, "Completează numele și vârsta!", Toast.LENGTH_SHORT).show();
            return;
        }

        String specie = spinnerSpecie.getSelectedItem().toString();
        Balena balena = new Balena(numeStr, Integer.parseInt(varstaStr), specie);
        database.balenaDAO().insertBalena(balena);
        Toast.makeText(this, "Inserată: " + balena, Toast.LENGTH_SHORT).show();
    }

    // ── 2. Select toate ──
    public void selectAllMethod(View view) {
        List<Balena> balene = database.balenaDAO().getAllBalene();
        afiseazaInLista(balene);
        Toast.makeText(this, "Total: " + balene.size(), Toast.LENGTH_SHORT).show();
    }

    // ── 3. Select dupa nume ──
    public void selectByNumeMethod(View view) {
        String numeStr = etCautaNume.getText().toString().trim();
        if (numeStr.isEmpty()) {
            Toast.makeText(this, "Introdu un nume!", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Balena> balene = database.balenaDAO().getBaleneByNume(numeStr);
        afiseazaInLista(balene);
        Toast.makeText(this, "Găsite: " + balene.size(), Toast.LENGTH_SHORT).show();
    }

    // ── 4. Select interval varsta ──
    public void selectByIntervalMethod(View view) {
        String minStr = etMin.getText().toString().trim();
        String maxStr = etMax.getText().toString().trim();

        if (minStr.isEmpty() || maxStr.isEmpty()) {
            Toast.makeText(this, "Introdu min și max!", Toast.LENGTH_SHORT).show();
            return;
        }

        List<Balena> balene = database.balenaDAO()
                .getBaleneByVarstaInterval(
                        Integer.parseInt(minStr),
                        Integer.parseInt(maxStr));
        afiseazaInLista(balene);
        Toast.makeText(this, "Găsite în interval: " + balene.size(), Toast.LENGTH_SHORT).show();
    }

    // ── 5. Delete unde varsta > prag ──
    public void deleteMethod(View view) {
        String pragStr = etPrag.getText().toString().trim();
        if (pragStr.isEmpty()) {
            Toast.makeText(this, "Introdu pragul!", Toast.LENGTH_SHORT).show();
            return;
        }

        database.balenaDAO().deleteByVarstaGreaterThan(Integer.parseInt(pragStr));

        // Afisam ce a ramas dupa stergere
        List<Balena> balene = database.balenaDAO().getAllBalene();
        afiseazaInLista(balene);
        Toast.makeText(this, "Șterse! Rămase: " + balene.size(), Toast.LENGTH_SHORT).show();
    }

    // ── 6. Increment varsta dupa litera ──
    public void incrementMethod(View view) {
        String literaStr = etLitera.getText().toString().trim();
        if (literaStr.isEmpty()) {
            Toast.makeText(this, "Introdu o literă!", Toast.LENGTH_SHORT).show();
            return;
        }

        database.balenaDAO().incrementVarstaByLitera(literaStr.toUpperCase());

        // Afisam lista actualizata
        List<Balena> balene = database.balenaDAO().getAllBalene();
        afiseazaInLista(balene);
        Toast.makeText(this, "Vârste incrementate!", Toast.LENGTH_SHORT).show();
    }

    /** Populeaza ListView-ul cu lista primita. */
    private void afiseazaInLista(List<Balena> balene) {
        ArrayAdapter<Balena> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                balene);
        listView.setAdapter(adapter);
    }
}