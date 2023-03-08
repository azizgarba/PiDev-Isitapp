/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.codefellaz.entities.OffreService;
import tn.esprit.codefellaz.services.CrudCategoriService;
import tn.esprit.codefellaz.services.CrudOffreService;
import tn.esprit.codefellaz.utils.CountryAPI;
import tn.esprit.codefellaz.utils.MyConnection;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import tn.esprit.codefellaz.entities.CategorieProduit;
import tn.esprit.codefellaz.entities.CategorieService;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.CategorieProduitService;
import tn.esprit.codefellaz.services.UserService;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author user
 */
public class AjouterOffreServiceController implements Initializable {

    @FXML
    private ComboBox<String> countryComboBox;
    @FXML
    private AnchorPane ajout;

    @FXML
    private void handleDragOver(DragEvent event) {
    }

    @FXML
    private void handleDrop(DragEvent event) {
    }

    private Label selection;
    @FXML
    private ImageView imageOffre;
    @FXML
    private Button ajouter_image;
    @FXML
    private TextArea description_offre;
    @FXML
    private TextField prix;
    private TextField pays;
    @FXML
    private ComboBox<String> categorie;
    @FXML
    private Button publier;

    private String filePath;
    @FXML
    private Button retour;
    
    private Parent fxml ;

    Connection cnx;
    public String fileName;
    public Path source;
    public Path destination;
    public String imagePath;

    public AjouterOffreServiceController() {
        cnx = MyConnection.getInstance().getCnx();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

                       
                try {
          List<String> countryNames = CountryAPI.getCountryNames();
        Collections.sort(countryNames); // Sort the list alphabetically
        countryComboBox.getItems().addAll(countryNames);

        } catch (IOException e) {
            System.err.println("err");
        }
        
        retour.setOnAction(retour -> {

           try {
                       
                      fxml =  FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/HomeView.fxml"));
                       ajout.getChildren().removeAll() ;
                       ajout.getChildren().setAll(fxml) ;
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

        });

        CrudCategoriService catg = new CrudCategoriService();

        String sql = "SELECT nom_categorie_service FROM categories_services";
        try (PreparedStatement stmt = cnx.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ObservableList<String> categories = FXCollections.observableArrayList();
                while (rs.next()) {
                    categories.add(rs.getString("nom_categorie_service"));
                }

                // Initialisation de la ComboBox
                categorie.setItems(categories);
                categorie.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        String categorieCombo = categorie.getSelectionModel().getSelectedItem();
                        selection.setText(categorieCombo);
                    }

                });

                System.out.println(categorie.getSelectionModel().getSelectedItem());

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException ex) {
            Logger.getLogger(AjouterOffreServiceController.class.getName()).log(Level.SEVERE, null, ex);
        }

        ajouter_image.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.jfif")
            );
            File selectedFile = fileChooser.showOpenDialog(ajouter_image.getScene().getWindow());
            if (selectedFile != null) {
                try {
                    Image image = new Image(selectedFile.toURI().toString());
                    fileName = selectedFile.getName();
                    source = selectedFile.toPath();
                    destination = Paths.get("C:/xampp/htdocs/img/" + fileName);

                    System.out.println("image" + destination);
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

                    imagePath = "/img/" + fileName;
                    
        byte[] fileContent = Files.readAllBytes(destination);

        // Encode the byte array as Base64
        String base64Encoded = Base64.getEncoder().encodeToString(fileContent);

        // Upload the Base64 encoded data to the server
        HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("http://localhost/img/upload.php");

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("photo", base64Encoded));
        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            
            System.out.println("image uploaded");
        } else {
            
            System.out.println("image not uploded");
        }

                } catch (Exception e) {
                    System.out.println("Failed to load image file.");
                    e.printStackTrace();
                }
            
            }
        });

        publier.setOnAction(e -> {
            
              CrudCategoriService CP = new CrudCategoriService();

            //int rep = CP.RetourID(combocategorie.getSelectionModel().getSelectedItem());
            CategorieService CategorySelected = CP.returnCatwithname(categorie.getSelectionModel().getSelectedItem());

            //controle de saisie champs manquant
            if ("".equals(prix.getText()) || "".equals(description_offre.getText()) || "".equals(filePath)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Un champ manquant!");

                alert.show();

            } else {

                //controle de saisie chamos prix n'est pas une valeur numerique 
                float verifPrix = Float.parseFloat((prix.getText()));
                countryComboBox.setOnAction(event -> {
                    String selectedCountry = (String) countryComboBox.getValue();
                    
                    System.out.println(countryComboBox.getValue());
                    
                });
                
                try {
                    FXMLLoader load = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
                    Parent root2 = load.load();
                    SignInController controller = load.getController();
                    String labelValue = SignInController.UserNameFromController;
                    
                    
                    UserService US = new UserService();
                    User userconnected = US.userconnected(labelValue);
                    
                    
                    
                    CrudOffreService ajout = new CrudOffreService();

                    OffreService os = new OffreService(CategorySelected.getIdCategorieServices(), userconnected.getId(), Float.parseFloat(prix.getText()), description_offre.getText(), 0,countryComboBox.getValue(), null,-2, imagePath, 0);
                    
                    System.out.println(os);
                    
                    // unicite d'un service recherche d'un service avec la meme categorie proprieataire prix et description 
                    String sql1 = "SELECT * FROM offres_services WHERE categorie_service = ? AND freelancer_proprietaire = ? AND prix = ? AND description_offre_service = ?";
                    PreparedStatement stmt = cnx.prepareStatement(sql1);
                    stmt.setInt(1, os.getCategorie());
                    stmt.setInt(2, os.getProprietaireOffre());
                    stmt.setFloat(3, os.getPrix());
                    stmt.setString(4, os.getDescriptionOffreService());
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);

                        alert.setTitle("Information Dialog");

                        alert.setHeaderText(null);

                        alert.setContentText("Ce service existe deja!");

                        alert.show();
                        return;
                    }
                    // sinon on procede a l'ajout

                    ajout.ajouterOffreService(os);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Information Dialog");

                    alert.setHeaderText(null);

                    alert.setContentText("Votre categorie a ete ajouter");

                    alert.show();
                    
              
      
                    
                    // fermer la fenetre apres l'ajout 
//                Stage stage = (Stage) publier.getScene().getWindow();
//                stage.close();
//                
//                 retour a la fenetre principale
try {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/view/HomeView.fxml"));
    Parent root = loader.load();
    Scene scene = new Scene(root);
    Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
    
} catch (IOException ex) {
    System.out.println(ex.getMessage());
}

                } catch (NumberFormatException erreur) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Information Dialog");

                    alert.setHeaderText(null);

                    alert.setContentText("Le prix doit etre exprimé en chiffre !");

                    alert.show();
                    
                    

                } catch (SQLException ex) {
                    Logger.getLogger(AjouterOffreServiceController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(AjouterOffreServiceController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
             

            }
        }
        );

    }

}
