package com.example.lab4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ActivityResultLauncher<Intent> launcher2;
    public ActivityResultLauncher<Intent> launcher3;
    public ActivityResultLauncher<Intent> launcherEdit;
    private List<Balena> balene;
    BalenaAdapter adapter;
    private ListView listViewResult;
    Button button2;
    Button button3;

    // Retine pozitia elementului selectat pentru editare
    private int editIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        balene = new ArrayList<>();
        adapter = new BalenaAdapter(this, balene);

        // Launcher pentru adaugare balena via MainActivity2
        launcher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Balena balena = result.getData().getParcelableExtra("balena");
                        balene.add(balena);
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        // Launcher pentru adaugare balena via MainActivity3
        launcher3 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Balena balena = result.getData().getParcelableExtra("balena");
                        balene.add(balena);
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        // Launcher pentru editarea balenei selectate din lista
        launcherEdit = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Balena balenaModificata = result.getData().getParcelableExtra("balena");
                        // Inlocuim obiectul de la pozitia editIndex cu cel modificat
                        balene.set(editIndex, balenaModificata);
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        button2 = findViewById(R.id.button);
        button2.setOnClickListener(v -> deschideActivitate2());
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> deschideActivitate3());
        listViewResult = findViewById(R.id.listView);
        listViewResult.setAdapter(adapter);

        // La click pe un element, deschidem activitatea de editare cu datele balenei selectate
        listViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editIndex = position;
                Intent it = new Intent(getApplicationContext(), MainActivity2.class);
                it.putExtra("balena", balene.get(position));
                launcherEdit.launch(it);
            }
        });

        listViewResult.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                balene.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void deschideActivitate2() {
        Intent it = new Intent(getApplicationContext(), MainActivity2.class);
        launcher2.launch(it);
    }

    public void deschideActivitate3() {
        Intent it = new Intent(getApplicationContext(), MainActivity3.class);
        launcher3.launch(it);
    }
}
