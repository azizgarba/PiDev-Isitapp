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
public class Produit {
     
    private int id_produit ;
    private String nom_produit ;
    private float prix_produit ;
    private int id_categorie ;
    private int id_freelancer ;
    private String image_produit ;

    public Produit(int id_produit, String nom_produit, float prix_produit, int id_categorie, int id_freelancer, String image_produit) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.prix_produit = prix_produit;
        this.id_categorie = id_categorie;
        this.id_freelancer = id_freelancer;
        this.image_produit = image_produit;
    }

   
    
    public Produit() {
    }

    public Produit(String nom_produit, float prix_produit, int id_categorie, int id_freelancer, String image_produit) {
        this.nom_produit = nom_produit;
        this.prix_produit = prix_produit;
        this.id_categorie = id_categorie;
        this.id_freelancer = id_freelancer;
        this.image_produit = image_produit;
    }

    public int getId_produit() {
        return id_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public float getPrix_produit() {
        return prix_produit;
    }

    public int getId_categorie() {
        return id_categorie;
    }

    public int getId_freelancer() {
        return id_freelancer;
    }

    public String getImage_produit() {
        return image_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public void setPrix_produit(float prix_produit) {
        this.prix_produit = prix_produit;
    }

    public void setId_categorie(int id_categorie) {
        this.id_categorie = id_categorie;
    }

    public void setId_freelancer(int id_freelancer) {
        this.id_freelancer = id_freelancer;
    }

    public void setImage_produit(String image_produit) {
        this.image_produit = image_produit;
    }

    @Override
    public String toString() {
        return "Produit{" + "id_produit=" + id_produit + ", nom_produit=" + nom_produit + ", prix_produit=" + prix_produit + ", id_categorie=" + id_categorie + ", id_freelancer=" + id_freelancer + ", image_produit=" + image_produit + '}';
    }
    
    
}
