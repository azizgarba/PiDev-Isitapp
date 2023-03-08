/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.controllers;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import tn.esprit.codefellaz.services.ProduitService;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class StatistiquesProduitController implements Initializable {

    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private PieChart pieChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        iniLineChart();
        iniPieChart();
    }    
    
    private void iniLineChart(){
        XYChart.Series series = new XYChart.Series();
        
        ProduitService p = new ProduitService();
        Map<String, Integer> countProductsByCategory = p.countProductsByCategory();
        //     countProductsByCategory.get()
        

//        for (String category : countProductsByCategory.keySet()) {
//            int count = countProductsByCategory.get(category);
//            System.out.println("Number of products in the " + category + " category: " + count);
//            dataset.addValue(count,category, " ");
//        }
        countProductsByCategory.keySet().forEach((category) -> {
            int count = countProductsByCategory.get(category);
            //System.out.println("Number of products in the " + category + " category: " + count);
            series.getData().add(new XYChart.Data(category,count));
            //dataset.addValue(count, category, "");
        });
        
        
//        series.getData().add(new XYChart.Data("Monday",8));
//        series.getData().add(new XYChart.Data("tuesday",12));
//        series.getData().add(new XYChart.Data("wednesday",2));
//        series.getData().add(new XYChart.Data("thursday",7));
//        series.getData().add(new XYChart.Data("friday",5));
        
        
        
        
        lineChart.getData().addAll(series);
        lineChart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;"); //#e24933
        series.getNode().setStyle("-fx-stroke: linear-gradient(from 25% 25% to 100% 100%, #091F4E,#18b9dF);");
        
    }
    
    private void iniPieChart(){
        
        ProduitService p = new ProduitService();
        Map<String, Integer> countProductsByCategory = p.countProductsByCategory();
        //     countProductsByCategory.get()
        System.out.println(p.countProductsByCategory());

//        for (String category : countProductsByCategory.keySet()) {
//            int count = countProductsByCategory.get(category);
//            System.out.println("Number of products in the " + category + " category: " + count);
//            dataset.addValue(count,category, " ");
//        }
        countProductsByCategory.keySet().forEach((category) -> {
            int count = countProductsByCategory.get(category);
            //System.out.println("Number of products in the " + category + " category: " + count);
          //  series.getData().add(new XYChart.Data(category,count));   
            //dataset.addValue(count, category, "");
        });
        
         countProductsByCategory.keySet().forEach((category) -> {
            int count = countProductsByCategory.get(category); });
         
         
            
        ObservableList <PieChart.Data> pieChartData = FXCollections.observableArrayList(
        
                
                
                
//                new PieChart.Data("Android",15),
//                new PieChart.Data("FX",15),
//                new PieChart.Data("JAVA",20),
//                new PieChart.Data("GOOGLE",15)
        
        );
        
        countProductsByCategory.keySet().forEach((category) -> {
    int count = countProductsByCategory.get(category);
    PieChart.Data data = new PieChart.Data(category, count);
    pieChartData.add(data);
});
        
        pieChart.setData(pieChartData);
    }
}
