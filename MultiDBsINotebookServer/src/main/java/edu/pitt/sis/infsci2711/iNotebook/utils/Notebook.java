package edu.pitt.sis.infsci2711.iNotebook.utils;

import java.io.File;
import java.io.IOException;

public class Notebook {

    public static void main(String[] args) {
        RunNotebook("test");
    }

    public static boolean RunNotebook(String input) {
        Runtime runtime = Runtime.getRuntime();
        String systemName = System.getProperty("os.name").toLowerCase();
        File file;
        Process process;

        //should get userid, and create directory by userid
        if (systemName.contains("windows")) {
            file = new File("C:\\notebook\\" + input);// windows directory path
            //System.out.println("selected path of windows");
        } else {
            file = new File("/Users/Wu/" + input + "/");// linux directory path
            //System.out.println("selected path of linux");
        }

        if (!file.exists()) {// if folder is not exist, create the folder
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }

        try {// run ipython notebook
            process = runtime.exec("ipython notebook ", null, file);
            System.out.println("Process created.");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

        try {
            Thread.sleep(3600000);// Auto-destroy the process after an hour. (temporary implementation)
            process.destroy();
            System.out.println("Process destroyed.");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
