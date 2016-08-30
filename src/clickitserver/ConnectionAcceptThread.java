/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Thread which deals with accepting incoming connections and spawn new threads
 * to deal with those incoming connections.
 *
 * @author David
 */
public class ConnectionAcceptThread extends Thread {

    private final ServerSocket s;
    private final Data data;
    private final ServerGUI g;

    private final Semaphore dataSem;
    private final Semaphore clientsSem;

    /**
     * Constructor for the thread.
     *
     * @param s the ServerSocket object.
     * @param dataSem the Semaphore for the products list.
     * @param clientsSem the Semaphore for the client list.
     * @param data data which stores all the lists.
     * @param g reference to the GUI to add to the list of clients connected.
     */
    public ConnectionAcceptThread(ServerSocket s, Semaphore dataSem, Semaphore clientsSem, Data data, ServerGUI g) {
        this.s = s;
        this.dataSem = dataSem;
        this.clientsSem = clientsSem;
        this.data = data;
        this.g = g;
    }

    @Override
    public void run() {
        System.out.println("Starting BankingServer on port number " + ClickItServer.PORT);
        System.out.println("Up to " + ClickItServer.MAX_CONNECTIONS + " connections can be accepted with " + ClickItServer.MAX_QUEUE + " queued");

        ThreadPoolExecutor pool = new ThreadPoolExecutor(ClickItServer.MAX_CONNECTIONS, ClickItServer.MAX_QUEUE, 50000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(ClickItServer.MAX_QUEUE)); //Create the thread pool
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        for (;;) {
            try {
                Socket incoming = s.accept();
                data.addClient(incoming);
                pool.submit(new InputThread(incoming, dataSem, clientsSem, data)); //Start a new thread
            } catch (IOException ex) {

            }
        }
    }
}
