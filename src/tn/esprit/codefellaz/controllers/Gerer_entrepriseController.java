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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.entities.cv;
import tn.esprit.codefellaz.entities.entreprise;
import tn.esprit.codefellaz.services.UserService;
import tn.esprit.codefellaz.services.entreprise_CRUD;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class Gerer_entrepriseController implements Initializable {

    @FXML
    private TableView<entreprise> Tableau;
    @FXML
    private TableColumn<?, ?> ID_Emp;
    @FXML
    private Button Bt_Ajouter;
    @FXML
    private Button Bt_Modifier;
    public static entreprise connectedEntreprise;
    @FXML
    private TableColumn<?, ?> adress;
    @FXML
    private TableColumn<?, ?> tl;
    @FXML
    private TableColumn<?, ?> des;
    @FXML
    private TableColumn<?, ?> Prenom1;
private Label label;
    @FXML
    private Button su;
    @FXML
    private TextField inputRech;
    
     public ObservableList<entreprise> list;
    @FXML
    private Button affparadress;
    @FXML
    private AnchorPane root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
                
                
     Bt_Modifier.setVisible(true);
     su.setVisible(true);
            
        entreprise_CRUD pss = new entreprise_CRUD();
       
        
        ArrayList<entreprise> c = new ArrayList<>();
        try {
            c = (ArrayList<entreprise>) pss.afficher_entreprise1(userconnected.getId());
        } catch (SQLException ex) {
            System.out.println("erreur");
        }
        
          ArrayList<entreprise> c2 = new ArrayList<>();
        try {
            c2 = (ArrayList<entreprise>) pss.afficher_entreprise1(userconnected.getId());
        } catch (SQLException ex) {
            System.out.println("erreur");
        }
        
        ObservableList<entreprise> obs2 = FXCollections.observableArrayList(c);
        Tableau.setItems(obs2);
      ID_Emp.setCellValueFactory(new PropertyValueFactory<>("id_representant"));
        adress.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        tl.setCellValueFactory(new PropertyValueFactory<>("tel_entreprise"));
        
       des.setCellValueFactory(new PropertyValueFactory<>("description_entreprise"));
       
       try {
            list = FXCollections.observableArrayList(
                    pss.afficher_entreprise1(userconnected.getId())
            );      
           FilteredList<entreprise> filteredData = new FilteredList<>(list, e -> true);
            inputRech.setOnKeyReleased(e -> {
                inputRech.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super entreprise>) entreprises -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lower = newValue.toLowerCase();
                        if ((entreprises.getAdresse()).toLowerCase().contains(lower)) {
                            return true;
                        }
                        return false;
                    });
                });
                SortedList<entreprise> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(Tableau.comparatorProperty());
                Tableau.setItems(sortedData);
            });
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }  
       } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        
        
        
        
        
    }    

    @FXML
    private void AjouterEmployer(ActionEvent event) throws IOException {
        
          Parent page1 = FXMLLoader.load(getClass().getResource("ajouter_entreprise.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void ModifierEmployer(ActionEvent event) throws IOException {
               entreprise_CRUD ps = new entreprise_CRUD();
        entreprise c = new entreprise(Tableau.getSelectionModel().getSelectedItem().getId_entreprise(),
                Tableau.getSelectionModel().getSelectedItem().getId_representant(),
                 Tableau.getSelectionModel().getSelectedItem().getAdresse(),
                Tableau.getSelectionModel().getSelectedItem().getTel_entreprise(),
               Tableau.getSelectionModel().getSelectedItem().getDescription_entreprise(),
        Tableau.getSelectionModel().getSelectedItem().getImage());
               
               
        Gerer_entrepriseController.connectedEntreprise = c;
        
             Parent page1 = FXMLLoader.load(getClass().getResource("modifier_entreprise.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();  
    }


    public void resetTableData() throws SQLDataException, SQLException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
       
            
             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
        entreprise_CRUD cs = new entreprise_CRUD();
        List<entreprise> listrec = new ArrayList<>();
        listrec = cs.afficher_entreprise1(userconnected.getId());
        ObservableList<entreprise> data = FXCollections.observableArrayList(listrec);
        Tableau.setItems(data);
        } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void su(ActionEvent event) throws SQLException {
          if (event.getSource() == su) {
            entreprise rec = new entreprise();
                         System.out.println("/////////"+Tableau.getSelectionModel().getSelectedItem().getId_entreprise());

            rec.setId_entreprise(Tableau.getSelectionModel().getSelectedItem().getId_entreprise());
            entreprise_CRUD cs = new entreprise_CRUD();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Delete ");
      alert.setHeaderText("Are you sure want to delete this entreprise");
      alert.setContentText(" ");

      // option != null.
      Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
           cs.Supprimerentreprise(rec);
    
       
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

    private void offre(ActionEvent event) throws IOException {
            Parent page1 = FXMLLoader.load(getClass().getResource("gerer_offre_de_travail.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
        
    }

    private void cv(ActionEvent event) throws IOException {
        
        Parent page1 = FXMLLoader.load(getClass().getResource("gerer_cv.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void affparadress(ActionEvent event) throws SQLException {
        
               entreprise_CRUD cs = new entreprise_CRUD();
        List<entreprise> listrec = new ArrayList<>();
        listrec = cs.afficher_entrepriseByadresse();
        ObservableList<entreprise> data = FXCollections.observableArrayList(listrec);
        Tableau.setItems(data);
    }
    
}
