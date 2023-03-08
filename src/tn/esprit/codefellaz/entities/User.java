/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.codefellaz.entities;

/**
 *
 * @author LENOVO
 */
public class User {

    private int id;
    private String userName;
    private String email;
    private String tel;
    private String Birth;
    private String gender;
    private String password;
    private String verifyCode;
    private int status;
    private String role;
    private String profilImg;
    private int etat;

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getBirth() {
        return Birth;
    }

    public String getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public int getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }

    public String getProfilImg() {
        return profilImg;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setBirth(String Birth) {
        this.Birth = Birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setProfilImg(String profilImg) {
        this.profilImg = profilImg;
    }

    public User(int id, String userName, String email, String tel, String Birth, String gender, String password, String verifyCode, String role, String profilImg) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.tel = tel;
        this.Birth = Birth;
        this.gender = gender;
        this.password = password;
        this.verifyCode = verifyCode;
        this.role = role;
        this.profilImg = profilImg;
    }

    public User(String userName, String email, String tel, String Birth, String gender, String password, String verifyCode, String role, String profilImg) {
        this.userName = userName;
        this.email = email;
        this.tel = tel;
        this.Birth = Birth;
        this.gender = gender;
        this.password = password;
        this.verifyCode = verifyCode;
        this.role = role;
        this.profilImg = profilImg;
    }

    public User() {
    }

    public User(String userName, String email, String tel, String Birth, String gender, String role) {
        this.userName = userName;
        this.email = email;
        this.tel = tel;
        this.Birth = Birth;
        this.gender = gender;
        this.role = role;
    }

    public User(String userName,String email) {
        this.userName=userName;
        this.email = email;
    }

    public User(String userName, String email, String profilImg) {
        this.userName = userName;
        this.email = email;
        this.profilImg = profilImg;
    }

    public User(int id, String role) {
        this.id = id;
        this.role = role ;
    }

    
    
    
    @Override
    public String toString() {
        return "User{" + " userName=" + userName + ", email=" + email + ", tel=" + tel + ", Birth=" + Birth + ", gender=" + gender + ", password=" + password + ", status=" + status + ", role=" + role + ", profilImg=" + profilImg + ",etat=" + etat + '}';
    }

}
