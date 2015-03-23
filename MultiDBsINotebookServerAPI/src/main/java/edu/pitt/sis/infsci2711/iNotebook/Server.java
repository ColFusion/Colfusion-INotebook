/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2711.iNotebook;

import edu.pitt.sis.infsci2711.multidbs.utils.JerseyJettyServer;

/**
 *
 * @author Wu
 */
public class Server {

    public static void main(final String[] args) throws Exception {
        final JerseyJettyServer server = new JerseyJettyServer(7654, "edu.pitt.sis.infsci2711.iNotebook.rest");
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
