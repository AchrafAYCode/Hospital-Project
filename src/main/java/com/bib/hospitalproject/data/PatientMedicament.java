package com.bib.hospitalproject.data;

public class PatientMedicament {
    private int refMed;
    private int cinPat;

    public PatientMedicament(int ref, int cin) {
        this.refMed = ref;
        this.cinPat = cin;
    }

    public int getRefMed() {
        return refMed;
    }

    public int getCinPat() {
        return cinPat;
    }


}