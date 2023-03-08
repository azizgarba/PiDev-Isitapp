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
import tn.esprit.codefellaz.entities.Panier;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author ASUS
 */
public class PanierService implements InterfacePanier {

     Connection conn = MyConnection.getInstance().getCnx();
    
    @Override
    public void ajouterPanier(Panier p) {
          try {
            String requete2="INSERT INTO `paniers`(`id_client`) VALUES (?)";
            PreparedStatement pst2=conn.prepareStatement(requete2);
            
            pst2.setInt(1, p.getId_client());

            pst2.executeUpdate();
            
            System.out.println("Panier ajouté avec succès");//}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());     
        }
    }

    @Override
    public void ajouterPanier2(Panier p) {
        try {
            String requete2="INSERT INTO `paniers`(`id_panier`, `id_client`) VALUES (?,?)";
            PreparedStatement pst2=conn.prepareStatement(requete2);
            
            pst2.setInt(1, p.getId_panier());
            pst2.setInt(2,p.getId_client());

            pst2.executeUpdate();
            
            System.out.println("Categorie ajouté avec succès");//}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());     
        }
    }

    @Override
    public void modifierPanier(Panier p) {
         try {
            String requete="UPDATE `paniers` SET `id_client`=? WHERE `id_panier`=?";
            PreparedStatement pst=conn.prepareStatement(requete);
            
            pst.setInt(1, p.getId_client());
            pst.setInt(2, p.getId_panier());

             pst.executeUpdate();
            System.out.println("Panier modifié avec succès");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerPanier(int id) {
     try {
            String req = "DELETE FROM `paniers` WHERE id_panier = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("Panier supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Panier> afficherPanier() {
       List<Panier> PanierList = new ArrayList<>();
        try {
            String requete="SELECT * FROM paniers";
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                
                Panier pan = new Panier();
                pan.setId_panier(rs.getInt(1));
                pan.setId_client(rs.getInt(2));
                
                PanierList.add(pan);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); 
        }
        return PanierList;
    }
    }
    
