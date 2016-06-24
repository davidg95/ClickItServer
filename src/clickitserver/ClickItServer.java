/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClickIt server class which accepts connections from multiple clients.
 *
 * @author David
 */
public class ClickItServer {

    public final int PORT = 500;
    public final int MAX_CONNECTIONS = 10;
    public final int MAX_QUEUE = 10;

    private ServerSocket s;
    private Semaphore sem;
    private ProductList cameras;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ClickItServer().start();
    }

    public ClickItServer() {

    }

    public void start() {

        try {
            s = new ServerSocket(PORT);
            sem = new Semaphore(1);
            cameras = new ProductList();
            
            System.out.println("Starting server on port number " + PORT);
            System.out.println("Up to " + MAX_CONNECTIONS + " can be accepted with " + MAX_QUEUE + " queued");

            ThreadPoolExecutor pool = new ThreadPoolExecutor(MAX_CONNECTIONS, MAX_QUEUE, 50000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(MAX_QUEUE)); //Create the thread pool
            pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

            for (;;) {
                pool.submit(new InputThread(s, sem, cameras)); //Start a new thread
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
