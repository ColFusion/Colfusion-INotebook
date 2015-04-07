/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2711.iNotebook.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Wu
 */
public class AddUser {

    public static boolean addUser(String email, String password) {
        Runtime runtime = Runtime.getRuntime();
        Process process;
        try {
            process = runtime.exec("echo \"infsci27115\" | sudo -S -v");
            process = runtime.exec("sudo adduser " + email + " --gecos \"\" --disabled-password");
            process = runtime.exec("echo \"" + email + ":" + password + "\"" + " | sudo chpasswd");
            //username.delete();
            //secr.delete();
            System.out.println("Process created.");
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
