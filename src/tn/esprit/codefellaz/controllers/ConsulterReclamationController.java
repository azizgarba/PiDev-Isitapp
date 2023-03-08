/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import tn.esprit.codefellaz.entities.Reclamation;
import tn.esprit.codefellaz.entities.Reponse;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.ReclamationCRUD;
import tn.esprit.codefellaz.services.ReponseCRUD;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author Meriem
 */
public class ConsulterReclamationController implements Initializable {

    @FXML
    private TableColumn<Reclamation, String> categorie_traite;
    @FXML
    private TableColumn<Reclamation, String> objet_traite;
    @FXML
    private TableColumn<Reclamation, Integer> etat_traite;
    @FXML
    private TableColumn<Reclamation, String> reclamationClient;
    @FXML
    private TableView<Reclamation> tableRecClient;
   
    @FXML
    private TableView<Reponse> ViewReponse;
    @FXML
    private TableColumn<Reponse, Integer> idRec;
    @FXML
    private TableColumn<Reponse, String> rep;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficherReclamations();
        afficherReponses();
    }  
     public void afficherReclamations() {
       ReclamationCRUD reclamation = new ReclamationCRUD();
       
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
        
        ObservableList<Reclamation> ReclamationList = reclamation.afficherReclamationsClient(userconnected.getId());
         
       
         
        categorie_traite.setCellValueFactory(new PropertyValueFactory<>("categorie_Reclamation"));
        objet_traite.setCellValueFactory(new PropertyValueFactory<>("objet_Reclamation"));
        etat_traite.setCellValueFactory(new PropertyValueFactory<>("etat_Reclamation"));
        reclamationClient.setCellValueFactory(new PropertyValueFactory<>("description_Reclamation"));
        tableRecClient.setItems(ReclamationList);
        
        
    }   catch (IOException ex) {
            Logger.getLogger(ConsulterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     }
     
     public void afficherReponses() {
         Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
       ReponseCRUD reponse = new ReponseCRUD();
        ObservableList<Reponse> ReponseList =  reponse.afficherReponsesClient(userconnected.getId());
         
       
         
        idRec.setCellValueFactory(new PropertyValueFactory<>("id_Reclamation"));
        rep.setCellValueFactory(new PropertyValueFactory<>("description_Reponse"));
    
        ViewReponse.setItems(ReponseList);
            }   catch (IOException ex) {
            Logger.getLogger(ConsulterReclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
        
    
}
