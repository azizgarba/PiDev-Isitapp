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
public class Achat {
     private int id_achat ;
    private int id_panier ;
    private int id_produit ;

    public Achat(int id_achat, int id_panier, int id_produit) {
        this.id_achat = id_achat;
        this.id_panier = id_panier;
        this.id_produit = id_produit;
    }

    public Achat() {
    }

    
    public Achat(int id_panier, int id_produit) {
        this.id_panier = id_panier;
        this.id_produit = id_produit;
    }

    public int getId_achat() {
        return id_achat;
    }

    public void setId_achat(int id_achat) {
        this.id_achat = id_achat;
    }

    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    @Override
    public String toString() {
        return "Achat{" + "id_achat=" + id_achat + ", id_panier=" + id_panier + ", id_produit=" + id_produit + '}';
    }
    
    
}
