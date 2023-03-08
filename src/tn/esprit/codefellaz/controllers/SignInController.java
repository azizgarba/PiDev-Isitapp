/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.codefellaz.entities.User;
import static tn.esprit.codefellaz.services.UserService.hashPassword;
import tn.esprit.codefellaz.utils.MyConnection;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class SignInController implements Initializable {

    @FXML
    private TextField mailauth;
    @FXML
    private TextField passauth;
    @FXML
    private Button btnconnex;
    Connection cnx1;
    @FXML
    private VBox inter1;
    @FXML
    private Hyperlink veriflink;
    @FXML
    private Hyperlink forgotpass;
   
    public static String UserNameFromController = "aa";
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btnconnex.setOnAction(event -> {
            if ("".equals(mailauth.getText()) || "".equals(passauth.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Un champ manquant!");

                alert.show();

            }
            else if (!(mailauth.getText().contains("@")) && !(mailauth.getText().contains(".")) && !"admin".equals(mailauth.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Veuillez respecter le format d'un courriel(exemple@domaine.com)!");

                alert.show();
            } else {
                try {
                    cnx1 = MyConnection.getInstance().getCnx();
                    String requete1 = "SELECT * FROM utilisateurs WHERE `email`=? AND `mot_de_passe`=? AND `status`=? AND `etat`=?";
                    PreparedStatement pst1 = cnx1.prepareStatement(requete1);
                    pst1.setString(1, mailauth.getText());
                    pst1.setString(2, hashPassword(passauth.getText()));
                    pst1.setInt(3, 1);
                    pst1.setInt(4, 1);
                    ResultSet rs = pst1.executeQuery();
                    if (rs.next()) {
                        if ("admin".equals(rs.getString(10))) {
                            Stage stage1 = (Stage) inter1.getScene().getWindow();
                            stage1.close();
                            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/DashAdmin.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.initStyle(StageStyle.TRANSPARENT);
                            stage.setTitle("Tableau de bord administrateur");
                            stage.setScene(scene);
                            stage.show();
                        } else if("Client".equals(rs.getString(10))){
                            Stage stage1 = (Stage) inter1.getScene().getWindow();
                            stage1.close();
                            User user=new User(null,mailauth.getText());
                            User user1=new User(null,mailauth.getText(),null);                     
                            UserService userser=new UserService();
                            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/HomePageClient.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setTitle("Page d'accueil ");
                            stage.setScene(scene);
                            stage.show();
                            Label usernameLabel = (Label) scene.lookup("#usernameLabel");
                            usernameLabel.setText(userser.userNameReturn(user));
                            UserNameFromController = usernameLabel.getText();
                            ImageView profileImage=(ImageView)scene.lookup("#profileImage");
                            Image image = new Image(new URL("http://localhost"+userser.img(user1)).toString());
                            profileImage.setImage(image);
                        }
                        else if("Représentant d'entreprise".equals(rs.getString(10))){
                            Stage stage1 = (Stage) inter1.getScene().getWindow();
                            stage1.close();
                            User user=new User(null,mailauth.getText());
                            User user1=new User(null,mailauth.getText(),null);                     
                            UserService userser=new UserService();
                            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/HomePageResponsable.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setTitle("Page d'accueil ");
                            stage.setScene(scene);
                            stage.show();
                            Label usernameLabel = (Label) scene.lookup("#usernameLabel");
                            usernameLabel.setText(userser.userNameReturn(user));
                            UserNameFromController = usernameLabel.getText();
                            ImageView profileImage=(ImageView)scene.lookup("#profileImage");
                            Image image = new Image(new URL("http://localhost"+userser.img(user1)).toString());
                            profileImage.setImage(image);
                        }
                        else { // CAS FREELANCER
                        Stage stage1 = (Stage) inter1.getScene().getWindow();
                            stage1.close();
                            User user=new User(null,mailauth.getText());
                            User user1=new User(null,mailauth.getText(),null);                     
                            UserService userser=new UserService();
                            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/HomePageFreelancer.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setTitle("Page d'accueil ");
                            stage.setScene(scene);
                            stage.show();
                            Label usernameLabel = (Label) scene.lookup("#usernameLabel");
                            usernameLabel.setText(userser.userNameReturn(user));
                            UserNameFromController = usernameLabel.getText();
                            ImageView profileImage=(ImageView)scene.lookup("#profileImage");
                            Image image = new Image(new URL("http://localhost"+userser.img(user1)).toString());
                            profileImage.setImage(image);
//                            Stage stage1 = (Stage) inter1.getScene().getWindow();
//                            stage1.close();
//                            User user=new User(null,mailauth.getText());
//                            User user1=new User(null,mailauth.getText(),null);
//                            UserService userser=new UserService();
//                            
//                            Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/Profil.fxml"));
//                            
//                            Scene scene = new Scene(root);
//                            
//                            Stage stage = new Stage();
//                            stage.initStyle(StageStyle.TRANSPARENT);
//                            stage.setTitle("Profil");
//                            stage.setScene(scene);
//                            stage.show();
//                            Label usernameLabel = (Label) scene.lookup("#usernameLabel");
//                            usernameLabel.setText(userser.userNameReturn(user));
//                            ImageView profileImage=(ImageView)scene.lookup("#profileImage");
//                            Image image = new Image(new URL("http://localhost"+userser.img(user1)).toString());
//                            profileImage.setImage(image);
                        
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);

                        alert.setTitle("Information Dialog");

                        alert.setHeaderText(null);

                        alert.setContentText("Compte introuvable ou non vérifié ou bloqué!");

                        alert.show();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        veriflink.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/VerifyAccount.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Vérification de compte");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
        });
        forgotpass.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/ForgotPass.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Mot de passe oublié");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {

            }
        });
        
    }
    
    

}
