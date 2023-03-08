/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author ASUS
 */
public class TwilioSMS {
 public static void send(String numero, String contenu) {

        final String AUTH_TOKEN = "fa1065a7c9215ad7bb009623176cfc82";
        final String ACCOUNT_SID = "AC47ff35c6beb09e34c82299161a3bb5e5";

 System.out.println(AUTH_TOKEN);

    
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        
       Message message;
     message = Message.creator(
             new PhoneNumber(numero), // TO
             new PhoneNumber("+12706755936"), // FROM
             contenu
     ).create(); 

        System.out.println(message.getSid());
    }
//12706755936
}
