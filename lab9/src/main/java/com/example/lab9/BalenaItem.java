package com.example.lab9;

import android.graphics.Bitmap;

public class BalenaItem {
    private String imageUrl;
    private String descriere;
    private String webLink;
    private Bitmap bitmap;

    public BalenaItem(String imageUrl, String descriere, String webLink) {
        this.imageUrl = imageUrl;
        this.descriere = descriere;
        this.webLink = webLink;
    }

    public String getImageUrl()  { return imageUrl; }
    public String getDescriere() { return descriere; }
    public String getWebLink()   { return webLink; }
    public Bitmap getBitmap()    { return bitmap; }
    public void   setBitmap(Bitmap bitmap) { this.bitmap = bitmap; }
}