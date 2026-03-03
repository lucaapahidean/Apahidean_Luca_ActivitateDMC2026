package com.example.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

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

        Bundle bundle = getIntent().getExtras();
        String text = bundle.getString("mesaj");
        int int1 = bundle.getInt("int1", 0);
        int int2 = bundle.getInt("int2", 0);

        Toast.makeText(getApplicationContext(), "Din bundle: " + text + "; " + int1 + "; " + int2, Toast.LENGTH_LONG).show();

        Button button = findViewById(R.id.button3);
        button.setOnClickListener(v -> {
            bundle.clear();
            bundle.putString("mesaj", "Acesta este mesajul din MainActivity3");
            bundle.putInt("sum", int1 + int2);

            Intent it = new Intent(getApplicationContext(), MainActivity2.class);
            it.putExtras(bundle);

            setResult(RESULT_OK, it);
            finish();
        });
    }
}