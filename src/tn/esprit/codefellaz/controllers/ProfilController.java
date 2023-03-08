/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class ProfilController implements Initializable {

    private Label usernameLabel;
    @FXML
    private Button deleteAccountButton;
    @FXML
    private AnchorPane profil;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button AfficherReclamationButton;
    @FXML
    private Button StatistiquesP;
    @FXML
    private Button projets;
    @FXML
    private Button MesServices;
   
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
             Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
             
         
           if ("Client".equals(userconnected.getRole())){
           projets.setVisible(false);
           MesServices.setVisible(false);
           
           }
           else if("Représentant d'entreprise".equals(userconnected.getRole())){
               projets.setVisible(false);
               StatistiquesP.setVisible(false);
               AfficherReclamationButton.setVisible(false);
           }
           else
           {
                projets.setVisible(true);
           }
           
            } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TODO
        
        changePasswordButton.setOnAction(event -> {
            try{
            Parent root3 = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/UpdatePass.fxml"));
            Scene scene = new Scene(root3);
            Stage stage = new Stage();
            stage.setTitle("Changement de mot de passe");
            stage.setScene(scene);
            stage.show();
            }
            catch (IOException ex) {

            }
        });
        
        StatistiquesP.setOnAction(event -> {
            try{
            Parent root4 = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/statistiquesProduit.fxml"));
            Scene scene = new Scene(root4);
            Stage stage = new Stage();
            stage.setTitle("Statistiques des produits");
            stage.setScene(scene);
            stage.show();
            }
            catch (IOException ex) {

            }
        });
        
        AfficherReclamationButton.setOnAction(event -> {
            try{
            Parent root5 = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ConsulterReclamationClient.fxml"));
            Scene scene = new Scene(root5);
            Stage stage = new Stage();
            stage.setTitle("Les reclamations");
            stage.setScene(scene);
            stage.show();
            }
            catch (IOException ex) {

            }
        });
        
        
        deleteAccountButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer le service");
            alert.setHeaderText(null);
            alert.setContentText("Êtes-vous sûr de vouloir supprimer votre profil ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get()==ButtonType.OK){
            User user=new User(usernameLabel.getText(),null);
            UserService userser=new UserService();
            userser.supprimerUser(user);
            Stage stage1 = (Stage) profil.getScene().getWindow();
            stage1.close();
            } });
        
        projets.setOnAction(event -> {
           try{
            Parent root6 = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/AfficherProjet.fxml"));
            Scene scene = new Scene(root6);
            Stage stage = new Stage();
            stage.setTitle("Mes projets");
            stage.setScene(scene);
            stage.show();
            }
            catch (IOException ex) {

            } });
        
         MesServices.setOnAction(event -> {
           try{
            Parent root7 = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/MesServices.fxml"));
            Scene scene = new Scene(root7);
            Stage stage = new Stage();
            stage.setTitle("mes services");
            stage.setScene(scene);
            stage.show();
            }
            catch (IOException ex) {
  Logger.getLogger(AjouterDemandeServiceController.class.getName()).log(Level.SEVERE, null, ex); 
            }
         
         
         });
        
                
    }    

    private void close(MouseEvent event) {
        Stage stage1 = (Stage) profil.getScene().getWindow();
        stage1.close();
    }

    
}
