package com.example.lab4;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

// Luca Apahidean - bALena
public class Balena implements Serializable {

    // Enum pentru specia balenei
    public enum Specie {
        BALENA_ALBASTRA,
        BALENA_CU_COCOASA,
        BALENA_UCIGASA,
        BALENA_ALBA,
        BALENA_GRI
    }

    private String nume;           // String
    private int varsta;            // intreg
    private int greutate;          // intreg (tone)
    private boolean esteAdult;     // boolean
    private Specie specie;         // enum
    private Date anulNasterii;     // data nasterii (zi, luna, an)

    // Constructor implicit
    public Balena() {}

    // Constructor cu parametri
    public Balena(String nume, int varsta, int greutate, boolean esteAdult, Specie specie, Date anulNasterii) {
        this.nume = nume;
        this.varsta = varsta;
        this.greutate = greutate;
        this.esteAdult = esteAdult;
        this.specie = specie;
        this.anulNasterii = anulNasterii;
    }

    // Getteri si setteri
    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public int getVarsta() { return varsta; }
    public void setVarsta(int varsta) { this.varsta = varsta; }

    public int getGreutate() { return greutate; }
    public void setGreutate(int greutate) { this.greutate = greutate; }

    public boolean isEsteAdult() { return esteAdult; }
    public void setEsteAdult(boolean esteAdult) { this.esteAdult = esteAdult; }

    public Specie getSpecie() { return specie; }
    public void setSpecie(Specie specie) { this.specie = specie; }

    public Date getAnulNasterii() { return anulNasterii; }

    public void setAnulNasterii(Date anulNasterii) { this.anulNasterii = anulNasterii; }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        return "Balena '" + nume + '\'' + '\n' +
                "are varsta de " + varsta + " ani," + '\n' +
                "greutatea " + greutate + " tone." + '\n' +
                "Este adult? " + esteAdult + '\n' +
                "Specia sa este " + specie + ',' + '\n' +
                "iar data nasterii este " + sdf.format(anulNasterii);
    }
}
