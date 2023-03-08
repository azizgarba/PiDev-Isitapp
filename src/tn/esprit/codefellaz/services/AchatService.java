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
import java.util.stream.Collectors;
import tn.esprit.codefellaz.entities.Achat;
import tn.esprit.codefellaz.entities.Panier;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author ASUS
 */
public class AchatService implements InterfaceAchat{

     Connection conn = MyConnection.getInstance().getCnx();
     
    @Override
    public void ajouterAchat(Achat a) {
        try {
            String requete2="INSERT INTO `achats`(`id_panier`, `id_produit`) VALUES (?,?)";
            PreparedStatement pst2=conn.prepareStatement(requete2);
            
            pst2.setInt(1, a.getId_panier());
            pst2.setInt(2, a.getId_produit());
            
            pst2.executeUpdate();
            
            System.out.println("achat ajouté avec succès");//}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());     
        }
    }

    @Override
    public void ajouterAchat2(Achat a) {
        try {
            String requete2="INSERT INTO `achats`(`id_achat`,`id_panier`, `id_produit`) VALUES (?,?,?)";
            PreparedStatement pst2=conn.prepareStatement(requete2);
            
            pst2.setInt(1, a.getId_achat());
            pst2.setInt(2, a.getId_panier());
            pst2.setInt(3, a.getId_produit());
            
            pst2.executeUpdate();
            
            System.out.println("achat ajouté avec succès");//}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());     
        }
    }

    @Override
    public void modifierAchat(Achat a) {
       try {
            String requete="UPDATE `achats` SET `id_panier`=? , `id_produit`=? WHERE `id_achat`=?";
            PreparedStatement pst=conn.prepareStatement(requete);
            
            pst.setInt(1, a.getId_panier());
            pst.setInt(2, a.getId_produit());
            pst.setInt(3, a.getId_achat());

             pst.executeUpdate();
            System.out.println("Achat modifié avec succès");
        } 
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerAchat(int id) {
       try {
            String req = "DELETE FROM `achats` WHERE id_achat = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("Achat supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Achat> afficherAchat() {
        List<Achat> AchatList = new ArrayList<>();
        try {
            String requete="SELECT * FROM achats";
            Statement st=conn.createStatement();
            ResultSet rs=st.executeQuery(requete);
            while(rs.next()){
                
                Achat Ach = new Achat();
                Ach.setId_achat(rs.getInt(1));
                Ach.setId_panier(rs.getInt(2));
                Ach.setId_produit(rs.getInt(3));
                
                AchatList.add(Ach);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage()); 
        }
        return AchatList;
    }
    
    public List<Integer> getAllProductsId(){
        
        ProduitService cr = new ProduitService();
        
        return afficherAchat().stream().map(Achat::getId_produit).collect(Collectors.toList());
    
//    return afficherProduit().stream().filter(p->p.getNom_produit().equals(name)).findFirst().orElse(null);
}
    public Achat findById(int id){
    //return afficherAchat().stream().filter(p->p.getId_produit()==id).findFirst().orElse(null);
    return afficherAchat().stream().filter(p->p.getId_produit()==id).findFirst().orElse(null);
}
    
    }
    
