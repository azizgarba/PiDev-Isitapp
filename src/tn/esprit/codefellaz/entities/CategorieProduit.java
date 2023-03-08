/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;

/**
 *
 * @author ASUS
 */
public class CategorieProduit {
    private int id_categorie_produit  ;
    private String nom_categorie_produit ;

    public CategorieProduit() {
    }

    public CategorieProduit(String nom_categorie_produit) {
        this.nom_categorie_produit = nom_categorie_produit;
    }
    
    public CategorieProduit(int id_categorie_produit , String nom_categorie_produit) {
        this.id_categorie_produit  = id_categorie_produit ;
        this.nom_categorie_produit = nom_categorie_produit;
    }

    public int getid_categorie_produit () {
        return id_categorie_produit ;
    }

    public String getNom_categorie_produit() {
        return nom_categorie_produit;
    }

    public void setid_categorie_produit (int id_categorie_produit ) {
        this.id_categorie_produit  = id_categorie_produit ;
    }

    public void setNom_categorie_produit(String nom_categorie_produit) {
        this.nom_categorie_produit = nom_categorie_produit;
    }

    @Override
    public String toString() {
        return "CategorieProduit{" + "id_categorie_produit =" + id_categorie_produit  + ", nom_categorie_produit=" + nom_categorie_produit + '}';
    }

    
}
