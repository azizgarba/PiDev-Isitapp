/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class MainMenuController implements Initializable {

    @FXML
    private Button ajouterCat;
    @FXML
    private Button ajouterProd;
    @FXML
    private Button consultP;
    @FXML
    private Button consulterProd;
    @FXML
    private Button boutiquebutton;
    @FXML
    private Button staticBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ajouterCat.setOnAction(ajoutercategorie -> {

           try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouterCategorieP.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Vérification de compte");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
        });

        ajouterProd.setOnAction(ajouterProduit -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouterProduit.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Vérification de compte");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
        });

        consultP.setOnAction(consulterpanier -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/monpanier.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Vérification de compte");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
          
            
        });
        
        consulterProd.setOnAction(consulterproduit -> {
            
              try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/mesproduit.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Vérification de compte");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }

        });
        
        boutiquebutton.setOnAction(afficherBoutique -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/AchatProduits.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Vérification de compte");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
        });
        
        staticBtn.setOnAction(staticBtn -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/statistiquesProduit.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Vérification de compte");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
        });

    }

}
