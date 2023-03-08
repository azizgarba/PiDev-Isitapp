/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import javafx.collections.ObservableList;
import tn.esprit.codefellaz.entities.User;

/**
 *
 * @author LENOVO
 */
public interface InterfaceUser {

    public void ajouterUser(User user);

    public void modifierUser(User user);

    public void supprimerUser(User user);

    public ObservableList<User> afficherUsers();

}
