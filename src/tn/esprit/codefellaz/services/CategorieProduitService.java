/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.CategorieProduit;
import tn.esprit.codefellaz.utils.MyConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class CategorieProduitService implements InterfaceCategorieProduit{

      
        Connection conn = MyConnection.getInstance().getCnx();
        
    @Override
    public void ajouterCategorieProduit(CategorieProduit cp) {
        try {
            String requete2="INSERT INTO `categories_produits`(`nom_categorie_produit`) VALUES (?)";
            PreparedStatement pst2=conn.prepareStatement(requete2);
            
            pst2.setString(1, cp.getNom_categorie_produit());

            pst2.executeUpdate();
            
            System.out.println("Categorie ajouté avec succès");//}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());     
        }
    }

    @Override
    public void ajouterCategorieProduit2(CategorieProduit cp) {
         try {
            String requete2="INSERT INTO `categories_produits`(`id_categorie_produit`, `nom_categorie_produit`) VALUES (?,?)";
            PreparedStatement pst2=conn.prepareStatement(requete2);
            
            pst2.setInt(1, cp.getid_categorie_produit());
            pst2.setString(2,cp.getNom_categorie_produit());

            pst2.executeUpdate();
            
            System.out.println("Categorie ajouté avec succès");//}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());     
        }
    }

    @Override
    public void modifierCategorieProduit(CategorieProduit cp) {
       try {
            String requete="UPDATE `categories_produits` SET `nom_categorie_produit`=? WHERE `id_categorie_produit`=?";
            PreparedStatement pst=conn.prepareStatement(requete);
            
            pst.setString(1, cp.getNom_categorie_produit());
            pst.setInt(2, cp.getid_categorie_produit());

             pst.executeUpdate();
            System.out.println("Categorie modifiée avec succès");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerCategorieProduit(int id) {
         try {
            String req = "DELETE FROM `categories_produits` WHERE id_categorie_produit = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("Categorie supprimée avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<CategorieProduit> afficherCategorieProduit() {
       List<CategorieProduit> CategorieProduitList=new ArrayList<>();
        try {
            String requete="SELECT * FROM categories_produits";
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                
                CategorieProduit catp = new CategorieProduit();
                catp.setid_categorie_produit(rs.getInt(1));
                catp.setNom_categorie_produit(rs.getString(2));
                
                CategorieProduitList.add(catp);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); 
        }
        return CategorieProduitList;
    }
    public List<String> ListeCategorie() {
        List<String> CategorieProduitList=new ArrayList<>();
        
        try {
            String requete="SELECT nom_categorie_produit FROM categories_produits";
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                
                CategorieProduit catp = new CategorieProduit();
                catp.setNom_categorie_produit(rs.getString(1));
                
                CategorieProduitList.add(catp.getNom_categorie_produit());
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); 
        }
        return CategorieProduitList;
    }
    
    public int RetourID(String NomCategorie) {
     int Answer = -1 ;
     
     try {
     Statement stmt = conn.createStatement();
     ResultSet rs = stmt.executeQuery("SELECT id_categorie_produit FROM categories_produits WHERE `nom_categorie_produit`=112");
      
        if (rs.next()) {
    Answer  = rs.getInt("id_categorie_produit");
   
    
        }
    } catch (SQLException ex) {
            System.err.println(ex.getMessage()); 
        }
     
     return Answer ;
}
    public CategorieProduit returnCatwithname(String NomCategorie){
    return afficherCategorieProduit().stream().filter(p->(null == p.getNom_categorie_produit() ? NomCategorie == null : p.getNom_categorie_produit().equals(NomCategorie))).findFirst().orElse(null);
}
public CategorieProduit findById(int id){
    return afficherCategorieProduit().stream().filter(p->p.getid_categorie_produit()==id).findFirst().orElse(null);
}

public CategorieProduit findByName(String name){
       return afficherCategorieProduit().stream().filter(p->p.getNom_categorie_produit().equals(name)).findFirst().orElse(null);  
}
     
//     try {
//            String requete="SELECT id_categorie_produit FROM categories_produits WHERE `nom_categorie_produit`=?";
//            
//            PreparedStatement pst=conn.prepareStatement(requete);
//            
//            pst.setString(1, NomCategorie);
//            
//            ResultSet rs=pst.executeQuery(requete);
//            if(rs.next()){
//                
//                Answer = rs.getInt("id_categorie_produit") ;
//            }
//        } catch (SQLException ex) {
//            System.err.println(ex.getMessage()); 
//        }
//     
          //   PreparedStatement pst=conn.prepareStatement(requete);
            
          //  pst.setString(1,NomCategorie);

          //   ResultSet rs =  pst.executeQuery();
         //    
         //   Answer = rs.getInt(1);
        //    System.out.println("Found the right ID");
      //  }// 
       // catch (SQLException ex) {
        //    System.err.println(ex.getMessage());
      //  }
       
    
        
    }
    
    
    
    
    

