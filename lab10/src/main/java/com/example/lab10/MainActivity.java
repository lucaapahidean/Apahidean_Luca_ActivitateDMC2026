package com.example.lab10;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_CITY_URL =
            "https://dataservice.accuweather.com/locations/v1/cities/search?q=";
    private static final String BASE_FORECAST_URL =
            "https://dataservice.accuweather.com/forecasts/v1/daily/";

    int forecastDays = 1;

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

        EditText etCity   = findViewById(R.id.etCity);
        Button btnSearch  = findViewById(R.id.btnSearch);
        TextView tvResult = findViewById(R.id.tvResult);
        Spinner spinner   = findViewById(R.id.spinner);

        // Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"1 zi", "5 zile"}
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position == 0) {
                    forecastDays = 1;
                } else {
                    forecastDays = 5;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        btnSearch.setOnClickListener(v -> {
            String oras = etCity.getText().toString().trim();

            // cautam orasul, obtinem Key-ul
            @SuppressLint("StaticFieldLeak") JSONParser cityParser = new JSONParser() {
                @Override
                protected void onPostExecute(String cityKey) {
                    tvResult.setText("City Key: " + cityKey);

                    // obtinem prognoza cu Key-ul si zilele selectate
                    String forecastUrl = BASE_FORECAST_URL
                            + forecastDays + "day/" + cityKey + "?metric=true";

                    JSONParser.GetForecast forecastParser = new JSONParser.GetForecast() {
                        @Override
                        protected void onPostExecute(List<String> rezultate) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("City Key: ").append(cityKey).append("\n\nPrognoza:\n");
                            for (String zi : rezultate) {
                                sb.append(zi).append("\n");
                            }
                            tvResult.setText(sb.toString());
                        }
                    };
                    forecastParser.execute(forecastUrl);
                }
            };
            cityParser.execute(BASE_CITY_URL + oras);
        });
    }
}