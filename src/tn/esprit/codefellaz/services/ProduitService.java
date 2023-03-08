/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import tn.esprit.codefellaz.utils.MyConnection;
import tn.esprit.codefellaz.entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import tn.esprit.codefellaz.entities.CategorieProduit;

/**
 *
 * @author ASUS
 */
public class ProduitService implements InterfaceProduit {

    Connection conn = MyConnection.getInstance().getCnx();

    @Override
    public void ajouterProduit(Produit p) {
        try {
            String requete2 = "INSERT INTO `produits`(`nom_produit`, `prix_produit`, `id_categorie`, `id_freelancer`, `image_produit`) VALUES (?,?,?,?,?)";
            PreparedStatement pst2 = conn.prepareStatement(requete2);

            pst2.setString(1, p.getNom_produit());
            pst2.setDouble(2, p.getPrix_produit());
            pst2.setInt(3, p.getId_categorie());
            pst2.setInt(4, p.getId_freelancer());
            pst2.setString(5, p.getImage_produit());

            pst2.executeUpdate();

            System.out.println("Produit ajouté avec succès");//}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void ajouterProduit2(Produit p) {
        try {
            String requete2 = "INSERT INTO `produits`(`id_produit`, `nom_produit`, `prix_produit`, `id_categorie`, `id_freelancer`, `image_produit`) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst2 = conn.prepareStatement(requete2);

            pst2.setInt(1, p.getId_produit());
            pst2.setString(2, p.getNom_produit());
            pst2.setDouble(3, p.getPrix_produit());
            pst2.setInt(4, p.getId_categorie());
            pst2.setInt(5, p.getId_freelancer());
            pst2.setString(6, p.getImage_produit());

            pst2.executeUpdate();

            System.out.println("Produit ajouté avec succès");//}

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifierProduit(Produit p) {
        try {
            String requete = "UPDATE `produits` SET `nom_produit`=?,`prix_produit`=?,`id_categorie`=?,`id_freelancer`=?,`image_produit`=? WHERE `id_produit`=?";
            PreparedStatement pst = conn.prepareStatement(requete);

            pst.setString(1, p.getNom_produit());
            pst.setDouble(2, p.getPrix_produit());
            pst.setInt(3, p.getId_categorie());
            pst.setInt(4, p.getId_freelancer());
            pst.setString(5, p.getImage_produit());
            pst.setInt(6, p.getId_produit());

            pst.executeUpdate();
            System.out.println("Produit modifié avec succès");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerProduit(int id) {

        //try {
        //    String requete="DELETE FROM `produits` WHERE `id_produit`=?";
        //    PreparedStatement pst=conn.prepareStatement(requete);
        //    pst.setInt(1, id);
        //    pst.executeUpdate();
        //    System.out.println("produit supprimé avec succès");
        //} catch (SQLException ex) {
        //    System.err.println(ex.getMessage());
        //}
        try {
            String req = "DELETE FROM `produits` WHERE id_produit = " + id;
            Statement st = conn.createStatement();
            st.executeUpdate(req);
            System.out.println("Produit supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<Produit> afficherProduit() {
        List<Produit> ProduitsList = new ArrayList<>();
        try {
            String requete = "SELECT * FROM produits";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Produit product = new Produit();
                product.setId_produit(rs.getInt(1));
                product.setNom_produit(rs.getString(2));
                product.setPrix_produit((float) rs.getDouble(3));
                product.setId_categorie(rs.getInt(4));
                product.setId_freelancer(rs.getInt(5));
                product.setImage_produit(rs.getString(6));

                ProduitsList.add(product);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return ProduitsList;
    }

    public Produit findByName(String name) {

        return afficherProduit().stream().filter(p -> p.getNom_produit().equals(name)).findFirst().orElse(null);
    }

    public Produit findById(int id) {
        return afficherProduit().stream().filter(p -> p.getId_produit() == id).findFirst().orElse(null);
    }

    public Produit findByIdFreelancer(int id) {
        return afficherProduit().stream().filter(p -> p.getId_freelancer() == id).findFirst().orElse(null);
    }
    
    public String ReturnImg(Produit Prod) {
        return afficherProduit().stream().filter(p -> p.equals(Prod)).findFirst().orElse(Prod).getImage_produit();
    }

    public List<Integer> getAllProductsId() {

        return afficherProduit().stream().map(Produit::getId_produit).collect(Collectors.toList());
    }

    public List<Integer> getIdsByFreelancerId(int id_connectedfreelancer) {
        return afficherProduit().stream()
                .filter(p -> p.getId_freelancer() == id_connectedfreelancer)
                .map(Produit::getId_produit)
                .collect(Collectors.toList());
    }
    
   
    

    public List<Produit> getProductsByIds(List<Integer> ids) {
        List<Produit> allProducts = afficherProduit();
        return allProducts.stream()
                .filter(p -> ids.contains(p.getId_produit()))
                .collect(Collectors.toList());
    }

    public Map<String, Integer> countProductsByCategory() {

        Map<String, Integer> productsByCategory = new HashMap<>();
        List<Produit> produits = afficherProduit();
        CategorieProduitService CP = new CategorieProduitService();
        CategorieProduit Catp = new CategorieProduit();

        for (Produit produit : produits) {
            Catp = CP.findById(produit.getId_categorie());
            String category = Catp.getNom_categorie_produit();
            if (productsByCategory.containsKey(category)) {
                int count = productsByCategory.get(category);
                productsByCategory.put(category, count + 1);
            } else {
                productsByCategory.put(category, 1);
            }
        }

        return productsByCategory;
    }
    
    public List<Produit> getProductsSorted(List<Integer> ids) {
    List<Produit> allProducts = afficherProduit();
    List<Produit> filteredProducts = allProducts.stream()
            .filter(p -> ids.contains(p.getId_produit()))
            .sorted(Comparator.comparingDouble(Produit::getPrix_produit))
            .collect(Collectors.toList());
    return filteredProducts;
}
    
    public String imgProduitReturned(Produit P)
    {
        return P.getImage_produit() ;
    }
     public List<Produit> getSelectedProducts(List<Integer> ids) {
    List<Produit> allProducts = afficherProduit();
    List<Produit> filteredProducts = allProducts.stream()
            .filter(p -> ids.contains(p.getId_produit()))
            .collect(Collectors.toList());
    return filteredProducts;
}
     
     public List<Produit> getProductsByCategory(String category) {
         List<Produit> products = afficherProduit();
        List<Produit> filteredProducts = new ArrayList<>();
        
         CategorieProduitService CP = new CategorieProduitService();
        CategorieProduit Catp = new CategorieProduit();
        
        
            String category2 ;
            
        for (Produit product : products) {
            Catp = CP.findById(product.getId_categorie());
           category2 = Catp.getNom_categorie_produit();
            if (category2.equals(category)) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

}
