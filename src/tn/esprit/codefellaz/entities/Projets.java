/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author myriam-PC
 */
public class Projets {
    private int id_projet;
    private String nom_projet;
    private int priorite_projet;
    private String dateP_projet;
    private String dateL_projet;
    private String description_projet;
    private float progression_P ;
    private int id_freelancer;
    private List <Taches> listtaches = new ArrayList<>();

    @Override
    public String toString() {
        return "Projets{" + "id_projet=" + id_projet + ", nom_projet=" + nom_projet + ", priorite_projet=" + priorite_projet + ", dateP_projet=" + dateP_projet + ", dateL_projet=" + dateL_projet + ", description_projet=" + description_projet + ", progression_P=" + progression_P + ", id_freelancer=" + id_freelancer + ", listtaches=" + listtaches + '}';
    }

    public Projets(int id_projet, String nom_projet, int priorite_projet, String dateP_projet, String dateL_projet, String description_projet, float progression_P, int id_freelancer) {
        this.id_projet = id_projet;
        this.nom_projet = nom_projet;
        this.priorite_projet = priorite_projet;
        this.dateP_projet = dateP_projet;
        this.dateL_projet = dateL_projet;
        this.description_projet = description_projet;
        this.progression_P = progression_P;
        this.id_freelancer = id_freelancer;
    }

    public Projets(String nom_projet, int priorite_projet, String dateP_projet, String dateL_projet, String description_projet, float progression_P, int id_freelancer) {
        this.nom_projet = nom_projet;
        this.priorite_projet = priorite_projet;
        this.dateP_projet = dateP_projet;
        this.dateL_projet = dateL_projet;
        this.description_projet = description_projet;
        this.progression_P = progression_P;
        this.id_freelancer = id_freelancer;
    }

    

    public Projets() {
    }

    public Projets(int id_projet, int priorite_projet, String dateL_projet, String description_projet) {
        this.id_projet = id_projet;
        this.priorite_projet = priorite_projet;
        this.dateL_projet = dateL_projet;
        this.description_projet = description_projet;
    }

    public Projets(int priorite_projet, String dateL_projet, String description_projet) {
        this.priorite_projet = priorite_projet;
        this.dateL_projet = dateL_projet;
        this.description_projet = description_projet;
    }

    public Projets(int priorite_projet, String dateP_projet, String dateL_projet, String description_projet) {
        this.priorite_projet = priorite_projet;
        this.dateP_projet = dateP_projet;
        this.dateL_projet = dateL_projet;
        this.description_projet = description_projet;
    }


   


    

    public int getId_projet() {
        return id_projet;
    }

    public void setId_projet(int id_projet) {
        this.id_projet = id_projet;
    }

    public String getNom_projet() {
        return nom_projet;
    }

    public void setNom_projet(String nom_projet) {
        this.nom_projet = nom_projet;
    }

    public int getPriorite_projet() {
        return priorite_projet;
    }

    public void setPriorite_projet(int priorite_projet) {
        this.priorite_projet = priorite_projet;
    }

    public String getDateP_projet() {
        return dateP_projet;
    }

    public void setDateP_projet(String dateP_projet) {
        this.dateP_projet = dateP_projet;
    }

    public String getDateL_projet() {
        return dateL_projet;
    }

    public void setDateL_projet(String dateL_projet) {
        this.dateL_projet = dateL_projet;
    }

    public String getDescription_projet() {
        return description_projet;
    }

    public void setDescription_projet(String description_projet) {
        this.description_projet = description_projet;
    }

    public float getProgression_P() {
        return progression_P;
    }

    public void setProgression_P(float progression_P) {
        this.progression_P = progression_P;
    }

    public List<Taches> getListtaches() {
        return listtaches;
    }

    public void setListtaches(List<Taches> listtaches) {
        this.listtaches = listtaches;
    }

    public int getId_freelancer() {
        return id_freelancer;
    }

    public void setId_freelancer(int id_freelancer) {
        this.id_freelancer = id_freelancer;
    }
    
    
    
    
}