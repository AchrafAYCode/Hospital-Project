package com.bib.hospitalproject.data;

public class Medicament {
    private Integer ref ;
    private String libelle ;
    private double prix ;

    public Medicament(int ref , String libelle , double prix ){
        this.ref=ref ;
        this.libelle=libelle;
        this.prix=prix ;
    }

    public Integer getRef(){return ref ;}
    public String getLibelle(){return libelle ;}
    public double getPrix(){return prix ;}

@Override
    public String toString(){
        return "Livre{" +
                "Reference : " +ref +
                "libelle : " + libelle +
                "prix : " + prix +
                "}" ;
}


}
