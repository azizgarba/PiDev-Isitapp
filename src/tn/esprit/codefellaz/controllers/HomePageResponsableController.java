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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class HomePageResponsableController implements Initializable {

    @FXML
    private Button offre_trav;
    @FXML
    private Button entreprise;
    @FXML
    private Button events;
    @FXML
    private ImageView profileImage;
    @FXML
    private Label usernameLabel;
    private Parent fxml ;
    @FXML
    private Button profil;
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView image_front;
    @FXML
    private Button consulter_cv;
    @FXML
    private Label usernameLabel1;
    @FXML
    private Label usernameLabel11;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usernameLabel.setText("a");
        profil.setOnAction(RETOUR -> {
             try {
                       
                      fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/Profil.fxml"));
                       root.getChildren().removeAll() ;
                       root.getChildren().setAll(fxml) ;

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

        });
        
        offre_trav.setOnAction(RETOUR -> {
             try {
                       
                      fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouter_offre_de_travail.fxml"));
                       root.getChildren().removeAll() ;
                       root.getChildren().setAll(fxml) ;

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

        });
        entreprise.setOnAction(RETOUR -> {
            try {

                     fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouter_entreprise.fxml"));
                      root.getChildren().removeAll() ;
                      root.getChildren().setAll(fxml) ;

                   } catch (IOException ex) {
                       System.out.println(ex.getMessage());
                   }

       });
        
        events.setOnAction(RETOUR -> {
            try {

                     fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                      root.getChildren().removeAll() ;
                      root.getChildren().setAll(fxml) ;

                   } catch (IOException ex) {
                       System.out.println(ex.getMessage());
                   }

       });
        consulter_cv.setOnAction(RETOUR -> {
            try {

                     fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/gerer_cv.fxml"));
                      root.getChildren().removeAll() ;
                      root.getChildren().setAll(fxml) ;

                   } catch (IOException ex) {
                       System.out.println(ex.getMessage());
                   }

       });
    }    
    
}
