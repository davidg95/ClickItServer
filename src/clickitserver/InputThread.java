/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import io.github.davidg95.productapi.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 *
 * @author David
 */
public class InputThread extends Thread {

    private final Socket s;
    private final Data data;

    private BufferedReader in;
    private PrintWriter out;
    private ObjectInputStream obIn;
    private ObjectOutputStream obOut;

    private final Semaphore dataSem;
    private final Semaphore clientSem;

    private boolean conn_term = false;

    /**
     * Constructor for input thread class.
     *
     * @param s the socket the connection is on.
     * @param dataSem the semaphore for the data.
     * @param clientsSem the semaphore for the client list.
     * @param data the data for the server.
     */
    public InputThread(Socket s, Semaphore dataSem, Semaphore clientsSem, Data data) {
        this.s = s;
        this.dataSem = dataSem;
        this.clientSem = clientsSem;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            System.out.println(s.getInetAddress().getHostAddress() + " connected");
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out = new PrintWriter(s.getOutputStream(), true);
            obIn = new ObjectInputStream(s.getInputStream());
            obOut = new ObjectOutputStream(s.getOutputStream());
            obOut.flush();

            while (!conn_term) {
                String input = in.readLine(); //Get input

                String inp[] = input.split(","); //Split up into arguments

                switch (inp[0]) {
                    case "NEW": //Add a new product
                        Object o;
                        try {
                            o = obIn.readObject();

                            Product p = (Product) o;
                            dataSem.acquire();
                            data.addProduct(p);
                        } catch (ClassNotFoundException | InterruptedException ex) {
                        }
                        dataSem.release();
                        break;
                    case "PUR": //Purchase a product
                        try {
                            String code = inp[1];
                            dataSem.acquire();
                            data.purchaseProduct(code);
                            out.println("SUCC");
                        } catch (InterruptedException ex) {

                        } catch (OutOfStockException ex) {
                            out.println("FAIL STOCK");
                        } catch (ProductNotFoundException ex) {
                            out.println("FAIL NFOUND");
                        }
                        dataSem.release();
                        break;
                    case "GET": //Get a product
                        try {
                            String code = inp[1];
                            dataSem.acquire();
                            Product p = data.getProduct(code);
                            obOut.writeObject(p);
                        } catch (InterruptedException ex) {

                        } catch (ProductNotFoundException ex) {
                            obOut.writeObject(ex);
                        }
                        dataSem.release();
                        break;
                    case "GETSTOCK": //Get a products stock level
                        try {
                            String code = inp[1];
                            dataSem.acquire();
                            int stock = data.getStock(code);
                            out.println(stock);
                        } catch (InterruptedException ex) {

                        } catch (ProductNotFoundException ex) {
                            out.println("FAIL");
                        }
                        dataSem.release();
                        break;
                    case "STOCKINC": //Increase a products stock level
                        try {
                            String code = inp[1];
                            int stock = Integer.parseInt(inp[2]);
                            dataSem.acquire();
                            data.increaseStock(code, stock);
                            out.println("SUCC");
                        } catch (InterruptedException ex) {

                        } catch (ProductNotFoundException ex) {
                            out.println("FAIL NFOUND");
                        }
                        dataSem.release();
                        break;
                    case "DEL": //Delete a product
                        try {
                            String code = inp[1];
                            dataSem.acquire();
                            data.deleteProduct(code);
                            out.println("SUCC");
                        } catch (InterruptedException ex) {

                        } catch (ProductNotFoundException ex) {
                            out.println("FAIL NFOUND");
                        }
                        dataSem.release();
                        break;
                    case "GETALL": //Get all the products
                        try {
                            dataSem.acquire();
                            List<Product> products = data.getAllProducts();
                            out.println(products.size());
                            for (Product p : products) {
                                obOut.writeObject(p);
                            }
                        } catch (InterruptedException ex) {

                        }
                        dataSem.release();
                        break;
                    case "SENDDATA": //Get sales data.
                        try {
                            double takings = Double.parseDouble(inp[1]);
                            dataSem.acquire();
                            data.addTakings(takings);
                        } catch (InterruptedException ex) {

                        }
                        dataSem.release();
                        break;
                    case "CONNTERM": //Terminate the connection
                        conn_term = true;
                        break;
                    default:

                }
            }
            try {
                clientSem.acquire();
                data.removeClient(s);
            } catch (InterruptedException ex) {
            }
            clientSem.release();
            s.close();
            System.out.println(s.getInetAddress().getHostAddress() + " has disconnected");
        } catch (IOException e) {
        }
    }
}
