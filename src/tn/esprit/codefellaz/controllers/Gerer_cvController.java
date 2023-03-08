/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import tn.esprit.codefellaz.entities.offre_de_travail;
import tn.esprit.codefellaz.services.CV_CRUD;
import tn.esprit.codefellaz.services.offre_de_travail_CRUD;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage; 
/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class Gerer_cvController implements Initializable {
private Label label;
    @FXML
    private TableView<cv> Tableau;
    @FXML
    private Button Bt_Ajouter;
    @FXML
    private Button Bt_Modifier;
    @FXML
    private TableColumn<?, ?> diplome;
    @FXML
    private TableColumn<?, ?> annee_diplome;
    @FXML
    private TableColumn<?, ?> institut;
    @FXML
    private TableColumn<?, ?> specialite;
    @FXML
    private TableColumn<?, ?> bio_cv;
public static cv connected_cv;
    @FXML
    private Button su;
    @FXML
    private TableColumn<?, ?> id_cv;
    @FXML
    private Button ent;
    @FXML
    private Button off;
     public ObservableList<cv> list;
    @FXML
    private TextField inputRech;
    @FXML
    private Button affInstitut;
    @FXML
    private Button pdf2;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CV_CRUD pss = new CV_CRUD();
       
        
        ArrayList<cv> c = new ArrayList<>();
        try {
            c = (ArrayList<cv>) pss.afficher_CV();
        } catch (SQLException ex) {
            System.out.println("erreur");
        }
        

        ObservableList<cv> obs2 = FXCollections.observableArrayList(c);
        Tableau.setItems(obs2);
              id_cv.setCellValueFactory(new PropertyValueFactory<>("id_cv"));

      bio_cv.setCellValueFactory(new PropertyValueFactory<>("bio_cv"));
        diplome.setCellValueFactory(new PropertyValueFactory<>("diplome"));
        annee_diplome.setCellValueFactory(new PropertyValueFactory<>("annee_diplome"));
        
       institut.setCellValueFactory(new PropertyValueFactory<>("institut"));
           specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));

           
          try {
            list = FXCollections.observableArrayList(
                    pss.afficher_CV()
            );        
        
        
   FilteredList<cv> filteredData = new FilteredList<>(list, e -> true);
            inputRech.setOnKeyReleased(e -> {
                inputRech.textProperty().addListener((ObservableValue, oldValue, newValue) -> {
                    filteredData.setPredicate((Predicate<? super cv>) cvs -> {
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
                        String lower = newValue.toLowerCase();
                        if ((cvs.getSpecialite()).toLowerCase().contains(lower)) {
                            return true;
                        }
                        return false;
                    });
                });
                SortedList<cv> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(Tableau.comparatorProperty());
                Tableau.setItems(sortedData);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }  
           
           

    }    

    @FXML
    private void AjouterEmployer(ActionEvent event) throws IOException {
               Parent page1 = FXMLLoader.load(getClass().getResource("ajouter_cv.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
        
    }

    @FXML
    private void ModifierEmployer(ActionEvent event) throws IOException {
        
              CV_CRUD ps = new CV_CRUD();
        cv c = new cv(Tableau.getSelectionModel().getSelectedItem().getId_cv(),
                Tableau.getSelectionModel().getSelectedItem().getBio_cv(),
                 Tableau.getSelectionModel().getSelectedItem().getDiplome(),
                Tableau.getSelectionModel().getSelectedItem().getAnnee_diplome(),
               Tableau.getSelectionModel().getSelectedItem().getInstitut(),
                Tableau.getSelectionModel().getSelectedItem().getSpecialite(),
                Tableau.getSelectionModel().getSelectedItem().getId_freelance(),
                Tableau.getSelectionModel().getSelectedItem().getImage()

        );
               
               
        Gerer_cvController.connected_cv = c;
        
             Parent page1 = FXMLLoader.load(getClass().getResource("modifier_cv.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void resetTableData() throws SQLDataException, SQLException {
        CV_CRUD cs = new CV_CRUD();
        List<cv> listrec = new ArrayList<>();
        listrec = cs.afficher_CV();
        ObservableList<cv> data = FXCollections.observableArrayList(listrec);
        Tableau.setItems(data);
    }

    @FXML
    private void su(ActionEvent event) throws SQLException {
       if (event.getSource() == su) {
            cv rec = new cv();

            rec.setId_cv(Tableau.getSelectionModel().getSelectedItem().getId_cv());
            CV_CRUD cs = new CV_CRUD();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
      alert.setTitle("Delete ");
      alert.setHeaderText("Are you sure want to delete this cv");
      alert.setContentText(" ");

      // option != null.
      Optional<ButtonType> option = alert.showAndWait();

      if (option.get() == null) {
       
      } else if (option.get() == ButtonType.OK) {
           cs.Supprimercv(rec);
    
       
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


    @FXML
    private void ent(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("gerer_entreprise.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
    

    @FXML
    private void off(ActionEvent event) throws IOException {
        Parent page1 = FXMLLoader.load(getClass().getResource("gerer_offre_de_travail.fxml"));
        Scene scene = new Scene(page1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void affInstitut(ActionEvent event) throws SQLException {
            CV_CRUD cs = new CV_CRUD();
        List<cv> listrec = new ArrayList<>();
        listrec = cs.afficher_CVByInstitut();
        ObservableList<cv> data = FXCollections.observableArrayList(listrec);
        Tableau.setItems(data);
    }
    
     private void printPDF() throws FileNotFoundException, DocumentException, IOException {
        
        Document d = new Document();
        PdfWriter.getInstance(d, new FileOutputStream("..\\ListCV.pdf"));
        d.open();
       // d.add(new Paragraph("Les spécialités des cvs"));
        
        PdfPTable pTable = new PdfPTable(1);
       

     
        
        Tableau.getItems().forEach((t) -> {
       pTable.addCell(String.valueOf(t.getSpecialite()));
            
       
        });
        
                        d.add(pTable);

        d.close();
        Desktop.getDesktop().open(new File("..\\ListCV.pdf"));

    } 
    
    
    
    
    
    @FXML
    private void pdf2(ActionEvent event) throws FileNotFoundException, DocumentException, IOException {
         if (event.getSource() == pdf2) {
            
             printPDF();
            
            TrayNotification tray = new TrayNotification();
            AnimationType type = AnimationType.SLIDE;
            tray.setAnimationType(type);
            tray.setTitle("PDF");
            tray.setMessage("PDF");
            tray.setNotificationType(NotificationType.INFORMATION);//
            tray.showAndDismiss(Duration.millis(3000));
        }
        
        
        
    }
    
}
