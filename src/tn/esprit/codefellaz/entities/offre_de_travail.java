/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;



/**
 *
 * @author Khouloud DAIRA
 */
public class offre_de_travail {
    
    private int id_offre_de_travail;
    private String titre_offre_de_travail;
    private String type_offre_de_travail;
    private float salaire_offre_de_travail;
    private String specialite_offre_de_travail;
    private int entreprise_offre_de_travail;
    private String description_offre_de_travail;

    public int getEntreprise_offre_de_travail() {
        return entreprise_offre_de_travail;
    }

    public void setEntreprise_offre_de_travail(int entreprise_offre_de_travail) {
        this.entreprise_offre_de_travail = entreprise_offre_de_travail;
    }

    public offre_de_travail() {
    }

    public offre_de_travail(int id_offre_de_travail, String titre_offre_de_travail, String type_offre_de_travail, float salaire_offre_de_travail, String specialite_offre_de_travail, int entreprise_offre_de_travail, String description_offre_de_travail) {
        this.id_offre_de_travail = id_offre_de_travail;
        this.titre_offre_de_travail = titre_offre_de_travail;
        this.type_offre_de_travail = type_offre_de_travail;
        this.salaire_offre_de_travail = salaire_offre_de_travail;
        this.specialite_offre_de_travail = specialite_offre_de_travail;
        this.entreprise_offre_de_travail = entreprise_offre_de_travail;
        this.description_offre_de_travail = description_offre_de_travail;
    }

    public offre_de_travail(String titre_offre_de_travail, String type_offre_de_travail, float salaire_offre_de_travail, String specialite_offre_de_travail, int entreprise_offre_de_travail, String description_offre_de_travail) {
        this.titre_offre_de_travail = titre_offre_de_travail;
        this.type_offre_de_travail = type_offre_de_travail;
        this.salaire_offre_de_travail = salaire_offre_de_travail;
        this.specialite_offre_de_travail = specialite_offre_de_travail;
        this.entreprise_offre_de_travail = entreprise_offre_de_travail;
        this.description_offre_de_travail = description_offre_de_travail;
    }

   

    

    public int getId_offre_de_travail() {
        return id_offre_de_travail;
    }

    public String getTitre_offre_de_travail() {
        return titre_offre_de_travail;
    }

    public String getType_offre_de_travail() {
        return type_offre_de_travail;
    }

    public float getSalaire_offre_de_travail() {
        return salaire_offre_de_travail;
    }

    public String getSpecialite_offre_de_travail() {
        return specialite_offre_de_travail;
    }



    public String getDescription_offre_de_travail() {
        return description_offre_de_travail;
    }

    public void setId_offre_de_travail(int id_offre_de_travail) {
        this.id_offre_de_travail = id_offre_de_travail;
    }

    public void setTitre_offre_de_travail(String titre_offre_de_travail) {
        this.titre_offre_de_travail = titre_offre_de_travail;
    }

    public void setType_offre_de_travail(String type_offre_de_travail) {
        this.type_offre_de_travail = type_offre_de_travail;
    }

    public void setSalaire_offre_de_travail(float salaire_offre_de_travail) {
        this.salaire_offre_de_travail = salaire_offre_de_travail;
    }

    public void setSpecialite_offre_de_travail(String specialite_offre_de_travail) {
        this.specialite_offre_de_travail = specialite_offre_de_travail;
    }

 

    public void setDescription_offre_de_travail(String description_offre_de_travail) {
        this.description_offre_de_travail = description_offre_de_travail;
    }


    
    

  
    
    
    
}

    