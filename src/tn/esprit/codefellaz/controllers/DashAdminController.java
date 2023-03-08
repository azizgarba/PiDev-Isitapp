/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static tn.esprit.codefellaz.controllers.SignUpController.isNumeric;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.UserService;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tn.esprit.codefellaz.utils.MyConnection;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;


/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class DashAdminController implements Initializable {

    @FXML
    private TextField dashuser;
    @FXML
    private TextField dashemail;
    @FXML
    private TextField dashtel;
    @FXML
    private DatePicker dashnais;
    @FXML
    private ComboBox<String> dashgenr;
    @FXML
    private ComboBox<String> dashrol;
    @FXML
    private FontAwesomeIconView dashaj;
    @FXML
    private FontAwesomeIconView dashmod;
    @FXML
    private FontAwesomeIconView dashbloq;
    @FXML
    private FontAwesomeIconView dashdebloq;
    @FXML
    private TableView<User> listusers;
    @FXML
    private TextField fieldrech;
    @FXML
    private FontAwesomeIconView btnrech;
    ObservableList<String> genrelist = FXCollections.observableArrayList("Homme", "femme");
    ObservableList<String> rolelist = FXCollections.observableArrayList("Client", "Freelancer", "représentant d'entreprise");
   
    @FXML
    private TableColumn<User, String> coluser;
    @FXML
    private TableColumn<User, String> colemai;
    @FXML
    private TableColumn<User, String> coltel;
    @FXML
    private TableColumn<User, String> colnaiss;
    @FXML
    private TableColumn<User, String> colgenr;
    @FXML
    private TableColumn<User, String> colrol;
    @FXML
    private TableColumn<User, Integer> colstat;
    @FXML
    private TableColumn<User, Integer> coleta;
    @FXML
    private AnchorPane dash;
    Connection cnx1 = MyConnection.getInstance().getCnx();
    @FXML
    private FontAwesomeIconView refresh;
    @FXML
    private FontAwesomeIconView exportpdf;
    @FXML
    private FontAwesomeIconView ajoutcatpro;
    @FXML
    private FontAwesomeIconView reclamation;
    @FXML
    private FontAwesomeIconView ajoutcatser;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dashgenr.setItems(genrelist);
        dashrol.setItems(rolelist);

        afficherUtilisateurs();
        selectUser();
      

    }

    public void afficherUtilisateurs() {
        UserService userService = new UserService();
        ObservableList<User> usersList = userService.afficherUsers();

        coluser.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colemai.setCellValueFactory(new PropertyValueFactory<>("email"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colnaiss.setCellValueFactory(new PropertyValueFactory<>("birth"));
        colgenr.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colrol.setCellValueFactory(new PropertyValueFactory<>("role"));
        colstat.setCellValueFactory(new PropertyValueFactory<>("status"));
        coleta.setCellValueFactory(new PropertyValueFactory<>("etat"));

        listusers.setItems(usersList);
    }

    public void selectUser() {
        listusers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                dashuser.setText(newValue.getUserName());
                dashemail.setText(newValue.getEmail());
                dashtel.setText(newValue.getTel());
                dashnais.setValue(LocalDate.parse(newValue.getBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                dashgenr.setValue(newValue.getGender());
                dashrol.setValue(newValue.getRole());
            }
        });

    }

    @FXML
    private void close(MouseEvent event) {
        Stage stage1 = (Stage) dash.getScene().getWindow();
        stage1.close();
    }
   
    @FXML
    public ObservableList<User> recherche() {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        try {
            String query = "SELECT nom_utilisateur, email, tel_utilisateur, date_naissance, genre, role, status, etat FROM utilisateurs WHERE nom_utilisateur=? OR email=? OR tel_utilisateur=? OR role=?";
            PreparedStatement stmt = cnx1.prepareStatement(query);
            stmt.setString(1, fieldrech.getText());
            stmt.setString(2, fieldrech.getText());
            stmt.setString(3, fieldrech.getText());
            stmt.setString(4, fieldrech.getText());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                User user = new User();

                user.setUserName(rs.getString("nom_utilisateur"));
                user.setEmail(rs.getString("email"));
                user.setTel(rs.getString("tel_utilisateur"));
                user.setBirth(rs.getString("date_naissance"));
                user.setGender(rs.getString("genre"));          
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getInt("status"));
                user.setEtat(rs.getInt("etat"));
                usersList.add(user);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return usersList;
    }
    public void afficherUtilisateursrecherche() {

        ObservableList<User> usersList = recherche();

        coluser.setCellValueFactory(new PropertyValueFactory<>("userName"));
        colemai.setCellValueFactory(new PropertyValueFactory<>("email"));
        coltel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colnaiss.setCellValueFactory(new PropertyValueFactory<>("birth"));
        colgenr.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colrol.setCellValueFactory(new PropertyValueFactory<>("role"));
        colstat.setCellValueFactory(new PropertyValueFactory<>("status"));
        coleta.setCellValueFactory(new PropertyValueFactory<>("etat"));

        listusers.setItems(usersList);
    }

    @FXML
    private void refresh(MouseEvent event) {
        afficherUtilisateurs();
    }
    @FXML
    private void recherche(MouseEvent event) {
        afficherUtilisateursrecherche();
    }

    @FXML
    private void ajout(MouseEvent event) {
        if (dashuser.getText().isEmpty() || dashemail.getText().isEmpty() || dashtel.getText().isEmpty() || dashnais.getValue().toString().isEmpty() || dashgenr.getValue().isEmpty() || dashrol.getValue().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez remplir tous les champs!");

                alert.show();
            }

            else if (isNumeric(dashtel.getText()) == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez saisir seulement des chifres pour votre numéro de téléphone!");

                alert.show();
            }

            else if (ValidationEmail()==false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez respecter le format d'un courriel(exemple@domaine.com)!");

                alert.show();
            }
            else{
            User user = new User(dashuser.getText(), dashemail.getText(), dashtel.getText(), dashnais.getValue().toString(), dashgenr.getValue(), dashrol.getValue());
            UserService userserv = new UserService();
            userserv.ajouterdash(user);
            afficherUtilisateurs();
            }
    }

    @FXML
    private void modifier(MouseEvent event) {
        User user = new User(dashuser.getText(), dashemail.getText(), dashtel.getText(), dashnais.getValue().toString(), dashgenr.getValue(), dashrol.getValue());
        UserService userserv = new UserService();
        userserv.modifierUser(user);
        afficherUtilisateurs();
    }
    @FXML
    private void bloc(MouseEvent event) {
        User user = new User(dashuser.getText(), dashemail.getText(), dashtel.getText(), dashnais.getValue().toString(), dashgenr.getValue(), dashrol.getValue());
        UserService userserv = new UserService();
        userserv.bloc(user);
        afficherUtilisateurs();
    }

    @FXML
    private void debloq(MouseEvent event) {
        User user = new User(dashuser.getText(), dashemail.getText(), dashtel.getText(), dashnais.getValue().toString(), dashgenr.getValue(), dashrol.getValue());
        UserService userserv = new UserService();
        userserv.unbloc(user);
        afficherUtilisateurs();
    }
    
    public boolean ValidationEmail(){ 
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(dashemail.getText());
        
        if(match.find() && match.group().equals(dashemail.getText()))
        {
            return true;
        }else {
            return false;
        }
    }
    
    public void exportToPdf(){
     try {
         FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(null);
        if (selectedFile != null) {
        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
        document.open();
        PdfPTable table = new PdfPTable(8);
        PdfPCell c1 = new PdfPCell(new Phrase("Nom utilisateur"));
        table.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("Email"));
        table.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Phrase("Téléphone"));
        table.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Phrase("Date de naissance"));
        table.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase("Genre"));
        table.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Phrase("Role"));
        table.addCell(c6);
        PdfPCell c7 = new PdfPCell(new Phrase("Statut"));
        table.addCell(c7);
        PdfPCell c8 = new PdfPCell(new Phrase("Etat"));
        table.addCell(c8);
        table.setHeaderRows(1);
        for (User user : listusers.getItems()) {
            table.addCell(user.getUserName());
            table.addCell(user.getEmail());
            table.addCell(user.getTel());
            table.addCell(user.getBirth());
            table.addCell(user.getGender());
            table.addCell(user.getRole());
            table.addCell(Integer.toString(user.getStatus()));
            table.addCell(Integer.toString(user.getEtat()));
        }
        document.add(table);
        document.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Export PDF");
        alert.setHeaderText(null);
        alert.setContentText("Le fichier a été exporté avec succès !");
        alert.showAndWait();}
    } catch (FileNotFoundException | DocumentException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Export PDF");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de l'exportation du fichier PDF !");
        alert.showAndWait();
    }}
    
        @FXML
    private void handleExportPdf(MouseEvent event) {
       exportToPdf();
    }

    @FXML
    private void ajoutercategorieproduit(MouseEvent event) {
        
         try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouterCategorieP.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Ajout Categorie Produit");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
    }

    @FXML
    private void reclamationbtn(MouseEvent event) {
         try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ConsulterReclamationAdmin.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Consulter Reclamation");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
    }

    @FXML
    private void ajoutercatser(MouseEvent event) {
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ajouterCategorie.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Ajout Categorie Service");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
    }

    



}
