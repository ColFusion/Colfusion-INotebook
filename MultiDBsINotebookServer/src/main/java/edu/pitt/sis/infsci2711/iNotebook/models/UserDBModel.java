/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2711.iNotebook.models;

/**
 *
 * @author Wu
 */
public class UserDBModel {
    
    private int id;
    private String userName;
    private String password;
    private String email;
    
    public UserDBModel() {
    }
    
    public UserDBModel(final String userName, final String password, final String email) {
        this.setUserName(userName);
        this.setPassword(password);
        this.setEmail(email);
    }
    
    public UserDBModel(final int id, final String userName, final String password, final String eamil) {
        this.setId(id);
        this.setUserName(userName);
        this.setPassword(password);
        this.setEmail(email);
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
