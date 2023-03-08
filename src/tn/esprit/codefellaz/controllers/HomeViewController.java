/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import tn.esprit.codefellaz.utils.CountryAPI;

import org.json.JSONArray;
import org.json.JSONObject;
import tn.esprit.codefellaz.entities.Produit;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.AchatService;
import tn.esprit.codefellaz.services.ProduitService;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author user
 */
public class HomeViewController implements Initializable {

    @FXML
    private Button ajouter_demande;
    @FXML
    private Button ajouter_offre;
    @FXML
    private Button afficher_offre;
    @FXML
    private Button afficher_demande;
    
    private Parent fxml ; 
    @FXML
    private AnchorPane listeBouttons;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//
//        try {
//            List<String> countryNames = CountryAPI.getCountryNames();
//            countryComboBox.getItems().addAll(countryNames);
//        } catch (IOException e) {
//            System.err.println("err");
//        }



 
           
            
             Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
             
         
           if ("Client".equals(userconnected.getRole())){
           ajouter_offre.setVisible(false);
           
           }
           else
           {
                ajouter_offre.setVisible(true);
           }
            
            
            
        

            } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }

                  
ajouter_offre.setOnAction(ajouter_demande -> {

   
            try {
                       
                       fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouterOffreService.fxml"));
                       listeBouttons.getChildren().removeAll() ;
                       listeBouttons.getChildren().setAll(fxml) ;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
        });

        afficher_offre.setOnAction(afficher_offre -> {

            try {
                       
                       fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ServicesView.fxml"));
                       listeBouttons.getChildren().removeAll() ;
                       listeBouttons.getChildren().setAll(fxml) ;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

        });
        
        
        
        

        ajouter_demande.setOnAction(ajouter_demande -> {

            try {
                       
                       fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouterDemandeService.fxml"));
                       listeBouttons.getChildren().removeAll() ;
                       listeBouttons.getChildren().setAll(fxml) ;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

        });

        afficher_demande.setOnAction(afficher_demande -> {

                try {
                       
                       fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/OffreServiceHome.fxml"));
                       listeBouttons.getChildren().removeAll() ;
                       listeBouttons.getChildren().setAll(fxml) ;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

        });

    }

}
