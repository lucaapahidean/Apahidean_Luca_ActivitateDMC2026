package com.example.lab3;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ActivityLifecycle";

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
        Log.v(TAG, "onCreate: Activitatea a fost creată [VERBOSE]");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: Activitatea devine vizibilă [DEBUG]");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: Activitatea este în prim-plan și interactivă [INFO]");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "onPause: Activitatea a pierdut focusul - salvați datele! [WARNING]");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: Activitatea nu mai este vizibilă [ERROR]");
    }
}