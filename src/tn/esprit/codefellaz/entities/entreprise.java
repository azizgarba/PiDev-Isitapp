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
public class entreprise {
    
    private int id_entreprise;
    private int id_representant;
    private String adresse;
    private int tel_entreprise;
    private String description_entreprise;
    private String image;

    public entreprise(int id_entreprise, int id_representant, String adresse, int tel_entreprise, String description_entreprise, String image) {
        this.id_entreprise = id_entreprise;
        this.id_representant = id_representant;
        this.adresse = adresse;
        this.tel_entreprise = tel_entreprise;
        this.description_entreprise = description_entreprise;
                        this.image = image;

    }

    public entreprise(int id_representant, String adresse, int tel_entreprise, String description_entreprise, String image) {
        this.id_representant = id_representant;
        this.adresse = adresse;
        this.tel_entreprise = tel_entreprise;
        this.description_entreprise = description_entreprise;
                this.image = image;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public entreprise() {
    }

    public int getId_entreprise() {
        return id_entreprise;
    }

    public int getId_representant() {
        return id_representant;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getTel_entreprise() {
        return tel_entreprise;
    }

    public String getDescription_entreprise() {
        return description_entreprise;
    }

    public void setId_entreprise(int id_entreprise) {
        this.id_entreprise = id_entreprise;
    }

    public void setId_representant(int id_representant) {
        this.id_representant = id_representant;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setTel_entreprise(int tel_entreprise) {
        this.tel_entreprise = tel_entreprise;
    }

    public void setDescription_entreprise(String description_entreprise) {
        this.description_entreprise = description_entreprise;
    }


    
    
    
}
