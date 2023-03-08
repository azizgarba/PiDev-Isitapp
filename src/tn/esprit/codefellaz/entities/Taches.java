/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;

import javafx.concurrent.Task;

/**
 *
 * @author myriam-PC
 */
public class Taches {
    private int id_tache;
    private String nom_tache;
    private int priorite_tache;
    private String dateP_tache;
    private String dateL_tache;
    private String description_tache;
    private int type_tache;
    private int id_projet;

    @Override
    public String toString() {
        return "Taches{" + "id_tache=" + id_tache + ", nom_tache=" + nom_tache + ", priorite_tache=" + priorite_tache + ", dateP_tache=" + dateP_tache + ", dateL_tache=" + dateL_tache + ", description_tache=" + description_tache + ", type_tache=" + type_tache + ", id_projet=" + id_projet + '}';
    }

    
    public Taches() {
    }
 
    public Taches(int id_tache, String nom_tache, int priorite_tache, String dateL_tache, String description_tache, int type_tache, int id_projet) {
        this.id_tache = id_tache;
        this.nom_tache = nom_tache;
        this.priorite_tache = priorite_tache;
        this.dateL_tache = dateL_tache;
        this.description_tache = description_tache;
        this.type_tache = type_tache;
        this.id_projet = id_projet;
    }

    
    
    public Taches(String nom_tache, int priorite_tache, String dateP_tache, String dateL_tache, String description_tache, int type_tache, int id_projet) {
        this.nom_tache = nom_tache;
        this.priorite_tache = priorite_tache;
        this.dateP_tache = dateP_tache;
        this.dateL_tache = dateL_tache;
        this.description_tache = description_tache;
        this.type_tache = type_tache;
        this.id_projet = id_projet;
    }

    public Taches(int id_tache, String nom_tache, int priorite_tache, String dateP_tache, String dateL_tache, String description_tache, int type_tache, int id_projet) {
        this.id_tache = id_tache;
        this.nom_tache = nom_tache;
        this.priorite_tache = priorite_tache;
        this.dateP_tache = dateP_tache;
        this.dateL_tache = dateL_tache;
        this.description_tache = description_tache;
        this.type_tache = type_tache;
        this.id_projet = id_projet;
    }


    public int getId_tache() {
        return id_tache;
    }

    public void setId_tache(int id_tache) {
        this.id_tache = id_tache;
    }

    public String getNom_tache() {
        return nom_tache;
    }

    public void setNom_tache(String nom_tache) {
        this.nom_tache = nom_tache;
    }

    public int getPriorite_tache() {
        return priorite_tache;
    }

    public void setPriorite_tache(int priorite_tache) {
        this.priorite_tache = priorite_tache;
    }

    public String getDateP_tache() {
        return dateP_tache;
    }

    public void setDateP_tache(String dateP_tache) {
        this.dateP_tache = dateP_tache;
    }

    public String getDateL_date() {
        return dateL_tache;
    }

    public void setDateL_tache(String dateL_tache) {
        this.dateL_tache = dateL_tache;
    }

    public String getDescription_tache() {
        return description_tache;
    }

    public void setDescription_tache(String description_tache) {
        this.description_tache = description_tache;
    }

    public int getType_tache() {
        return type_tache;
    }

    public void setType_tache(int type_tache) {
        this.type_tache = type_tache;
    }

    public int getId_projet() {
        return id_projet;
    }

    public void setId_projet(int id_projet) {
        this.id_projet = id_projet;
    }
    
    
    
    
}