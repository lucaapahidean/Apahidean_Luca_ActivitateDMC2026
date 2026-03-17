package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
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
    private TextView textViewResult;
    private List<Balena> balene;
    ArrayAdapter<Balena> adapter;
    private ListView listViewResult;
    Button button2;
    Button button3;

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
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                balene
        );

        launcher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            Balena balena = (Balena) data.getSerializableExtra("balena");
                            //textViewResult.setText(balena.toString());
                            balene.add(balena);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
        );
        launcher3 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Balena balena = (Balena) result.getData().getSerializableExtra("balena");
                        //textViewResult.setText(balena.toString());
                        balene.add(balena);
                        adapter.notifyDataSetChanged();
                    }
                }
        );

        //textViewResult = findViewById(R.id.textView7);
        button2 = findViewById(R.id.button);
        button2.setOnClickListener(v -> deschideActivitate2());
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> deschideActivitate3());
        listViewResult = findViewById(R.id.listView);
        listViewResult.setAdapter(adapter);
        listViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, balene.get(position).toString(), Toast.LENGTH_SHORT).show();
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