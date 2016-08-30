/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Semaphore;

/**
 * ClickIt server class which accepts connections from multiple clients.
 *
 * @author David
 */
public class ClickItServer {

    public static final int PORT = 500;
    public static final int MAX_CONNECTIONS = 10;
    public static final int MAX_QUEUE = 10;

    public Semaphore dataSem;
    public Semaphore clientsSem;

    private ServerSocket s;
    private final Data data;
    private final ServerGUI g;
    private ConnectionAcceptThread connThread;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ClickItServer().start();
    }

    public ClickItServer() {
        data = new Data();
        g = new ServerGUI(data);
        dataSem = new Semaphore(1);
        clientsSem = new Semaphore(1);
        try {
            s = new ServerSocket(PORT);
            connThread = new ConnectionAcceptThread(s, dataSem, clientsSem, data, g);
        } catch (IOException ex) {

        }
    }

    public void start() {
        connThread.start();
        g.setVisible(true);
        g.login();
    }

}
