/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author MAG-PC
 */
public class purgoMalumApi {
     private static final String API_ENDPOINT = "https://www.purgomalum.com/service/containsprofanity?text=";
     public String response="";

    public String test_if_has_bad_words(String word) {
       
                try {
                    // Make HTTP request
                    URL url = new URL(API_ENDPOINT + word);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");

                    // Read response
                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder response1 = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response1.append(inputLine);
                    }
                    response=response1.toString();
                    in.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
        return response;

    }
}
