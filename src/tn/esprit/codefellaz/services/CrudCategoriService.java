/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.codefellaz.entities.CategorieProduit;
import tn.esprit.codefellaz.entities.CategorieService;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author user
 * 
 * 
 * 
 * ////////////////////////////////crud fonctiennel ///////////////////////////////////////////////
 * 
 * 
 */
public class CrudCategoriService implements InterfaceCategorieService{
    Connection cnx ; 
    
    
    public CrudCategoriService(){
         cnx = MyConnection.getInstance().getCnx();
    }
    
    @Override
    
    
    
    public void ajouterCategorieService(CategorieService cs) {

          try {
            String requete2 = "insert into categories_services (nom_categorie_service) values (?)" ;
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setString(1, cs.getNomCategorieServices());
            pst.executeUpdate();
            System.out.println("Categorie ajouter");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
    }

    @Override
    public void modifierNomCategorieService(int idCategorieService, String nomCategorieService) {
        
         try {
            String requete = "UPDATE categories_services SET nom_categorie_service = ? WHERE id_categorie_service = ?" ;
            
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1,nomCategorieService);
            pst.setInt(2,idCategorieService);
            pst.executeUpdate();
            System.out.println("Categorie modifie");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    

    }

    @Override
    public void supprimerCategorieService(int idCategorieService) {
 try {
            String requete = "DELETE from categories_services WHERE id_categorie_service = ?" ;
            
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,idCategorieService);
            
            pst.executeUpdate();
            System.out.println("Categorie supprime");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    }

    @Override
    public List<CategorieService> afficherCategorieService() {
  List<CategorieService> myList = new  ArrayList<> ();

        try {
            String requete3 = "select * from categories_services " ;
            Statement st = cnx.createStatement() ;
            ResultSet rs = st.executeQuery(requete3);
            while(rs.next())
            {
                CategorieService c = new CategorieService() ;
                //CategorieService categorieService = new CategorieService() ;
                c.setIdCategorieServices(rs.getInt(1));
                c.setNomCategorieServices(rs.getString(2));
                myList.add(c) ; 
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());        }
    
        return myList ;
    }    
    
    
    public CategorieService returnCatwithname(String NomCategorie){
    return afficherCategorieService().stream().filter(p->(null == p.getNomCategorieServices() ? NomCategorie == null : p.getNomCategorieServices().equals(NomCategorie))).findFirst().orElse(null);
}
    
}
