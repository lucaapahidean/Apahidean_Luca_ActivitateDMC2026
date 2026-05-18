package com.example.lab11;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DesenPieChart extends View {

    private float[] valori;

    private int[] culori = {
            Color.rgb(255, 0,   0),
            Color.rgb(0,   0,   255),
            Color.rgb(0,   255, 0),
            Color.rgb(240, 190, 20)
    };

    public DesenPieChart(Context context, float[] valori) {
        super(context);
        this.valori = valori;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        int width  = getWidth();
        int height = getHeight();

        // Centrul si raza cercului (~60% din latime)
        float raza    = width * 0.35f;
        float centruX = width  / 2f;
        float centruY = height * 0.40f;  // lasam loc legendei jos

        // Calculam suma totala
        float total = 0;
        for (float v : valori) total += v;

        // Desenam feliile
        float unghiStart = -90f; // incepem de sus
        for (int i = 0; i < valori.length; i++) {
            float unghi = (valori[i] / total) * 360f;
            paint.setColor(culori[i % culori.length]);
            canvas.drawArc(
                    centruX - raza,
                    centruY - raza,
                    centruX + raza,
                    centruY + raza,
                    unghiStart,
                    unghi,
                    true,
                    paint
            );
            unghiStart += unghi;
        }

        // Conturul cercului
        Paint contur = new Paint();
        contur.setStyle(Paint.Style.STROKE);
        contur.setColor(Color.WHITE);
        contur.setStrokeWidth(4);
        contur.setAntiAlias(true);
        canvas.drawCircle(centruX, centruY, raza, contur);

        // Legenda
        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.DKGRAY);

        Paint patratPaint = new Paint();
        patratPaint.setAntiAlias(true);

        float legendaX = centruX - raza;
        float legendaY = centruY + raza + 60;

        for (int i = 0; i < valori.length; i++) {
            patratPaint.setColor(culori[i % culori.length]);
            canvas.drawRect(legendaX, legendaY, legendaX + 40, legendaY + 40, patratPaint);

            float procent = (valori[i] / total) * 100f;
            String eticheta = String.format("Valoarea %d: %.1f  (%.1f%%)", i + 1, valori[i], procent);
            canvas.drawText(eticheta, legendaX + 55, legendaY + 33, textPaint);

            legendaY += 60;
        }
    }
}