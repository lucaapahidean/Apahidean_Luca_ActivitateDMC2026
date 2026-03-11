package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    public ActivityResultLauncher<Intent> launcher2;
    public ActivityResultLauncher<Intent> launcher3;

    private TextView textViewResult;
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

        launcher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            Balena balena = (Balena) data.getSerializableExtra("balena");
                            textViewResult.setText(balena.toString());
                        }
                    }
                }
        );
        launcher3 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Balena balena = (Balena) result.getData().getSerializableExtra("balena");
                        textViewResult.setText(balena.toString());
                    }
                }
        );

        textViewResult = findViewById(R.id.textView7);
        button2 = findViewById(R.id.button);
        button2.setOnClickListener(v -> deschideActivitate2());
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(v -> deschideActivitate3());
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