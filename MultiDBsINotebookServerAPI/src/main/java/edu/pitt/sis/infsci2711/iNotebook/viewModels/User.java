/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2711.iNotebook.viewModels;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wu
 */
@XmlRootElement
public class User {

    private int id;
    private String username;
    private String password;
    private String email;

    User() {

    }

    public User(final String username, final String password, final String email) {
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
    }

    public User(final int id, final String username, final String password, final String email) {
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setEmail(email);
    }

    public User(final int id, final String username, final String email) {
        this.setId(id);
        this.setUsername(username);
        this.setEmail(email);
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
