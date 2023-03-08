/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.awt.event.MouseEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.codefellaz.entities.Produit;
import tn.esprit.codefellaz.services.ProduitService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class ItemController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private ImageView img;
    
    @FXML
    void click(MouseEvent mouseEvent) {
 myListener.onClickListener(produit);
    }
            
//    private void click2(MouseEvent mouseEvent){
//        myListener.onClickListener(produit);
//    }

    private Produit produit ;
    private MyListener myListener ;
    /**
     * Initializes the controller class.
     * @param produit
     * @param myListener 
     */
    
    public void setData(Produit produit, MyListener myListener) throws MalformedURLException{
        ProduitService CP = new ProduitService();
        
        this.produit = produit ;
        this.myListener = myListener ; 
        nameLabel.setText(produit.getNom_produit());
        priceLabel.setText(Float.toString(produit.getPrix_produit()));
        Image image = new Image(new URL("http://localhost"+CP.ReturnImg(produit)).toString()); //new URL("http://localhost"+userser.img(user1)).toString()
        img.setImage(image);
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void click(javafx.scene.input.MouseEvent event) {
         myListener.onClickListener(produit);
    }
    
}
