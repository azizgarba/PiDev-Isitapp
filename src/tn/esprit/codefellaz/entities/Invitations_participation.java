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
public class Invitations_participation {
    private int id_invi_parti;
    private int id_event;
    private int id_freelancer;
    private String type_invi_parti;
//DEFAULT CONSTRUCTOR
    public Invitations_participation() {
    }

    @Override
    public String toString() {
        return "Invitations_participation{" + "id_invi_parti=" + id_invi_parti + ", id_event=" + id_event + ", id_freelancer=" + id_freelancer + ", type=" + type_invi_parti + '}';
    }

    public String getType() {
        return type_invi_parti;
    }

    public void setType(String type) {
        this.type_invi_parti = type;
    }

    
    
//CONSTRUCTOR WITHOUT ID
    public Invitations_participation(int id_event, int id_freelancer, String type) {
        this.id_event = id_event;
        this.id_freelancer = id_freelancer;
        this.type_invi_parti = type;
    }

 


//CONSTRUCTOR WITH ID
      public Invitations_participation(int id_invi_parti, int id_event, int id_freelancer, String type) {
        this.id_invi_parti = id_invi_parti;
        this.id_event = id_event;
        this.id_freelancer = id_freelancer;
        this.type_invi_parti = type;
    }
//GETTERS & SETTERS
    public int getId_invi() {
        return id_invi_parti;
    }

    public void setId_invi(int id_invi) {
        this.id_invi_parti = id_invi;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public int getId_freelancer() {
        return id_freelancer;
    }

    public void setId_freelancer(int id_freelancer) {
        this.id_freelancer = id_freelancer;
    }
    
    //is empty()
    public boolean isEmpty(){
        return type_invi_parti == null || type_invi_parti.isEmpty();
    }
    
    
}
