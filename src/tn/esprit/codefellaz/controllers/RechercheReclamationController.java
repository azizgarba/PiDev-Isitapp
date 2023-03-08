/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.esprit.codefellaz.entities.Reclamation;
import tn.esprit.codefellaz.services.ReclamationCRUD;


/**
 * FXML Controller class
 *
 * @author MERIEM
 */
public class RechercheReclamationController implements Initializable {

    @FXML
    private ComboBox<String> comborech;
    @FXML
    private TableView<Reclamation> tablerech;
    @FXML
    private TableColumn<Reclamation, Integer> recrech;
    @FXML
    private TableColumn<Reclamation, Integer> userrech;
    @FXML
    private TableColumn<Reclamation, String> catrech;
    @FXML
    private TableColumn<Reclamation, String> objrech;
    @FXML
    private TableColumn<Reclamation, String> descrech;
    @FXML
    private TableColumn<Reclamation, Integer> etrech;
    private String etatSelectionne ;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       /* RechercherReclamation(etatSelectionne);
        afficherReclamations() ;*/
        
        comborech.getItems().addAll("Freelancer","Produit","Service");
        comborech.setOnAction(event->{ 
        
                 etatSelectionne = comborech.getSelectionModel().getSelectedItem();
                System.out.println(etatSelectionne);
   
    
   
    // Appeler la méthode rechercherReclamation avec l'état sélectionné
   /// CRUDReclamation crudReclamation = new CRUDReclamation();
   // ObservableList<Reclamation> reclamations = reclamations.RechercherReclamation(etatSelectionne);
    ReclamationCRUD crudReclamation = new ReclamationCRUD();
    ObservableList<Reclamation> listeReclamations = crudReclamation.RechercherReclamation(etatSelectionne);
    ObservableList<Reclamation> reclamations = FXCollections.observableArrayList(listeReclamations);
   /*  recrech.setCellValueFactory(new PropertyValueFactory<>("id_Reclamation"));
        userrech.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        catrech.setCellValueFactory(new PropertyValueFactory<>("categorie_Reclamation"));
        objrech.setCellValueFactory(new PropertyValueFactory<>("objet_Reclamation"));
        descrech.setCellValueFactory(new PropertyValueFactory<>("description_Reclamation"));
        etrech.setCellValueFactory(new PropertyValueFactory<>("etat_Reclamation"));
      */  
        recrech.setCellValueFactory(new PropertyValueFactory<>("id_Reclamation"));
        userrech.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        catrech.setCellValueFactory(new PropertyValueFactory<>("categorie_Reclamation"));
        objrech.setCellValueFactory(new PropertyValueFactory<>("objet_Reclamation"));
        etrech.setCellValueFactory(new PropertyValueFactory<>("etat_Reclamation"));
        descrech.setCellValueFactory(new PropertyValueFactory<>("description_Reclamation"));
      //  tablerech.setItems(reclamations);
        
        System.out.println(reclamations);
          tablerech.setItems(FXCollections.observableArrayList(reclamations));
   // tablerech.setItems(FXCollections.observableArrayList(reclamations));
} );
    }
    

// Ajouter le ComboBox à votre interface utilisateur
// Par exemple, si vous utilisez JavaFX :
/*AnchorPane anchorPane = new AnchorPane();
anchorPane.getChildren().addAll(comboBox, tableView);
AnchorPane.setTopAnchor(comboBox, 10.0);
AnchorPane.setLeftAnchor(comboBox, 10.0);
AnchorPane.setTopAnchor(tableView, 50.0);
AnchorPane.setLeftAnchor(tableView, 10.0);*/
    
    
    /* public void afficherReclamations() {
       CRUDReclamation R = new CRUDReclamation();
        ObservableList<Reclamation> ReclamationList = (ObservableList<Reclamation>) R.afficherReclamations();

       
       
        recrech.setCellValueFactory(new PropertyValueFactory<>("id_Reclamation"));
        userrech.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        catrech.setCellValueFactory(new PropertyValueFactory<>("categorie_Reclamation"));
        objrech.setCellValueFactory(new PropertyValueFactory<>("objet_Reclamation"));
        descrech.setCellValueFactory(new PropertyValueFactory<>("description_Reclamation"));
        etrech.setCellValueFactory(new PropertyValueFactory<>("etat_Reclamation"));
        tablerech.setItems(ReclamationList);
    }*/

    
    
}