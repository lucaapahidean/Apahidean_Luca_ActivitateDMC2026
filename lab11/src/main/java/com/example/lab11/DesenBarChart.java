package com.example.lab11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DesenBarChart extends View {

    private float[] valori;

    private int[] culori = {
            Color.rgb(255, 0,   0),
            Color.rgb(0,   0,   255),
            Color.rgb(0,   255, 0),
            Color.rgb(240, 190, 20)
    };

    public DesenBarChart(Context context, float[] valori) {
        super(context);
        this.valori = valori;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        int width  = getWidth();
        int height = getHeight();

        float axaX        = width  * 0.15f;  // linia de baza (axa OY) - stanga
        float latimeMax   = width  * 0.70f;  // lungimea maxima a unui bar
        float margineSus  = height * 0.15f;

        // Gasim valoarea maxima pentru scalare
        float maxVal = 0;
        for (float v : valori) if (v > maxVal) maxVal = v;

        int n = valori.length;
        float inaltimeBar = (height - margineSus * 2f) / (n * 2f);

        // Axa OY
        Paint axaPaint = new Paint();
        axaPaint.setColor(Color.DKGRAY);
        axaPaint.setStrokeWidth(4);
        canvas.drawLine(axaX, margineSus - 20, axaX, margineSus + n * inaltimeBar * 2f, axaPaint);

        // Axa OX
        canvas.drawLine(axaX, margineSus + n * inaltimeBar * 2f,
                axaX + latimeMax + 20, margineSus + n * inaltimeBar * 2f, axaPaint);

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(35);
        textPaint.setColor(Color.DKGRAY);

        for (int i = 0; i < n; i++) {
            float lungimeBar = (valori[i] / maxVal) * latimeMax;
            float startY = margineSus + i * (inaltimeBar * 2f) + inaltimeBar * 0.5f;

            paint.setColor(culori[i % culori.length]);
            canvas.drawRect(
                    axaX,
                    startY,
                    axaX + lungimeBar,
                    startY + inaltimeBar,
                    paint
            );

            // Eticheta stanga (V1, V2...)
            textPaint.setTextAlign(Paint.Align.RIGHT);
            canvas.drawText(
                    "V" + (i + 1),
                    axaX - 10,
                    startY + inaltimeBar * 0.7f,
                    textPaint
            );

            // Valoarea la capatul bar-ului
            textPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(
                    " " + valori[i],
                    axaX + lungimeBar,
                    startY + inaltimeBar * 0.7f,
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
        canvas.drawText("Bar Chart", width / 2f, height * 0.08f, titluPaint);
    }
}