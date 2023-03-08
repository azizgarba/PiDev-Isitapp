/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import tn.esprit.codefellaz.utils.MyConnection;
import tn.esprit.codefellaz.entities.Projets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author myriam-PC
 */
public class ProjetsCRUD implements InterfaceProjet {

    Statement ste;
    Connection conn = MyConnection.getInstance().getCnx();

    @Override
    public void AjouterProjet(Projets projet) {
        try {

            String requete1 = "SELECT * FROM `Projets` WHERE `nom_projet`=?";
            PreparedStatement pst1 = conn.prepareStatement(requete1);
            pst1.setString(1, projet.getNom_projet());
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Nom de projet Existant, veuillez saisir un autre nom!");
                alert.show();
                //System.err.println("Utilisateur existe déjà ou changer le nom d'utilisateur!");

            } else {
                ste = conn.createStatement();
            }
            String req = "INSERT INTO `Projets`(`id_projet`, `nom_projet`, `priorite_projet`, `dateP_projet`, `dateL_projet`, `description_projet`, `progression_P`, `id_freelancer`) VALUES ('" + projet.getId_projet() + "','" + projet.getNom_projet() + "','" + projet.getPriorite_projet() + "','" + projet.getDateP_projet() + "','" + projet.getDateL_projet() + "','" + projet.getDescription_projet() + "','" + projet.getProgression_P() + "','" + projet.getId_freelancer() + "')";
            ste.executeUpdate(req);
            System.out.println("Projet ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    @Override
    public void ModifierProjet(Projets projet) {
        try {
            String requete = "UPDATE `Projets` SET `priorite_projet`=?,`dateL_projet`=?,`description_projet`=? WHERE nom_projet=?";
            PreparedStatement ste = conn.prepareStatement(requete);

            
            ste.setInt(1, projet.getPriorite_projet());
            ste.setString(2, projet.getDateL_projet());
            ste.setString(3, projet.getDescription_projet());
            ste.setString(4, projet.getNom_projet());

            ste.executeUpdate();
            System.out.println("projet modifié avec succès");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void SupprimerProjet(int id_projet) {
        try {
            String req = "DELETE FROM `Projets` WHERE id_projet = '" + id_projet + "'";
            Statement ste = conn.createStatement();
            ste.executeUpdate(req);
            System.out.println("projet supprimé!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    //methode supprime avec nom
     @Override
    public void SupprimerProjet2(String nom_projet) {
        try {
            String req = "DELETE FROM `Projets` WHERE nom_projet = '" + nom_projet + "'";
            Statement ste = conn.createStatement();
            ste.executeUpdate(req);
            System.out.println("projet supprimé!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public ObservableList<Projets> afficherProjets2() {
        ObservableList<Projets> projetlist = FXCollections.observableArrayList();
        try {
            String requete = "SELECT id_projet,Priorite_projet, dateP_projet, dateL_projet, description_projet FROM `Projets`";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Projets projet = new Projets();

                projet.setId_projet(rs.getInt("id_projet"));
                projet.setPriorite_projet(rs.getInt("priorite_projet"));
                projet.setDateP_projet(rs.getString("dateP_projet"));
                projet.setDateL_projet(rs.getString("dateL_projet"));
                projet.setDescription_projet(rs.getString("description_projet"));
                projetlist.add(projet);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return projetlist;
    }

    @Override

    public List<Projets> AfficherProjets() {
        List<Projets> proj = new ArrayList<>();
        try {
            ste = conn.createStatement();
            String req = "SELECT * FROM `Projets`";
            ResultSet result = ste.executeQuery(req);
            while (result.next()) {

                Projets proo = new Projets();

                proo.setId_projet(result.getInt(1));
                proo.setNom_projet(result.getString(2));
                proo.setPriorite_projet(result.getInt(3));
                proo.setDateP_projet(result.getString(4));
                proo.setDateL_projet(result.getString(5));
                proo.setDescription_projet(result.getString(6));
                proo.setProgression_P(result.getFloat(7));
                proo.setId_freelancer(result.getInt(8));
                proj.add(proo);

            }

        } catch (SQLException ex) {

            System.err.println(ex.getMessage());
        }
        return proj;
    }

}
