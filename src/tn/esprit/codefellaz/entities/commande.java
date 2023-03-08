/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user
 */
public class commande {
    
    
    
    private int idCommande ; 
    private int idClient ; 
    private int idService ; 
    private String dateCommande ;

    public commande(int idCommande, int idClient, int idService,String dateCommande) {
        this.idCommande = idCommande;
        this.idClient = idClient;
        this.idService = idService;
        Date date = new Date() ; 
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
          String dateStr = formatter.format(date);
        this.dateCommande = dateStr ;
    }

    public commande(int idClient, int idService,String dateCommande) {
        this.idClient = idClient;
        this.idService = idService;
         Date date = new Date() ; 
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
         String dateStr = formatter.format(date);
        this.dateCommande = dateStr ;
    }

    public commande() {
    }

    public String getDateCommande() {
         Date date = new Date() ; 
         SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
         String dateStr = formatter.format(date);
        this.dateCommande = dateStr ;
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande ;
    }

    @Override
    public String toString() {
        return "commande{" + "idCommande=" + idCommande + ", idClient=" + idClient + ", idService=" + idService + '}';
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }
    
    
    
    
}
