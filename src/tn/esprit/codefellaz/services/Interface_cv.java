/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.services;

import java.util.List;
import tn.esprit.codefellaz.entities.cv;

/**
 *
 * @author Khouloud DAIRA
 */
public interface Interface_cv {
     public void ajouter_CV();
    public void ajouter_CV2(cv c);
    public List<cv> afficher_CV();
    public void SupprimerCvById (int id_cv);
    public void modifier(cv c);
}
