/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;

/**
 *
 * @author user
 * 
 * 
 * 
 * update 
 */
public class Avis {
    
    int idAvis ; 
    int client ; 
    String descriptionAvis ; 
    int nbEtoiles  ; 
    int idService ; 

    @Override
    public String toString() {
        return "Avis{" + "idAvis=" + idAvis + ", client=" + client + ", descriptionAvis=" + descriptionAvis + ", nbEtoiles=" + nbEtoiles + ", idService=" + idService + '}';
    }

    public Avis(int client, int nbEtoiles, int idService) {
        this.client = client;
        this.nbEtoiles = nbEtoiles;
        this.idService = idService;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

  
    
    public Avis(int idAvis, int client, String descriptionAvis, int nbEtoiles) {
        this.idAvis = idAvis;
        this.client = client;
        this.descriptionAvis = descriptionAvis;
        this.nbEtoiles = nbEtoiles;
    }

    public Avis(int client, String descriptionAvis, int nbEtoiles) {
        this.client = client;
        this.descriptionAvis = descriptionAvis;
        this.nbEtoiles = nbEtoiles;
    }

    public int getIdAvis()  {
        return idAvis;
    }

    public void setIdAvis(int idAvis) {
        this.idAvis = idAvis;
    }

    public int getClient() {
        return client;
    }

    public void setClient(User User) {
        this.client = User.getId();
    }

    public String getDescriptionAvis() {
        return descriptionAvis;
    }

    public void setDescriptionAvis(String descriptionAvis) {
        this.descriptionAvis = descriptionAvis;
    }

    public int getNbEtoiles() {
        return nbEtoiles;
    }

    public void setNbEtoiles(int nbEtoiles) {
        this.nbEtoiles = nbEtoiles;
    }

    
    
}