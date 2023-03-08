/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.codefellaz.entities.cv;
import tn.esprit.codefellaz.entities.entreprise;
import tn.esprit.codefellaz.entities.offre_de_travail;
import tn.esprit.codefellaz.services.entreprise_CRUD;
import tn.esprit.codefellaz.services.offre_de_travail_CRUD;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class Gerer_offre_de_travailController implements Initializable {
private Label label;
 public static offre_de_travail connectedoffre_de_travail;
    @FXML
    private TableView<offre_de_travail> Tableau;
    @FXML
    private TableColumn<?, ?> ID_Emp;
    @FXML
    private Button Bt_Ajouter;
    @FXML
    private Button Bt_Modifier;
    @FXML
    private TableColumn<?, ?> titre_offre_de_travail;
    @FXML
    private TableColumn<?, ?> type_offre_de_travail;
    @FXML
    private TableColumn<?, ?> salaire_offre_de_travail;
    @FXML
    private TableColumn<?, ?> specialite_offre_de_travail;
    @FXML
    private TableColumn<?, ?> entreprise_offre_de_travail;
    @FXML
    private TableColumn<?, ?> description_offre_de_travail;
    @FXML
    private Button su;
    @FXML
    private Button cc;
    @FXML
    private Button cv;
    @FXML
    private TextField inputRech;
     public ObservableList<offre_de_travail> list;
    @FXML
    private Button parSpecialité;
    @FXML
    private Button hh;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            offre_de_travail_CRUD pss = new offre_de_travail_CRUD();
       
        
        ArrayList<offre_de_travail> c = new ArrayList<>();
        try {
            c = (ArrayList<offre_de_travail>) pss.afficheroffre_de_travail();
        } catch (SQLException ex) {
            System.out.println("erreur");
        }
        

        ObservableList<offre_de_travail> obs2 = FXCollections.observableArrayList(c);
        Tableau.setItems(obs2);
              ID_Emp.setCellValueFactory(new PropertyValueFactory<>("id_offre_de_travail"));

      titre_offre_de_travail.setCellValueFactory(new PropertyValueFactory<>("titre_offre_de_travail"));
        type_offre_de_travail.setCellValueFactory(new PropertyValueFactory<>("type_offre_de_travail"));
        salaire_offre_de_travail.setCellValueFactory(new PropertyValueFactory<>("salaire_offre_de_travail"));
        
       specialite_offre_de_travail.setCellValueFactory(new PropertyValueFactory<>("specialite_offre_de_travail"));
           entreprise_offre_de_travail.setCellValueFactory(new PropertyValueFactory<>("entreprise_offre_de_travail"));
           description_offre_de_travail.setCellValueFactory(new PropertyValueFactory<>("description_offre_de_travail"));
  
           
               try {
            list = FXCollections.observableArrayList(
                    pss.afficheroffre_de_travail()
            );        
          
           FilteredList<offre_de_travail> filteredData = new FilteredList<>(list, e -> true);
            inputRech.setOnKeyReleased(e -> {
                inputRech.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super offre_de_travail>) offre_de_travails -> {
                     
                        
                        
                        
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lower = newValue.toLowerCase();
                        if ((offre_de_travails.getSpecialite_offre_de_travail()).toLowerCase().contains(lower)) {
                            return true;
                        }
                        return false;
                    });
                });
                SortedList<offre_de_travail> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(Tableau.comparatorProperty());
                Tableau.setItems(sortedData);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        }

           
     
        

    @FXML
    private void AjouterEmployer(ActionEvent event) throws IOException {
        
            Parent page1 = FXMLLoader.load(getClass().getResource("ajouter_offre_de_travail.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }

    @FXML
    private void ModifierEmployer(ActionEvent event) throws IOException {
        
              offre_de_travail_CRUD ps = new offre_de_travail_CRUD();
        offre_de_travail c = new offre_de_travail(Tableau.getSelectionModel().getSelectedItem().getId_offre_de_travail(),
                Tableau.getSelectionModel().getSelectedItem().getTitre_offre_de_travail(),
                 Tableau.getSelectionModel().getSelectedItem().getType_offre_de_travail(),
                Tableau.getSelectionModel().getSelectedItem().getSalaire_offre_de_travail(),
               Tableau.getSelectionModel().getSelectedItem().getSpecialite_offre_de_travail(),
        Tableau.getSelectionModel().getSelectedItem().getEntreprise_offre_de_travail(),
                Tableau.getSelectionModel().getSelectedItem().getDescription_offre_de_travail()
        );
               
               
        Gerer_offre_de_travailController.connectedoffre_de_travail = c;
        
             Parent page1 = FXMLLoader.load(getClass().getResource("Modifier_offre_de_travail.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void su(ActionEvent event) throws SQLException {
             if (event.getSource() == su) {
            offre_de_travail rec = new offre_de_travail();

            rec.setId_offre_de_travail(Tableau.getSelectionModel().getSelectedItem().getId_offre_de_travail());
            offre_de_travail_CRUD cs = new offre_de_travail_CRUD();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Delete ");
      alert.setHeaderText("Are you sure want to delete this offre");
      alert.setContentText(" ");

      // option != null.
      Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
           cs.Supprimeroffre_de_travail(rec);
    
       
      } else if (option.get() == ButtonType.CANCEL) {
      
      } else {
         this.label.setText("-");
      }
          
           
              TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.SLIDE;
            tray.setAnimationType(type);
            tray.setTitle("Supprimé avec succés");
            tray.setMessage("Supprimé avec succés");
            tray.setNotificationType(NotificationType.ERROR);
            tray.showAndDismiss(Duration.millis(3000));
            
            
            resetTableData();

        }
        
        
    }

        public void resetTableData() throws SQLDataException, SQLException {
        offre_de_travail_CRUD cs = new offre_de_travail_CRUD();
        List<offre_de_travail> listrec = new ArrayList<>();
        listrec = cs.afficheroffre_de_travail();
        ObservableList<offre_de_travail> data = FXCollections.observableArrayList(listrec);
        Tableau.setItems(data);
    }

    @FXML
    private void cc(ActionEvent event) throws IOException {
              Parent page1 = FXMLLoader.load(getClass().getResource("gerer_entreprise.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void cv(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("gerer_cv.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void parSpecialité(ActionEvent event) throws SQLException {
         offre_de_travail_CRUD cs = new offre_de_travail_CRUD();
        List<offre_de_travail> listrec = new ArrayList<>();
        listrec = cs.afficheroffre_de_travailBySpecialité();
        ObservableList<offre_de_travail> data = FXCollections.observableArrayList(listrec);
        Tableau.setItems(data);
        
    }

    @FXML
    private void hh(ActionEvent event) throws IOException, SQLException {

        
        
        
        
        Parent page1 = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/Statistique.fxml"));
        Scene scene = new Scene(page1);
        Stage stage =   new Stage();                
        stage.setScene(scene);
        stage.show();
        
        
    }
}
