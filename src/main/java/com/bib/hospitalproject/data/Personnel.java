package com.bib.hospitalproject.data;

public class Personnel {
    private String cin ;
    private String nom ;
    private String prenom ;
    private String login ;
    private String password ;
    private String fonction ;

    public Personnel (String cin ,String nom, String prenom,String login,String password,String fonction){
        this.cin=cin ;
        this.nom=nom;
        this.prenom=prenom;
        this.login=login;
        this.password=password ;
        this.fonction=fonction ;
    }

    public Personnel (String cin ,String nom, String prenom,String fonction){
        this.cin=cin ;
        this.nom=nom;
        this.prenom=prenom;
        this.fonction=fonction ;
    }

    public String getCin(){return cin;}
    public String getNom(){return nom;}
    public String getPrenom(){return prenom ;}
    public String getLogin(){return login ;}
    public String getPassword(){return password ;}
    public String getFonction(){return fonction ;}

    public String toString(){
        return "Personnel {" +
                "cin"+cin +
                "nom"+nom+
                "prenom"+prenom + "login"+login+
                "password"+password +
                "fonction"+fonction +
                "}";
    }
}

