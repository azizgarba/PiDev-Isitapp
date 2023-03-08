/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.esprit.codefellaz.entities;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import pidev_isitapp.PiDev_IsItApp;

/**
 *
 * @author dhiaajmi
 *
 * update
 */
public class DemandeService {

    private int idDemande;
    private String nomDemande;
    private float budget;
    private String description;
    private String dateLimite;
    private int clientProprietaire;
    private Double priorite;

    
    @Override
    public String toString() {
        return "DemandeService{" + "idDemande=" + idDemande + ", nomDemande=" + nomDemande + ", budget=" + budget + ", description=" + description + ", dateLimite=" + dateLimite + ", clientProprietaire=" + clientProprietaire + ", priorite=" + priorite + '}';
    }

    public DemandeService(int idDemande, float budget, String description) {
        this.idDemande = idDemande;
        this.budget = budget;
        this.description = description;
    }

    
    
    
    
    public DemandeService() {
    }

    
    
    
    public DemandeService(int idDemande) {
        this.idDemande = idDemande;
    }

    
    
    
    
    public DemandeService(int idDemande , float budget, String description, String dateLimite ) {
        this.idDemande = idDemande ; 
        this.budget = budget;
        this.description = description;
        this.dateLimite = dateLimite;
    }

    
    
    public DemandeService(String nomDemande, float budget, String description, String dateLimite, int clientProprietaire) {
        this.nomDemande = nomDemande;
        this.budget = budget;
        this.description = description;
        this.dateLimite = dateLimite;
        this.clientProprietaire = clientProprietaire;
    }

    
    
    public DemandeService(int idDemande, String nomDemande, float budget, String description, String dateLimite, int clientProprietaire, Double priorite) {
        this.idDemande = idDemande;
        this.nomDemande = nomDemande;
        this.budget = budget;
        this.description = description;
        this.dateLimite = dateLimite;
        this.clientProprietaire = clientProprietaire;
        this.priorite = priorite;
    }

    public DemandeService(String nomDemande, float budget, String description, String dateLimite, int clientProprietaire, Double priorite) {
        this.nomDemande = nomDemande;
        this.budget = budget;
        this.description = description;
        this.dateLimite = dateLimite;
        this.clientProprietaire = clientProprietaire;
        this.priorite = priorite;
    }

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public String getNomDemande() {
        return nomDemande;
    }

    public void setNomDemande(String nomDemande) {
        this.nomDemande = nomDemande;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateLimite() {
        return dateLimite;
    }

    public void setDateLimite(String dateLimite) {
        this.dateLimite = dateLimite;
    }

    public int getClientProprietaire() {
        return clientProprietaire;
    }

    public void setClientProprietaire(int id) {
        this.clientProprietaire = id;

    }

    public Double getPriorite() {
        return priorite;
    }

    /**
     *
     * @param dateLimite
     * @param prix
     */
    public void setPriorite(String dateLimite, float prix) {
        this.priorite = calculatePriorite(dateLimite, prix);
    }

    ////////   metier  priorite   //////
    public Double calculatePriorite(String dateLimite, float prix) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Double prioriteNA = 365.0;
        try {
            date = formatter.parse(dateLimite);
        } catch (ParseException ex) {
            Logger.getLogger(PiDev_IsItApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date currentDate = new Date();
        Long difference = currentDate.getTime() - date.getTime();
        Long differenceInDays = difference / (1000 * 60 * 60 * 24);

        if (differenceInDays <= -365) {

            prioriteNA = 0.0;

            return prioriteNA;
        } else {
            prioriteNA = (prioriteNA + differenceInDays) * 0.9 + (prix) * 0.1;
            DecimalFormat df = new DecimalFormat("#.##");

            Double prioriteArrondi = Math.round(prioriteNA * 100.0) / 100.0;

            return prioriteArrondi;

        }

    }

}