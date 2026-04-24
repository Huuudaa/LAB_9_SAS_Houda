package com.example.lab_9_sas_houda.beans;

public class Membre {
    private int id;
    private String nom, prenom, ville, genre;

    public Membre() {}

    public Membre(int id, String nom, String prenom,
                  String ville, String genre) {
        this.id     = id;
        this.nom    = nom;
        this.prenom = prenom;
        this.ville  = ville;
        this.genre  = genre;
    }

    public int getId()       { return id; }
    public String getNom()   { return nom; }
    public String getPrenom(){ return prenom; }
    public String getVille() { return ville; }
    public String getGenre() { return genre; }

    @Override
    public String toString() {
        return "Membre{id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", ville='" + ville + '\'' +
                ", genre='" + genre + '\'' + '}';
    }
}