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
public class Panier {
    private int id_panier ;
    private int id_client ;

    public Panier(int id_panier, int id_client) {
        this.id_panier = id_panier;
        this.id_client = id_client;
    }

    public Panier() {
    }
    

    public Panier(int id_client) {
        this.id_client = id_client;
    }

    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    @Override
    public String toString() {
        return "Panier{" + "id_panier=" + id_panier + ", id_client=" + id_client + '}';
    }
   
    
}
