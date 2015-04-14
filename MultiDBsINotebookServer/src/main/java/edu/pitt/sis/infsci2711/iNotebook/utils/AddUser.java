/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2711.iNotebook.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Wu
 */
public class AddUser {

    public static boolean addUser(String email, String password) {
        Runtime runtime = Runtime.getRuntime();
        Process process,process2;
        try {
            //process = runtime.exec("echo \"infsci27115\" | sudo -S -v");
            //System.out.println("echo \"infsci27115\" | sudo -S -v");
            //process = runtime.exec("sudo adduser " + email + " --gecos \"\" --disabled-password");
            //System.out.println("sudo adduser " + email + " --gecos \"\" --disabled-password");
            //process = runtime.exec("echo \"infsci27115\" | sudo -S -v");
            //String s = "echo \"" + email + ":" + password + "\" | sudo chpasswd ";
            //System.out.println("echo \"infsci27115\" | sudo -S -v");
            //String s = "sudo useradd -d /home/"+email+" -m -p "+password+" "+email;
            //String s2 = "echo \"" + email + ":" + password + "\" | sudo chpasswd ";
            System.out.println("before");
            process = runtime.exec(String.format("./pleaseAddUser.sh %s %s", email, password));
            InputStream errorStream = 
                    process.getErrorStream();
            String result = readStream(errorStream);
            System.out.println("errorStream->"+result);
            System.out.println("after");
            
            InputStream outputStream = process.getInputStream();
            String result2 = readStream(outputStream);
            System.out.println("outputStream->"+result2);
            
            process.waitFor();
            errorStream.close();
            //System.out.println(s);
            //process = runtime.exec(s);
            //System.out.println(s2);
            //process2 = runtime.exec(s2);
            process.destroy();
            //process2.destroy();
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
    }
    
    private static String readStream(InputStream inStream) throws IOException {
        InputStream in = inStream;
        InputStreamReader is = new InputStreamReader(in);
        StringBuilder sb=new StringBuilder();
        BufferedReader br = new BufferedReader(is);
        String read = br.readLine();

        while(read != null) {
            //System.out.println(read);
            sb.append(read);
            read =br.readLine();

        }

        return sb.toString();
    }
}
