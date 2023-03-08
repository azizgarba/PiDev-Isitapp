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
public class cv {
    
    private int id_cv;
    private String bio_cv;
    private String diplome; 
    private int annee_diplome;
    private String institut;
    private String specialite;
    private int id_freelance;
    private String image;

    public cv(int id_cv, String bio_cv, String diplome, int annee_diplome, String institut, String specialite, int id_freelance,String image) {
        this.id_cv = id_cv;
        this.bio_cv = bio_cv;
        this.diplome = diplome;
        this.annee_diplome = annee_diplome;
        this.institut = institut;
        this.specialite = specialite;
        this.id_freelance = id_freelance;
         this.image = image;
    }

    public cv(String bio_cv, String diplome, int annee_diplome, String institut, String specialite, int id_freelance,String image) {
        this.bio_cv = bio_cv;
        this.diplome = diplome;
        this.annee_diplome = annee_diplome;
        this.institut = institut;
        this.specialite = specialite;
        this.id_freelance = id_freelance;
                this.image = image;

    }

    public cv(int id_cv, String bio_cv, String diplome, int annee_diplome, String institut, String specialite,String image) {
        this.id_cv = id_cv;
        this.bio_cv = bio_cv;
        this.diplome = diplome;
        this.annee_diplome = annee_diplome;
        this.institut = institut;
        this.specialite = specialite;
        this.image = image;
    }

    public cv(String bio_cv, String diplome, int annee_diplome, String institut, String specialite,String image) {
        this.bio_cv = bio_cv;
        this.diplome = diplome;
        this.annee_diplome = annee_diplome;
        this.institut = institut;
        this.specialite = specialite;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    
    
    
    public cv() {
    }

    public int getId_cv() {
        return id_cv;
    }

    public String getBio_cv() {
        return bio_cv;
    }

    public String getDiplome() {
        return diplome;
    }

    public int getAnnee_diplome() {
        return annee_diplome;
    }

    public String getInstitut() {
        return institut;
    }

    public String getSpecialite() {
        return specialite;
    }

    public int getId_freelance() {
        return id_freelance;
    }

    public void setId_cv(int id_cv) {
        this.id_cv = id_cv;
    }

    public void setBio_cv(String bio_cv) {
        this.bio_cv = bio_cv;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public void setAnnee_diplome(int annee_diplome) {
        this.annee_diplome = annee_diplome;
    }

    public void setInstitut(String institut) {
        this.institut = institut;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public void setId_freelance(int id_freelance) {
        this.id_freelance = id_freelance;
    }

    @Override
    public String toString() {
        return "cv{" + "id_cv=" + id_cv + ", bio_cv=" + bio_cv + ", diplome=" + diplome + ", annee_dimplome=" + annee_diplome + ", institut=" + institut + ", specialite=" + specialite + ", id_freelance=" + id_freelance + '}';
    }
    
    
    
}
