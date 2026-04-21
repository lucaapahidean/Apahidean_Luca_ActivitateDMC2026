package com.example.lab9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListActivity extends AppCompatActivity {

    // 5 balene: (url imagine, descriere, link pagina web)
    private static final String[][] DATE = {
            {
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8b/Humpback_whales_in_singing_position.jpg/330px-Humpback_whales_in_singing_position.jpg",
                    "Balena cu cocoasă (Megaptera novaeangliae)\nCunoscută pentru cântecele complexe.",
                    "https://en.wikipedia.org/wiki/Humpback_whale"
            },
            {
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Blue_Whale_001_noaa_body_color.jpg/330px-Blue_Whale_001_noaa_body_color.jpg",
                    "Balena albastră (Balaenoptera musculus)\nCel mai mare animal de pe Pământ.",
                    "https://en.wikipedia.org/wiki/Blue_whale"
            },
            {
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b1/Mother_and_baby_sperm_whale.jpg/330px-Mother_and_baby_sperm_whale.jpg",
                    "Balena cachalot (Physeter macrocephalus)\nAre cel mai mare creier din regnul animal.",
                    "https://en.wikipedia.org/wiki/Sperm_whale"
            },
            {
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/3/37/Killerwhales_jumping.jpg/330px-Killerwhales_jumping.jpg",
                    "Orca (Orcinus orca)\nNumită și balena ucigașă, trăiește în grupuri sociale.",
                    "https://en.wikipedia.org/wiki/Orca"
            },
            {
                    "https://upload.wikimedia.org/wikipedia/commons/thumb/e/e8/Oceanogr%C3%A0fic_29102004.jpg/330px-Oceanogr%C3%A0fic_29102004.jpg",
                    "Beluga (Delphinapterus leucas)\nSupranumită canarul mărilor pentru sunetele emise.",
                    "https://en.wikipedia.org/wiki/Beluga_whale"
            }
    };

    private List<BalenaItem> items;
    private BalenaAdapter    adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Construim lista de itemi fara bitmap
        items = new ArrayList<>();
        for (String[] d : DATE) {
            items.add(new BalenaItem(d[0], d[1], d[2]));
        }

        adapter = new BalenaAdapter(this, items);
        ListView listView = findViewById(R.id.listViewBalene);
        listView.setAdapter(adapter);

        // Click -> deschide WebViewActivity cu link-ul aferent
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String link = items.get(position).getWebLink();
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("link", link);
            startActivity(intent);
        });

        // Incarcam imaginile cu Executors
        Executor executor = Executors.newFixedThreadPool(5);
        Handler  handler  = new Handler(Looper.getMainLooper());

        for (int i = 0; i < items.size(); i++) {
            final int index = i;
            executor.execute(() -> {
                Bitmap bmp = descarcaImagine(items.get(index).getImageUrl());
                handler.post(() -> {
                    if (bmp != null) {
                        items.get(index).setBitmap(bmp);
                        adapter.notifyDataSetChanged();
                    }
                });
            });
        }
    }

    /** Descarca imaginea de la URL si returneaza un Bitmap. */
    private Bitmap descarcaImagine(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            return BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}