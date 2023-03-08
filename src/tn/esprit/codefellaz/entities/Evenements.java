/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;

/**
 *
 * @author MAG-PC
 */
public class Evenements {

    private int id_event;
    private String name_event;
    private String logo_event;
    private String desc_event;
    private String emplacement_event;
    private String date_event;
    private int nbr_perso_event;
    private int type_event;
    private int id_representant;
    
// CONSTRUCTOR WITH ID

    public Evenements(int id_event, String name_event, String logo_event, String desc_event, String emplacement_event, String date_event, int nbr_perso_event,int type_event, int id_representant) {
        this.id_event = id_event;
        this.name_event = name_event;
        this.logo_event = logo_event;
        this.desc_event = desc_event;
        this.emplacement_event = emplacement_event;
        this.date_event = date_event;
        this.nbr_perso_event = nbr_perso_event;
        this.type_event = type_event;
        this.id_representant = id_representant;
    }

//DEFAULT CONSTRUCTOR 
    public Evenements() {
    }
//CONSTRUCTOR WITHOUT ID

    public Evenements(String name_event, String logo_event, String desc_event, String emplacement_event, String date_event, int nbr_perso_event, int type_event,int id_representant) {
        this.name_event = name_event;
        this.logo_event = logo_event;
        this.desc_event = desc_event;
        this.emplacement_event = emplacement_event;
        this.date_event = date_event;
        this.nbr_perso_event = nbr_perso_event;
        this.type_event = type_event;
        this.id_representant = id_representant;
    }

    //   GETTERS & SETTERS
    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getName_event() {
        return name_event;
    }

    public void setName_event(String name_event) {
        this.name_event = name_event;
    }

    public String getLogo_event() {
        return logo_event;
    }

    public void setLogo_event(String logo_event) {
        this.logo_event = logo_event;
    }

    public String getDesc_event() {
        return desc_event;
    }

    public void setDesc_event(String desc_event) {
        this.desc_event = desc_event;
    }

    public String getEmplacement_event() {
        return emplacement_event;
    }

    public void setEmplacement_event(String emplacement_event) {
        this.emplacement_event = emplacement_event;
    }

    public String getDate_event() {
        return date_event;
    }

    public void setDate_event(String date_event) {
        this.date_event = date_event;
    }

    public int getNbr_perso_event() {
        return nbr_perso_event;
    }

    public void setNbr_perso_event(int nbr_perso_event) {
        this.nbr_perso_event = nbr_perso_event;
    }

    public int getType_event() {
        return type_event;
    }

    public void setType_event(int type_event) {
        this.type_event = type_event;
    }
    

    public int getId_representant() {
        return id_representant;
    }

    public void setId_representant(int id_representant) {
        this.id_representant = id_representant;
    }

    @Override
    public String toString() {
        return "Evenements{" + "id_event=" + id_event + ", name_event=" + name_event + ", logo_event=" + logo_event + ", desc_event=" + desc_event + ", emplacement_event=" + emplacement_event + ", date_event=" + date_event + ", nbr_perso_event=" + nbr_perso_event + ", type_event=" + type_event + ", id_representant=" + id_representant + '}';
    }

    

}
