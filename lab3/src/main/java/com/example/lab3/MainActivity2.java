package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

public class MainActivity2 extends AppCompatActivity {
    public ActivityResultLauncher<Intent> launcher;

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

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent data = result.getData();
                            String text = data.getStringExtra("mesaj");
                            int sum = data.getIntExtra("sum", 0);
                            Toast.makeText(getApplicationContext(), "Din bundle: " + text + "; " + sum, Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> deschideActivitate3());

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> deschideActivitate1());
    }

    public void deschideActivitate3() {
        Intent it = new Intent(getApplicationContext(), MainActivity3.class);

        Bundle bundle = new Bundle();
        bundle.putString("mesaj", "Acesta este mesajul din MainActivity2");
        bundle.putInt("int1", 1);
        bundle.putInt("int2", 2);
        it.putExtras(bundle);

        launcher.launch(it);
    }

    public void deschideActivitate1() {
        Intent it = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(it);
    }
}