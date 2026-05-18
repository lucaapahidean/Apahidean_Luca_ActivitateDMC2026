package com.example.lab11;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText etVal1, etVal2, etVal3, etVal4;
    private RadioGroup radioGroup;
    private Button btnAfiseaza;

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

        etVal1      = findViewById(R.id.etVal1);
        etVal2      = findViewById(R.id.etVal2);
        etVal3      = findViewById(R.id.etVal3);
        etVal4      = findViewById(R.id.etVal4);
        radioGroup  = findViewById(R.id.radioGroup);
        btnAfiseaza = findViewById(R.id.btnAfiseaza);

        btnAfiseaza.setOnClickListener(v -> {
            try {
                float v1 = Float.parseFloat(etVal1.getText().toString());
                float v2 = Float.parseFloat(etVal2.getText().toString());
                float v3 = Float.parseFloat(etVal3.getText().toString());
                float v4 = Float.parseFloat(etVal4.getText().toString());

                if (v1 <= 0 || v2 <= 0 || v3 <= 0 || v4 <= 0) {
                    Toast.makeText(this, "Valorile trebuie sa fie pozitive!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Determinam tipul de grafic ales
                String tipGrafic;
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.radioPie) {
                    tipGrafic = "PIE";
                } else if (selectedId == R.id.radioColumn) {
                    tipGrafic = "COLUMN";
                } else {
                    tipGrafic = "BAR";
                }

                Bundle bundle = new Bundle();
                bundle.putFloatArray("valori", new float[]{v1, v2, v3, v4});
                bundle.putString("tipGrafic", tipGrafic);

                Intent intent = new Intent(this, ChartActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

            } catch (NumberFormatException e) {
                Toast.makeText(this, "Introduceti valori numerice valide!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}