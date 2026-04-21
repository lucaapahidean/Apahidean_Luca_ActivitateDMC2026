package com.example.lab8;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Balena")
public class Balena {

    public enum Specie {
        BALENA_ALBASTRA,
        BALENA_CU_COCOASA,
        BALENA_UCIGASA,
        BALENA_ALBA,
        BALENA_GRI
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nume")
    private String nume;

    @ColumnInfo(name = "varsta")
    private int varsta;

    @ColumnInfo(name = "specie")
    private String specie;

    public Balena(String nume, int varsta, String specie) {
        this.nume   = nume;
        this.varsta = varsta;
        this.specie = specie;
    }

    public int    getId()     { return id; }
    public void   setId(int id) { this.id = id; }

    public String getNume()   { return nume; }
    public void   setNume(String nume) { this.nume = nume; }

    public int    getVarsta() { return varsta; }
    public void   setVarsta(int varsta) { this.varsta = varsta; }

    public String getSpecie() { return specie; }
    public void   setSpecie(String specie) { this.specie = specie; }

    @Override
    public String toString() {
        return "Balena{" +
                "nume='" + nume + '\'' +
                ", varsta=" + varsta +
                ", specie='" + specie + '\'' +
                '}';
    }
}