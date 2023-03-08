/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.codefellaz.services.UserService;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */

public class UpdatePassController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private PasswordField orgpass;
    @FXML    
    private Button valmodpass;
    @FXML
    private AnchorPane updatepass;
    @FXML
    private PasswordField newpass;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        valmodpass.setOnAction(event -> {
            UserService userserv=new UserService();
            userserv.updatePass(orgpass.getText(), newpass.getText());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Information Dialog");

            alert.setHeaderText(null);

            alert.setContentText("Mot de passe modifié!");

            alert.show();
            Stage stage1 = (Stage) updatepass.getScene().getWindow();
            stage1.close();
        });
    }    
    
}
