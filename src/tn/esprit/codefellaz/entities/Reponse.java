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
public class Reponse {
   
    private int id_Reponse ;
    private int id_Reclamation ;
    private String description_Reponse ;

    public Reponse(int id_Reponse, int id_Reclamation, String description_Reponse) {
        this.id_Reponse = id_Reponse;
        this.id_Reclamation = id_Reclamation;
        this.description_Reponse = description_Reponse;
    }

    public Reponse(int id_Reclamation, String description_Reponse) {
        this.id_Reclamation = id_Reclamation;
        this.description_Reponse = description_Reponse;
    }

    public Reponse() {
    }

    public Reponse(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    public int getId_Reponse() {
        return id_Reponse;
    }

    public int getId_Reclamation() {
        return id_Reclamation;
    }

    public String getDescription_Reponse() {
        return description_Reponse;
    }

    public void setId_Reponse(int id_Reponse) {
        this.id_Reponse = id_Reponse;
    }

    public void setId_Reclamation(int id_Reclamation) {
        this.id_Reclamation = id_Reclamation;
    }

    public void setDescription_Reponse(String description_Reponse) {
        this.description_Reponse = description_Reponse;
    }

    @Override
    public String toString() {
        return "Reponses{" + "id_Reponse=" + id_Reponse + ", id_Reclamation=" + id_Reclamation + ", description_Reponse=" + description_Reponse + '}';
    }
    

}
