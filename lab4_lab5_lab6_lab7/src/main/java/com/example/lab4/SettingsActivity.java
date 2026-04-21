package com.example.lab4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    public static final String PREFS_NAME   = "AppSettings";
    public static final String KEY_COLOR    = "textColor";
    public static final String KEY_SIZE     = "textSize";

    // valori implicite
    public static final int   DEFAULT_COLOR = 0xFF000000; // negru
    public static final float DEFAULT_SIZE  = 16f;        // sp

    private RadioGroup radioGroupColor;
    private SeekBar    seekBarSize;
    private TextView   textViewPreview;
    private Button     buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sb = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom);
            return insets;
        });

        radioGroupColor = findViewById(R.id.radioGroupColor);
        seekBarSize     = findViewById(R.id.seekBarSize);
        textViewPreview = findViewById(R.id.textViewPreview);
        buttonSave      = findViewById(R.id.buttonSaveSettings);

        // Citim preferintele salvate si precompletam UI-ul
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int   savedColor = prefs.getInt(KEY_COLOR, DEFAULT_COLOR);
        float savedSize  = prefs.getFloat(KEY_SIZE,  DEFAULT_SIZE);

        // Setam radio button-ul corespunzator culorii salvate
        selectRadioForColor(savedColor);

        // SeekBar: 8sp..40sp → progress 0..32
        int progress = Math.round(savedSize) - 8;
        seekBarSize.setMax(32);
        seekBarSize.setProgress(Math.max(0, progress));

        // Preview initial
        updatePreview();

        // Listener seekBar
        seekBarSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar s, int p, boolean fromUser) { updatePreview(); }
            @Override public void onStartTrackingTouch(SeekBar s) {}
            @Override public void onStopTrackingTouch(SeekBar s)  {}
        });

        // Listener radio group
        radioGroupColor.setOnCheckedChangeListener((g, id) -> updatePreview());

        // Salvare
        buttonSave.setOnClickListener(v -> {
            int color = colorFromRadio();
            float size = seekBarSize.getProgress() + 8f;

            prefs.edit()
                    .putInt(KEY_COLOR, color)
                    .putFloat(KEY_SIZE, size)
                    .apply();

            finish();
        });
    }

    /** Actualizeaza textul de previzualizare in functie de selectiile curente. */
    private void updatePreview() {
        textViewPreview.setTextColor(colorFromRadio());
        textViewPreview.setTextSize(seekBarSize.getProgress() + 8f);
    }

    /** Returneaza culoarea ARGB corespunzatoare radio button-ului selectat. */
    private int colorFromRadio() {
        int checkedId = radioGroupColor.getCheckedRadioButtonId();
        if (checkedId == R.id.radioNegru)  return 0xFF000000;
        if (checkedId == R.id.radioRosu)   return 0xFFE53935;
        if (checkedId == R.id.radioAlbastru) return 0xFF1E88E5;
        if (checkedId == R.id.radioVerde)  return 0xFF43A047;
        return DEFAULT_COLOR;
    }

    /** Bifa radio button-ul care corespunde culorii salvate. */
    private void selectRadioForColor(int color) {
        if      (color == 0xFFE53935) radioGroupColor.check(R.id.radioRosu);
        else if (color == 0xFF1E88E5) radioGroupColor.check(R.id.radioAlbastru);
        else if (color == 0xFF43A047) radioGroupColor.check(R.id.radioVerde);
        else                          radioGroupColor.check(R.id.radioNegru);
    }
}