package com.bib.hospitalproject.data;

public class Patient {
    private String cin ;
    private String nom ;
    private String prenom ;
    private String sexe ;
    private String tel ;

    public Patient (String cin , String nom,String prenom,String sexe,String tel){
        this.cin=cin;
        this.nom=nom;
        this.prenom=prenom;
        this.sexe=sexe;
        this.tel=tel;
    }

    public String getCin(){return cin;}
    public String getNom(){return nom;}
    public String getPrenom(){return prenom;}
    public String getSexe(){return sexe ;}
    public String getTel(){return tel;}

    public String ToString(){
        return "Patient { " +
                "cin:" + cin +
                "Nom"+nom +
                "prenom"+prenom +
                "sexe"+sexe +
                "tel"+tel +
                "}" ;
    }
}
