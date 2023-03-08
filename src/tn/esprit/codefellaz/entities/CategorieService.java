/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.esprit.codefellaz.entities;

/**
 *
 * @author dhiaajmi
 * 
 * update
 */
public class CategorieService{
    private int idCategorieServices ;
    private String nomCategorieServices  ; 

    @Override
    public String toString() {
        return "CategorieService{" + "idCategorieServices=" + idCategorieServices + ", nomCategorieServices=" + nomCategorieServices + '}';
    }

    
    
    
    public CategorieService() {
    }

    
    
    public CategorieService(int idCategorieServices, String nomCategorieServices) {
        this.idCategorieServices = idCategorieServices;
        this.nomCategorieServices = nomCategorieServices;
    }

    public CategorieService(String nomCategorieServices) {
        this.nomCategorieServices = nomCategorieServices;
    }

    public int getIdCategorieServices() {
        return idCategorieServices;
    }

    public String getNomCategorieServices() {
        return nomCategorieServices;
    }

    public void setIdCategorieServices(int idCategorieServices) {
        this.idCategorieServices = idCategorieServices;
    }

    public void setNomCategorieServices(String nomCategorieServices) {
        this.nomCategorieServices = nomCategorieServices;
    }
    
}