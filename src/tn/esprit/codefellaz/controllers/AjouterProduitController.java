/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.DefaultComboBoxModel;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.controlsfx.control.Notifications;
import tn.esprit.codefellaz.entities.CategorieProduit;
import tn.esprit.codefellaz.entities.Produit;
import tn.esprit.codefellaz.entities.User;
import tn.esprit.codefellaz.services.AchatService;
import tn.esprit.codefellaz.services.CategorieProduitService;
import tn.esprit.codefellaz.services.ProduitService;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AjouterProduitController implements Initializable {

    @FXML
    private ComboBox<String> combocategorie;
    @FXML
    private Label selection;
    @FXML
    private TextField nom_produit;
    @FXML
    private TextField prix_produit;
    @FXML
    private Button image_produit;
    @FXML
    private Button confirmer_produit;
    @FXML
    private ImageView image_Produit;

    String filePath;

    String filePath2;
    
    public String fileName;
    public Path source;
    public Path destination;
    public String imagePath;
    @FXML
    private ImageView image_Produit1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CategorieProduitService catg = new CategorieProduitService();
        List<String> cat = catg.ListeCategorie();
        ObservableList<String> list = FXCollections.observableArrayList(cat);

        combocategorie.setItems(list);
        combocategorie.setOnAction(event -> {

            String categorie_du_combo = combocategorie.getSelectionModel().getSelectedItem();
            selection.setText(categorie_du_combo);
        });

        image_produit.setOnAction(event -> {
            
            
           FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose an image file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.jfif")
            );
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                try {
                    Image image = new Image(selectedFile.toURI().toString());
                    image_Produit.setImage(image);
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
//            FileChooser fileChooser = new FileChooser();
//            File selectedFile = fileChooser.showOpenDialog(image_produit.getScene().getWindow());
//            if (selectedFile != null) {
//                Image image = new Image(selectedFile.toURI().toString());
//                image_Produit.setImage(image);
//                filePath = selectedFile.getAbsolutePath();
////                System.out.println(filePath);
////                filePath2 =filePath.replaceAll("\\\\", "/");
////               filePath = selectedFile.toString();
////String filePath = "C:\\Users\\myuser\\Documents\\myfile.txt";
//                String filePath2 = filePath.replaceAll("\\\\", "/");
//                System.out.println(filePath2);
//                filePath = filePath2;
//                filePath = filePath.substring(47);
//                System.out.println(filePath);
////                System.out.println(filePath);
////                System.out.println("Chemin du fichier : " + filePath2);
//            }
//        });

        confirmer_produit.setOnAction(e -> {

            ProduitService ajout = new ProduitService();
            CategorieProduitService CP = new CategorieProduitService();

            //int rep = CP.RetourID(combocategorie.getSelectionModel().getSelectedItem());
            CategorieProduit CategorySelected = CP.returnCatwithname(combocategorie.getSelectionModel().getSelectedItem());
               // System.out.println(rep + "TESTTT");
            // System.out.println(rep);
            if(nom_produit.getText().isEmpty() || prix_produit.getText().isEmpty() || combocategorie.getValue().isEmpty() || imagePath.isEmpty())
            {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs!");
                alert.show();
            }
            else {
                   Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/tn/esprit/codefellaz/views/SignIn.fxml"));
            Parent root2 = loader.load();
            SignInController controller = loader.getController();
            String labelValue = SignInController.UserNameFromController;
            

             UserService US = new UserService();
                User userconnected = US.userconnected(labelValue);
             
            
            
           Produit os = new Produit(nom_produit.getText(), Float.parseFloat(prix_produit.getText()), CategorySelected.getid_categorie_produit(), userconnected.getId(), imagePath);

            //PersonneDao pdao = PersonneDao.getInstance();
            ajout.ajouterProduit(os);

           /* Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Information Dialog");
            alert.setHeaderText("Indication :");
            alert.setContentText("Votre Produit a ete ajouteé");
            alert.show(); */
           Notifications.create()
                    .title("Produit ajouté")
                    .text("Un produit a ete ajouté !")
                    .showInformation();

            // fermer la fenetre apres l'ajout 
           // Stage stage = (Stage) confirmer_produit.getScene().getWindow();
           // stage.close();

            } catch (IOException ex) {
            Logger.getLogger(MesproduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            }
        }
        );
    }

}    
    
//}
