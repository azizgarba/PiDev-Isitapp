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
 * @author MAG-PC
 */
public class Home_pageController implements Initializable {

    @FXML
    private Button but_responsable;
    @FXML
    private Button but_freelancer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        but_freelancer.setOnAction(freelancer ->{
             try {
                            Parent ajouter_event_parentt = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements_freelancer.fxml"));
                            Scene ajouter_event_scenee = new Scene(ajouter_event_parentt);
                            Stage ajout_stagee = (Stage) ((Node) freelancer.getSource()).getScene().getWindow();
                            ajout_stagee.hide();
                            ajout_stagee.setScene(ajouter_event_scenee);
                            ajout_stagee.show();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
        });
        but_responsable.setOnAction(responsable -> {
             try {
                            Parent ajouter_event_parent = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/liste_evenements.fxml"));
                            Scene ajouter_event_scene = new Scene(ajouter_event_parent);
                            Stage ajout_stage = (Stage) ((Node) responsable.getSource()).getScene().getWindow();
                            ajout_stage.hide();
                            ajout_stage.setScene(ajouter_event_scene);
                            ajout_stage.show();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
        });
    }    
    
}
