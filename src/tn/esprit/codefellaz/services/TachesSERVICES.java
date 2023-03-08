/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import tn.esprit.codefellaz.utils.MyConnection;
import tn.esprit.codefellaz.entities.Taches;
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
import javafx.scene.control.Alert;

/**
 *
 * @author myriam-PC
 */
public class TachesSERVICES implements InterfaceTache {

    Statement ste;
    Connection conn = MyConnection.getInstance().getCnx();

    @Override
    public void AjouterTache(Taches T) {

        try {
            String requete2 = "SELECT * FROM `Taches` WHERE `nom_tache`=?";
            PreparedStatement pst1 = conn.prepareStatement(requete2);
            pst1.setString(1, T.getNom_tache());
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Nom de tache Existant, veuillez saisir un autre nom!");
                alert.show();

            } else {

                ste = conn.createStatement();
            }
            String req = "INSERT INTO `Taches`(`id_tache`, `nom_tache`, `priorite_tache`, `dateP_tache`, `dateL_tache`, `description_tache`, `type_tache`,`id_projet`) VALUES ('" + T.getId_tache() + "','" + T.getNom_tache() + "','" + T.getPriorite_tache() + "','" + T.getDateP_tache() + "','" + T.getDateL_date() + "','" + T.getDescription_tache() + "','" + T.getType_tache() + "','" + T.getId_projet() + "')";

            ste.executeUpdate(req);
            System.out.println("Tache ajouté");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void ModifierTache(Taches T) {

        try {
            Statement ste = conn.createStatement();
            String req = "UPDATE `Taches` SET `nom_tache`='" + T.getNom_tache() + "',`priorite_tache`='" + T.getPriorite_tache() + "',`dateL_tache`='" + T.getDateL_date() + "',`description_tache`='" + T.getDescription_tache() + "',`type_tache`='" + T.getType_tache() + "',`id_projet`='" + T.getId_projet() + "' WHERE id_tache='" + T.getId_tache() + "'";
            ste.executeUpdate(req);
            System.out.println(ste);
            System.out.println("tache modifié!");

            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void SupprimerTache(int id_tache) {
        try {

            String req = "DELETE FROM `Taches` WHERE `id_tache`= '" + id_tache + "'";
            Statement ste = conn.createStatement();
            ste.executeUpdate(req);
            System.out.println("tache supprimé!");
            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Taches> AfficherTaches() {
        List<Taches> tach = new ArrayList<Taches>();
        try {
            Statement ste = conn.createStatement();
            String req = "SELECT * FROM `Taches`";
            ResultSet result = ste.executeQuery(req);

            while (result.next()) {

                Taches tacho = new Taches();

                tacho.setId_tache(result.getInt(1));
                tacho.setNom_tache(result.getString(2));
                tacho.setPriorite_tache(result.getInt(3));
                tacho.setDateP_tache(result.getString(4));
                tacho.setDateL_tache(result.getString(5));
                tacho.setDescription_tache(result.getString(6));
                tacho.setType_tache(7);
                tacho.setId_projet(result.getInt(8));

                tach.add(tacho);

                tach.add(tacho);
            }

            ste.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return tach;
    }
    
    
    
}
