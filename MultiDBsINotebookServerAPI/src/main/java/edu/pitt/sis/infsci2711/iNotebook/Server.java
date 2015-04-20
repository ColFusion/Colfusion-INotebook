/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2711.iNotebook;

import edu.pitt.sis.infsci2711.multidbs.utils.JerseyJettyServer;
import edu.pitt.sis.infsci2711.multidbs.utils.PropertiesManager;
import java.io.File;

/**
 *
 * @author Wu
 */
public class Server {

    private final static String PROPERTY_PORT = "port";
    private final static int DEFAULT_PORT = 7654;

    public static void main(final String[] args) throws Exception {
        if (args.length > 0) {
            String propertiesFilePath = args[0];
            File propertiesFile = new File(propertiesFilePath);
            PropertiesManager.getInstance().loadProperties(propertiesFile);
        }
        final JerseyJettyServer server = new JerseyJettyServer(PropertiesManager.getInstance().getIntProperty(PROPERTY_PORT, DEFAULT_PORT), "edu.pitt.sis.infsci2711.iNotebook.rest");
        Thread serverTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.start();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        serverTread.start();

        System.out.println("NOTE: To stop the server, focus on console and hit enter");
        System.in.read();

        server.stop();
    }
}
