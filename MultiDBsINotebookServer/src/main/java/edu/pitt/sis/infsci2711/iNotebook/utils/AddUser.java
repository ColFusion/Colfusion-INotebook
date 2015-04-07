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
        File username = new File("username.txt");
        File secr = new File("serc.txt");
        try {
            username.createNewFile();
            secr.createNewFile();
            BufferedWriter out = new BufferedWriter(new FileWriter(username));
            out.write(email);
            out.close();

            BufferedWriter out1 = new BufferedWriter(new FileWriter(secr));
            out1.write(email + ":" + password);
            out1.flush();
            out1.close();

            process = runtime.exec("sudo sh /opt/project/MultiDBs-INotebook-Server/MultiDBsINotebookServerAPI/createUser.sh");
            System.out.println("sudo sh createUser.sh");
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
