package com.example.lab11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DesenColumnChart extends View {

    private float[] valori;

    private int[] culori = {
            Color.rgb(255, 0,   0),
            Color.rgb(0,   0,   255),
            Color.rgb(0,   255, 0),
            Color.rgb(240, 190, 20)
    };

    public DesenColumnChart(Context context, float[] valori) {
        super(context);
        this.valori = valori;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        int width  = getWidth();
        int height = getHeight();

        float bazaY       = height * 0.75f;  // linia de baza a graficului
        float inaltimeMax = height * 0.55f;  // inaltimea maxima a unei coloane
        float margineStanga = width * 0.08f;

        // Gasim valoarea maxima pentru scalare
        float maxVal = 0;
        for (float v : valori) if (v > maxVal) maxVal = v;

        int n = valori.length;
        float latimeColoana = (width - margineStanga * 2f) / (n * 2f); // spatiu egal intre coloane

        // Axa OX
        Paint axaPaint = new Paint();
        axaPaint.setColor(Color.DKGRAY);
        axaPaint.setStrokeWidth(4);
        canvas.drawLine(margineStanga, bazaY, width - margineStanga, bazaY, axaPaint);

        // Axa OY
        canvas.drawLine(margineStanga, bazaY - inaltimeMax - 20, margineStanga, bazaY, axaPaint);

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(35);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.DKGRAY);

        for (int i = 0; i < n; i++) {
            float inaltimeColoana = (valori[i] / maxVal) * inaltimeMax;
            float startX = margineStanga + i * (latimeColoana * 2f) + latimeColoana * 0.5f;

            paint.setColor(culori[i % culori.length]);
            canvas.drawRect(
                    startX,
                    bazaY - inaltimeColoana,
                    startX + latimeColoana,
                    bazaY,
                    paint
            );

            // Valoarea deasupra coloanei
            canvas.drawText(
                    String.valueOf(valori[i]),
                    startX + latimeColoana / 2f,
                    bazaY - inaltimeColoana - 10,
                    textPaint
            );

            // Eticheta sub axa
            canvas.drawText(
                    "V" + (i + 1),
                    startX + latimeColoana / 2f,
                    bazaY + 45,
                    textPaint
            );
        }

        // Titlu
        Paint titluPaint = new Paint();
        titluPaint.setAntiAlias(true);
        titluPaint.setTextSize(50);
        titluPaint.setTextAlign(Paint.Align.CENTER);
        titluPaint.setColor(Color.DKGRAY);
        titluPaint.setFakeBoldText(true);
        canvas.drawText("Column Chart", width / 2f, height * 0.10f, titluPaint);
    }
}