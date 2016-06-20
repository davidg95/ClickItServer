/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 *
 * @author David
 */
public class InputThread extends Thread {

    private final ServerSocket s;
    private final Semaphore sem;
    private final CameraList cameras;

    private boolean conn_term = false;

    public InputThread(ServerSocket s, Semaphore sem, CameraList data) {
        this.s = s;
        this.sem = sem;
        this.cameras = data;
    }

    @Override
    public void run() {
        try {
            Socket incoming = s.accept();
            System.out.println(incoming.getInetAddress().getHostAddress() + " connected");
            BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            PrintWriter out = new PrintWriter(incoming.getOutputStream(), true);

            while (!conn_term) {
                String input = in.readLine(); //Get input

                String inp[] = input.split(","); //Split up into arguments

                switch (inp[0]) {
                    case "NEW": //New camera getting added
                        sem.acquire();
                        boolean sensor = false;
                        if (inp[4].equals("FULL")) {
                            sensor = true;
                        }
                        try {
                            cameras.addCamera(new Camera(inp[1], inp[2], Double.parseDouble(inp[3]), sensor, Integer.parseInt(inp[5]), Double.parseDouble(inp[6])));
                            cameras.saveToFile();
                            out.println("SUCC");
                        } catch (CodeAlreadyExistsException e) {
                            out.println("FAIL CODE");
                        }
                        sem.release();
                        break;
                    case "PUR": //Camera getting purchased
                        sem.acquire();
                        try {
                            cameras.purchaseCamera(inp[1]);
                            cameras.saveToFile();
                            out.println("SUCC");
                            System.out.println("Purchase Successful");
                        } catch (OutOfStockException e) {
                            out.println("FAIL STOCK");
                            System.out.println(e.getMessage());
                        } catch (CameraNotFoundException e) {
                            out.println("FAIL NFOUND");
                            System.out.println(e.getMessage());
                        }
                        sem.release();
                        break;
                    case "DEL": //Camera getting deleted
                        sem.acquire();
                        try {
                            cameras.removeCamera(inp[1]);
                            cameras.saveToFile();
                            out.println("SUCC");
                            System.out.println("Camera " + inp[1] + " deleted");
                        } catch (CameraNotFoundException e) {
                            out.println("FAIL NFOUND");
                        } catch (Exception e) {
                            System.out.println(e);
                            out.println("FAIL");
                        } finally {
                            sem.release();
                        }
                        break;
                    case "GET": //Searching for a camera by product code
                        sem.acquire();
                        try {
                            Camera c = cameras.getCamera(inp[1]);
                            sem.release();
                            String str = c.getMake() + "," + c.getModel() + "," + c.getMegapixles() + "," + c.getSensor() + "," + c.getStock() + "," + c.getPrice();
                            out.println(str);
                        } catch (CameraNotFoundException e) {
                            sem.release();
                            out.println("FAIL");
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "GETINDEX": //Get a camera by index
                        sem.acquire();
                        try {
                            Camera c = cameras.getCamera(Integer.parseInt(inp[1]));
                            sem.release();
                            String str = c.getMake() + "," + c.getModel() + "," + c.getMegapixles() + "," + c.getSensor() + "," + c.getStock() + "," + c.getPrice();
                            out.println(str);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            sem.release();
                            out.println("FAIL");
                            System.out.println("Index is out of bounds");
                        }
                        break;
                    case "GETSIZE": //Get the total number of different cameras
                        sem.acquire();
                        out.println(Integer.toString(cameras.size()));
                        sem.release();
                        break;
                    case "GETSTOCK": //Get the stock level of a camera
                        sem.acquire();
                        try {
                            Camera c = cameras.getCamera(inp[1]);
                            sem.release();
                            out.println(c.getStock());
                        } catch (CameraNotFoundException e) {
                            sem.release();
                            out.println("FAIL");
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "STOCKINC": //Increace the stock
                        sem.acquire();
                        try {
                            cameras.increaceStock(inp[1], Integer.parseInt(inp[2]));
                            cameras.saveToFile();
                            out.println("SUCC");
                        } catch (CameraNotFoundException ex) {
                            out.println("FAIL NFOUND");
                        } finally {
                            sem.release();
                        }
                        break;
                    case "CONNTERM": //Terminate the connection
                        conn_term = true;
                        break;
                    default: //If input is not recognised
                        System.out.println(inp[0] + " was not recognised");
                        out.println("NOTREC");
                        break;
                }
            }
            incoming.close();
            System.out.println(incoming.getInetAddress().getHostAddress() + " has disconnected");
        } catch (IOException | InterruptedException ex) {
        }
    }
}
