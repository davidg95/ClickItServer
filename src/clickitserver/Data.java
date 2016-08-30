/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import io.github.davidg95.clickitapi.Camera;
import io.github.davidg95.clickitapi.Lens;
import io.github.davidg95.productapi.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class which extends off ProductList. It adds methods for reading and writing
 * to file.
 *
 * @author David
 */
public class Data {

    private final List<Product> products;
    private final List<Socket> clients;

    private double takings;

    private static int cameraCounter;
    private static int lensCounter;

    private final File camerasFile;
    private final File lensesFile;

    /**
     * Constructor which initialises the Lists and the Files.
     */
    public Data() {
        products = new ArrayList<>();
        clients = new ArrayList<>();

        takings = 0;

        camerasFile = new File("cameras.txt");
        lensesFile = new File("lenses.txt");

        openFile();
    }

    /**
     * Method to add a client to the list.
     *
     * @param s the socket the client is connected on.
     */
    public void addClient(Socket s) {
        clients.add(s);
    }

    /**
     * Method to remove a client from the list.
     *
     * @param s the socket the client was connected from.
     */
    public void removeClient(Socket s) {
        clients.remove(s);
    }

    /**
     * Method to add takings.
     *
     * @param t takings value to add.
     */
    public void addTakings(double t) {
        takings += t;
    }

    /**
     * Method to get takings.
     *
     * @return takings as double.
     */
    public double getTakings() {
        return this.takings;
    }

    /**
     * Method to reset takings to 0.
     */
    public void resetTakings() {
        takings = 0;
    }

    /**
     * Method to add a new product to the list.
     *
     * @param p the products to add.
     */
    public void addProduct(Product p) {
        String t = "";
        if (p instanceof Camera) {
            t = "c";
        } else if (p instanceof Lens) {
            t = "l";
        }
        p.setCode(getNewProductCode(t));
        products.add(p);
        saveToFile();
    }

    /**
     * Method to purchase a product.
     *
     * @param code the product code to purchase.
     * @throws OutOfStockException if the product is out of stock.
     * @throws ProductNotFoundException if the product could not be found.
     */
    public void purchaseProduct(String code) throws OutOfStockException, ProductNotFoundException {
        for (Product p : products) {
            if (p.getCode().equals(code)) {
                p.purchase();
                saveToFile();
                return;
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to get a product by product code.
     *
     * @param code the product code to get.
     * @return Product object.
     * @throws ProductNotFoundException if the product code was not found.
     */
    public Product getProduct(String code) throws ProductNotFoundException {
        for (Product p : products) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to get the stock level of a product.
     *
     * @param code the product code the get the stock of.
     * @return the stock level as an int.
     * @throws ProductNotFoundException if the product code was not found.
     */
    public int getStock(String code) throws ProductNotFoundException {
        for (Product p : products) {
            if (p.getCode().equals(code)) {
                return p.getStock();
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to increase the stock level of a product.
     *
     * @param code the code to increase the stock level of.
     * @param stock the stock to add.
     * @throws ProductNotFoundException if the product code was not found.
     */
    public void increaseStock(String code, int stock) throws ProductNotFoundException {
        products.stream().filter((p) -> (p.getCode().equals(code))).forEach((p) -> {
            p.increaseStock(stock);
            saveToFile();
        });
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to delete a product from the list.
     *
     * @param code the product code to delete.
     * @throws ProductNotFoundException if the product code was not found.
     */
    public void deleteProduct(String code) throws ProductNotFoundException {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getCode().equals(code)) {
                products.remove(i);
                saveToFile();
                return;
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to return the List of all the products.
     *
     * @return List of type Product.
     */
    public List<Product> getAllProducts() {
        return products;
    }

    /**
     * Method to create a new 6 digit product code based on how many products
     * there already are.
     *
     * @return String value of a new product code.
     */
    private static String getNewProductCode(String t) {
        String no = "";
        String zeros = "";
        switch (t) {
            case "c":
                no = Integer.toString(cameraCounter);
                zeros = "";
                for (int i = no.length(); i < 6; i++) {
                    zeros += "0";
                }
                cameraCounter++;
                break;
            case "l":
                no = Integer.toString(lensCounter);
                zeros = "";
                for (int i = no.length(); i < 6; i++) {
                    zeros += "0";
                }
                lensCounter++;
                break;
        }

        return t.toUpperCase() + zeros + no;
    }

    /**
     * Method to save the contents of the list to a file.
     */
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter("cameras.txt", "UTF-8")) {
            System.out.println("Starting to write to cameras file");
            writer.println(cameraCounter);
            for (int i = 0; i <= (products.size() - 1); i++) {
                if (products.get(i) instanceof Camera) {
                    writer.println(products.get(i).toCSV());
                }
            }
            System.out.println("Writing to cameras file complete");
        } catch (IOException ex) {

        }

        try (PrintWriter writer = new PrintWriter("lenses.txt", "UTF-8")) {
            System.out.println("Starting to write to lenses file");
            writer.println(lensCounter);
            for (int i = 0; i <= (products.size() - 1); i++) {
                if (products.get(i) instanceof Lens) {
                    writer.println(products.get(i).toCSV());
                }
            }
            System.out.println("Writing to lenses file complete");
        } catch (IOException ex) {

        }
    }

    /**
     * Method to open a file and save it to the list.
     */
    public final void openFile() {
        System.out.println("Starting to open cameras file");
        try {
            Scanner fileReader = new Scanner(camerasFile);

            cameraCounter = Integer.parseInt(fileReader.nextLine());
            if (fileReader.hasNextLine()) {
                do {
                    products.add(Camera.cameraFromNotepad(fileReader.nextLine()));
                } while (fileReader.hasNextLine());
                System.out.println("Open cameras file complete");
            } else {
                System.out.println("Cameras file is empty");
            }
        } catch (IOException e) {
            try {
                System.out.println("Createing new file " + camerasFile.getName());
                boolean createNewFile = camerasFile.createNewFile();
                System.out.println("New file " + camerasFile.getName() + " created");
            } catch (IOException ex) {
            }
        }

        System.out.println("Starting to open lenses file");
        try {
            Scanner fileReader = new Scanner(lensesFile);

            lensCounter = Integer.parseInt(fileReader.nextLine());
            if (fileReader.hasNextLine()) {
                do {
                    products.add(Lens.lensFromNotepad(fileReader.nextLine()));
                } while (fileReader.hasNextLine());
                System.out.println("Open customer file complete");
            } else {
                System.out.println("Customer file is empty");
            }
        } catch (IOException e) {
            try {
                System.out.println("Createing new file " + lensesFile.getName());
                boolean createNewFile = lensesFile.createNewFile();
                System.out.println("New file " + lensesFile.getName() + " created");
            } catch (IOException ex) {
            }
        }
    }
}
