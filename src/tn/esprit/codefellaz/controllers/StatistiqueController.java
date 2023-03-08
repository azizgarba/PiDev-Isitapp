/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import tn.esprit.codefellaz.entities.offre_de_travail;
import tn.esprit.codefellaz.services.offre_de_travail_CRUD;
/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class StatistiqueController implements Initializable {

    @FXML
    private PieChart pie;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     offre_de_travail_CRUD cs = new offre_de_travail_CRUD();
 List<String> listSpécialité = new ArrayList<>();
 
  List<offre_de_travail> listoffre = new ArrayList<>();
  
        try {
            listoffre = cs.afficheroffre_de_travail();
        } catch (SQLException ex) {
            Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
for (offre_de_travail s : listoffre){
    listSpécialité.add(s.getSpecialite_offre_de_travail());
    
}
        
        Map<String, Integer> occurences = new HashMap<>();
        
        for (String element : listSpécialité) {
            if (occurences.containsKey(element)) {
                int count = occurences.get(element);
                occurences.put(element, count + 1);
            } else {
                occurences.put(element, 1);
            }
        }
        
        System.out.println("Nombre d'occurrences des éléments : ");
        for (Map.Entry<String, Integer> entry : occurences.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
             ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data(entry.getKey(), entry.getValue())
                       );
              pie.getData().addAll(pieChartData);
                   pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), "  ", data.pieValueProperty()
                        )
                )
        );
        }
        

   

        
    }    
    
}
