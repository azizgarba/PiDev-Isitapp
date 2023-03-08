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
public class Invitations {
    private int id_invi;
    private int id_event;
    private int id_freelancer;
//DEFAULT CONSTRUCTOR
    public Invitations() {
    }

    @Override
    public String toString() {
        return "Invitations{" + "id_invi=" + id_invi + ", id_event=" + id_event + ", id_freelancer=" + id_freelancer + '}';
    }
    
//CONSTRUCTOR WITHOUT ID
    public Invitations(int id_event, int id_freelancer) {
        this.id_event = id_event;
        this.id_freelancer = id_freelancer;
    }
//CONSTRUCTOR WITH ID
    public Invitations(int id_invi, int id_event, int id_freelancer) {
        this.id_invi = id_invi;
        this.id_event = id_event;
        this.id_freelancer = id_freelancer;
    }
//GETTERS & SETTERS
    public int getId_invi() {
        return id_invi;
    }

    public void setId_invi(int id_invi) {
        this.id_invi = id_invi;
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
    
    
}
