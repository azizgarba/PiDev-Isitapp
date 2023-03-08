/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tn.esprit.codefellaz.services.UserService;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 * FXML Controller class
 *
 * @author LENOVO
 */
public class VerifyAccountController implements Initializable {

    @FXML
    private TextField code;
    @FXML
    private Button valcode;
    Connection cnx1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        valcode.setOnAction(e->{
            cnx1 = MyConnection.getInstance().getCnx();

            String query2 = "select * from utilisateurs where code_verification=? ";
            try {

                PreparedStatement smt = cnx1.prepareStatement(query2);
                smt.setString(1, code.getText());
                ResultSet rs = smt.executeQuery();
                if (rs.next()) {
                    UserService userser=new UserService();
                    userser.updateStatus(code.getText());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);

                alert.setTitle("Information Dialog");

                alert.setHeaderText(null);

                alert.setContentText("Votre compte est vérifié,vous pouvez connecter!");

                alert.show();
                    
                }
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });

    }

}
