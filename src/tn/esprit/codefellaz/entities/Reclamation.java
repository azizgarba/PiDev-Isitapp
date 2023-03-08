/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;

/**
 *
 * @author Meriem
 */
public class Reclamation {
    
    private int id_Reclamation ;
    private int id_user ;
    private String objet_Reclamation ;
    private String description_Reclamation ;
    private String categorie_Reclamation ;
    private int etat_Reclamation ;

    public Reclamation() {
    }

    public Reclamation(int id_Reclamation) {
        this.id_Reclamation = id_Reclamation;
    }

    public Reclamation(String objet_Reclamation, String description_Reclamation, String categorie_Reclamation, int etat_Reclamation) {
        this.objet_Reclamation = objet_Reclamation;
        this.description_Reclamation = description_Reclamation;
        this.categorie_Reclamation = categorie_Reclamation;
        this.etat_Reclamation = etat_Reclamation;
    }

   
    

    public Reclamation(int id_Reclamation, int id_user, String objet_Reclamation, String description_Reclamation, String categorie_Reclamation, int etat_Reclamation) {
        this.id_Reclamation = id_Reclamation;
        this.id_user = id_user;
        this.objet_Reclamation = objet_Reclamation;
        this.description_Reclamation = description_Reclamation;
        this.categorie_Reclamation = categorie_Reclamation;
        this.etat_Reclamation = etat_Reclamation;
    }

    public Reclamation(int id_user, String objet_Reclamation, String description_Reclamation, String categorie_Reclamation, int etat_Reclamation) {
        this.id_user = id_user;
        this.objet_Reclamation = objet_Reclamation;
        this.description_Reclamation = description_Reclamation;
        this.categorie_Reclamation = categorie_Reclamation;
        this.etat_Reclamation = etat_Reclamation;
    }

    public int getId_Reclamation() {
        return id_Reclamation;
    }

    public void setId_Reclamation(int id_Reclamation) {
        this.id_Reclamation = id_Reclamation;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getObjet_Reclamation() {
        return objet_Reclamation;
    }

    public void setObjet_Reclamation(String objet_Reclamation) {
        this.objet_Reclamation = objet_Reclamation;
    }

    public String getDescription_Reclamation() {
        return description_Reclamation;
    }

    public void setDescription_Reclamation(String description_Reclamation) {
        this.description_Reclamation = description_Reclamation;
    }

    public String getCategorie_Reclamation() {
        return categorie_Reclamation;
    }

    public void setCategorie_Reclamation(String categorie_Reclamation) {
        this.categorie_Reclamation = categorie_Reclamation;
    }

    public int getEtat_Reclamation() {
        return etat_Reclamation;
    }

    public void setEtat_Reclamation(int etat_Reclamation) {
        this.etat_Reclamation = etat_Reclamation;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id_Reclamation=" + id_Reclamation + ", id_user=" + id_user + ", objet_Reclamation=" + objet_Reclamation + ", description_Reclamation=" + description_Reclamation + ", categorie_Reclamation=" + categorie_Reclamation + ", etat_Reclamation=" + etat_Reclamation + '}';
    }
    
    
    


  
    
    
    
    
    
    
}
