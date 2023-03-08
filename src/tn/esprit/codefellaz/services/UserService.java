/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import tn.esprit.codefellaz.controllers.SignInController;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author LENOVO
 */
public class UserService implements InterfaceUser {

    Connection cnx1;

    public static String hashPassword(String password) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return generatedPassword;
    }

    public UserService() {
        cnx1 = MyConnection.getInstance().getCnx();
    }

    /*public void ajouterUser(){
        try {
            String requete="INSERT INTO `utilisateurs`(`nom_utilisateur`, `email`, `tel_utilisateur`, `date_naissance`, `genre`, `mot_de_passe`, `code_verification`, `status`, `role`, `img_profil`) VALUES ('atef','atef.riahi@esprit.tn','56182649','14/01/2000','homme','atef','5524','1','client','/desktop')";
            Statement st=cnx1.createStatement();
            st.executeUpdate(requete);
            System.out.println("Utilisateur ajouté avec succès");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        } */
    @Override
    public void ajouterUser(User user) {
        try {
            String requete1 = "SELECT * FROM utilisateurs WHERE `email`=? OR `nom_utilisateur`=?";
            PreparedStatement pst1 = cnx1.prepareStatement(requete1);
            pst1.setString(1, user.getEmail());
            pst1.setString(2, user.getUserName());
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Email existe déjà ou changer le nom d'utilisateur!");
                alert.show();
                System.err.println("Utilisateur existe déjà ou changer le nom d'utilisateur!");
            } else {
                String requete2 = "INSERT INTO `utilisateurs`(`nom_utilisateur`, `email`, `tel_utilisateur`, `date_naissance`, `genre`, `mot_de_passe`, `code_verification`,  `role`, `img_profil`) VALUES (?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst2 = cnx1.prepareStatement(requete2);
                pst2.setString(1, user.getUserName());
                pst2.setString(2, user.getEmail());
                pst2.setString(3, user.getTel());
                pst2.setString(4, user.getBirth());
                pst2.setString(5, user.getGender());
                pst2.setString(6, hashPassword(user.getPassword()));
                pst2.setString(7, user.getVerifyCode());
                pst2.setString(8, user.getRole());
                pst2.setString(9, user.getProfilImg());
                pst2.executeUpdate();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/VerifyAccount.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setTitle("Vérification de compte");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {

                }
                System.out.println("Utilisateur ajouté avec succès");
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimerUser(User user) {
        try {
            String requete = "DELETE FROM `utilisateurs` WHERE `nom_utilisateur`=?";
            PreparedStatement pst = cnx1.prepareStatement(requete);
            pst.setString(1, user.getUserName());
            pst.executeUpdate();
            System.out.println("Utilisateur supprimé avec succès");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifierUser(User user) {
        try {
            String requete = "UPDATE `utilisateurs` SET `nom_utilisateur`=?,`email`=?,`tel_utilisateur`=?,`date_naissance`=?,`genre`=?,`role`=? WHERE `email`=?";
            PreparedStatement pst = cnx1.prepareStatement(requete);
            pst.setString(1, user.getUserName());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getTel());
            pst.setString(4, user.getBirth());
            pst.setString(5, user.getGender());
            pst.setString(6, user.getRole());
            pst.setString(7, user.getEmail());
            pst.executeUpdate();
            System.out.println("Utilisateur modifié avec succès");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public ObservableList<User> afficherUsers() {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        try {
            String requete = "SELECT nom_utilisateur, email, tel_utilisateur, date_naissance, genre, role, status, etat FROM utilisateurs";
            Statement st = cnx1.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                User user = new User();

                user.setUserName(rs.getString("nom_utilisateur"));
                user.setEmail(rs.getString("email"));
                user.setTel(rs.getString("tel_utilisateur"));
                user.setBirth(rs.getString("date_naissance"));
                user.setGender(rs.getString("genre"));          
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getInt("status"));
                user.setEtat(rs.getInt("etat"));
                usersList.add(user);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return usersList;
    }
    
    public void ajouterdash(User user){
        try {
            String requete1 = "SELECT * FROM utilisateurs WHERE `email`=? OR `nom_utilisateur`=?";
            PreparedStatement pst1 = cnx1.prepareStatement(requete1);
            pst1.setString(1, user.getEmail());
            pst1.setString(2, user.getUserName());
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Email existe déjà ou changer le nom d'utilisateur!");
                alert.show();
                System.err.println("Utilisateur existe déjà ou changer le nom d'utilisateur!");
            } else {
                try {
                    String requete2 = "INSERT INTO `utilisateurs`(`nom_utilisateur`, `email`, `tel_utilisateur`, `date_naissance`, `genre`,  `role`,`status`,`etat`) VALUES (?,?,?,?,?,?,?,?)";
                    PreparedStatement pst2 = cnx1.prepareStatement(requete2);
                    pst2.setString(1, user.getUserName());
                    pst2.setString(2, user.getEmail());
                    pst2.setString(3, user.getTel());
                    pst2.setString(4, user.getBirth());
                    pst2.setString(5, user.getGender());

                    pst2.setString(6, user.getRole());
                    pst2.setInt(7, 1);
                    pst2.setInt(8, 1);
                    pst2.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }
    
    public void bloc(User user){
        try {
            String requete = "UPDATE `utilisateurs` SET `etat`=? WHERE `email`=?";
            PreparedStatement pst = cnx1.prepareStatement(requete);
            pst.setInt(1, 0);
            pst.setString(2, user.getEmail());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void unbloc(User user){
        try {
            String requete = "UPDATE `utilisateurs` SET `etat`=? WHERE `email`=?";
            PreparedStatement pst = cnx1.prepareStatement(requete);
            pst.setInt(1, 1);
            pst.setString(2, user.getEmail());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String userNameReturn(User user){
        try {
            cnx1 = MyConnection.getInstance().getCnx();
            String requete1 = "SELECT nom_utilisateur FROM utilisateurs WHERE `email`=?";
            PreparedStatement pst1 = cnx1.prepareStatement(requete1);
            pst1.setString(1, user.getEmail());
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                user.setUserName(rs.getString("nom_utilisateur"));
                return user.getUserName();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String img(User user){
        try {
            cnx1 = MyConnection.getInstance().getCnx();
            String requete1 = "SELECT img_profil FROM utilisateurs WHERE `email`=?";
            PreparedStatement pst1 = cnx1.prepareStatement(requete1);
            pst1.setString(1, user.getEmail());
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                user.setProfilImg(rs.getString("img_profil"));
                return user.getProfilImg();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void updatePass(String passactuel,String passupd){
        try {
            String requete = "UPDATE `utilisateurs` SET `mot_de_passe`=? WHERE `mot_de_passe`=?";
            PreparedStatement pst = cnx1.prepareStatement(requete);
            pst.setString(1,hashPassword(passupd));
            pst.setString(2, hashPassword(passactuel));
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updatePassmail(String code){
        try {
            String requete = "UPDATE `utilisateurs` SET `mot_de_passe`=? WHERE `code_verification`=?";
            PreparedStatement pst = cnx1.prepareStatement(requete);
            pst.setString(1,hashPassword(code));
            pst.setString(2, code);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateStatus(String code){
        try {
            String requete = "UPDATE `utilisateurs` SET `status`=? WHERE `code_verification`=?";
            PreparedStatement pst = cnx1.prepareStatement(requete);
            pst.setInt(1,1);
            pst.setString(2, code);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public User userconnected(String username){
        try {
            User user=new User(0,null);
            cnx1 = MyConnection.getInstance().getCnx();
            String requete1 = "SELECT * FROM utilisateurs WHERE `nom_utilisateur`=?";
            PreparedStatement pst1 = cnx1.prepareStatement(requete1);
            pst1.setString(1, username);
            ResultSet rs = pst1.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id_utilisateur"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public String getNomUser(int id) {

        String nomUtilisateur=null;
        Connection cnx = MyConnection.getInstance().getCnx();
        try {
            String requete2 = "SELECT nom_utilisateur FROM utilisateurs WHERE id_utilisateur = ?";
            PreparedStatement pst = cnx.prepareStatement(requete2);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                nomUtilisateur = rs.getString("nom_utilisateur");
                return nomUtilisateur;
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());

        }

        return null;
    }
    


}
