/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import pidev_isitapp.PiDev_IsItApp;
import tn.esprit.codefellaz.utils.MyConnection;

/**
 *
 * @author user
 */


public class updateBase {
    
    
    
    public static void main(String[] args) throws ParseException {
     Connection cnx ; 
     cnx = MyConnection.getInstance().getCnx();
     System.out.println("test");
    try {
        String requete = "SELECT * FROM demandes_services";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            Double priorite = calculatePriorite(rs.getString("date_limite"), rs.getFloat("budget"));
            requete = "UPDATE demandes_services SET priorite = " + priorite + " WHERE id_demande = " + rs.getInt("id_demande");
            st = cnx.createStatement();
            st.executeUpdate(requete);
        }
        System.out.println("personnes mises à jour");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());        }
    
     updateJour(); 
//    
//        try {
//        String requete = "SELECT * FROM offres_services";
//        Statement st = cnx.createStatement();
//        ResultSet rs = st.executeQuery(requete);
//        while (rs.next()) {
//            String date = rs.getString(8);
//            System.out.println(date);
//        String dateFormat = "yyyy-MM-dd";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
//        LocalDate otherDate = LocalDate.parse(date, formatter);
//        LocalDate currentDate = LocalDate.now();
//        int daysBetween = (int) ChronoUnit.DAYS.between(currentDate, otherDate);
//            System.out.println(daysBetween);
//            requete = "UPDATE offres_services SET date_depuis_derniere_commande = " + daysBetween*(-1) + " WHERE id_offre_service = " + rs.getInt("id_offre_service");
//            st = cnx.createStatement();
//            st.executeUpdate(requete);
//        }
//        System.out.println("personnes mises à jour");
//    } catch (SQLException ex) {
//        System.err.println(ex.getMessage());        }
    
}
    
    
     public static  void updateJour(){
       Connection cnx ; 
     cnx = MyConnection.getInstance().getCnx();
         
        try {
        String requete = "SELECT * FROM offres_services";
        Statement st = cnx.createStatement();
        ResultSet rs = st.executeQuery(requete);
        while (rs.next()) {
            String date = rs.getString(8);
            System.out.println(date);
        String dateFormat = "yyyy-MM-dd";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
        LocalDate otherDate = LocalDate.parse(date, formatter);
        LocalDate currentDate = LocalDate.now();
        int daysBetween = (int) ChronoUnit.DAYS.between(currentDate, otherDate);
            System.out.println(daysBetween);
            requete = "UPDATE offres_services SET date_depuis_derniere_commande = " + daysBetween*(-1) + " WHERE id_offre_service = " + rs.getInt("id_offre_service");
            st = cnx.createStatement();
            st.executeUpdate(requete);
        }
        System.out.println("personnes mises à jour");
    } catch (SQLException ex) {
        System.err.println(ex.getMessage());        }
     
     
     }
    
    
      public static Double calculatePriorite(String dateLimite, float prix) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        Double prioriteNA = 365.0;
        try {
            date = formatter.parse(dateLimite);
        } catch (ParseException ex) {
            Logger.getLogger(PiDev_IsItApp.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date currentDate = new Date();
        Long difference = currentDate.getTime() - date.getTime();
        Long differenceInDays = difference / (1000 * 60 * 60 * 24);

        if (differenceInDays <= -365) {

            prioriteNA = 0.0;

            return prioriteNA;
        } else {
            prioriteNA = (prioriteNA + differenceInDays) * 0.9 + (prix) * 0.1;
            DecimalFormat df = new DecimalFormat("#.##");

            Double prioriteArrondi = Math.round(prioriteNA * 100.0) / 100.0;

            return prioriteArrondi;

        }

    }
    
}
