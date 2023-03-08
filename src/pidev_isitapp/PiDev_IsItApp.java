/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev_isitapp;


import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tn.esprit.codefellaz.entities.Produit;
import tn.esprit.codefellaz.services.ProduitService;
/**
 *
 * @author ASUS
 */
public class PiDev_IsItApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/tn/esprit/codefellaz/views/SignInUp.fxml"));        
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }
    
    
    public static void main(String[] args){
     
       launch(args);
        
//        ProduitService CP = new ProduitService();
//        
//        Produit Prod = new Produit();
//        Prod = CP.findById(45);
//       System.out.println(CP.ReturnImg(Prod)) ;

 

    }

}

    

