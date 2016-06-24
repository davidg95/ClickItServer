/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.List;

/**
 * A class which stores a list of objects of type Camera and Lens.
 *
 * @author David
 * @version 24/06/2015
 */
public class ProductList {

    protected List<Camera> cameras;
    protected List<Lens> lenses;

    /**
     * Constructor method for the <code>CameraList</code> class which creates an
     * instance of the CameraList object. It creates an ArrayList of type Camera
     * and then calls the method to read from the file.
     */
    public ProductList() {
        cameras = new ArrayList<>();
        lenses = new ArrayList<>();

        this.openFile();
    }

    /**
     * Gets the ArrayList which stores all the Camera objects.
     *
     * @return returns an ArrayList of type Camera.
     */
    public List<Camera> getCameras() {
        return cameras;
    }

    public List<Lens> getLenses() {
        return lenses;
    }

    /**
     * Sets the ArrayList of type Camera.
     *
     * @param cameras the new ArrayList of type Camera.
     */
    public void setCameras(ArrayList<Camera> cameras) {
        this.cameras = cameras;
    }

    public void setLenses(ArrayList<Lens> lenses) {
        this.lenses = lenses;
    }

    /**
     * Method to add a new camera to the list.
     *
     * @param c the camera to be added as a Camera.
     * @throws clickitserver.CodeAlreadyExistsException if the product code
     * already exists.
     */
    public void addCamera(Camera c) throws CodeAlreadyExistsException {
        for (Camera cam : cameras) {
            if (cam.getCode().equals(c.getCode())) {
                throw new CodeAlreadyExistsException(c.getCode());
            }
        }
        c.generateProductCode();
        cameras.add(c);
        System.out.println(c);
    }

    /**
     * Method to add a new lense to the list.
     *
     * @param l the new lens to add as a Lens.
     * @throws CodeAlreadyExistsException if the product code already exists.
     */
    public void addLens(Lens l) throws CodeAlreadyExistsException {
        for (Lens lens : lenses) {
            if (lens.getCode().equals(l.getCode())) {
                throw new CodeAlreadyExistsException(l.getCode());
            }
        }
        l.generateProductCode();
        lenses.add(l);
        System.out.println(l);
    }

    /**
     * Method to remove a camera from the list.
     *
     * @param camera the camera to be removed from the list as a Camera.
     * @throws clickitserver.ProductNotFoundException if the product code is not
     * found.
     */
    public void removeCamera(Camera camera) throws ProductNotFoundException {
        for (int i = 0; i < cameras.size(); i++) {
            if (cameras.get(i).equals(camera)) {
                cameras.remove(i);
                return;
            }
        }
        throw new ProductNotFoundException(camera.getCode());
    }

    /**
     * Method to remove a lens from the list.
     *
     * @param lens the lens to remove from the list as a lens.
     * @throws ProductNotFoundException if the product code is not found.
     */
    public void removeLens(Lens lens) throws ProductNotFoundException {
        for (int i = 0; i < lenses.size(); i++) {
            if (lenses.get(i).equals(lens)) {
                lenses.remove(i);
                return;
            }
        }
        throw new ProductNotFoundException(lens.getCode());
    }

    /**
     * Method to remove a camera from the list.
     *
     * @param i the location of the camera to remove.
     */
    public void removeCamera(int i) {
        cameras.remove(i);
    }

    public void removeLens(int i) {
        lenses.remove(i);
    }

    /**
     * Method to remove either a camera or a lens from the list.
     *
     * @param code the product code of the camera or lens to remove
     * @throws clickitserver.ProductNotFoundException if the product code is not
     * found.
     */
    public void removeProduct(String code) throws ProductNotFoundException {
        for (int i = 0; i < cameras.size(); i++) {
            if (cameras.get(i).getCode().equals(code)) {
                cameras.remove(i);
                return;
            }
        }
        for (int i = 0; i < lenses.size(); i++) {
            if (lenses.get(i).getCode().equals(code)) {
                lenses.remove(i);
                return;
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to return the amount of cameras stored in the list.
     *
     * @return returns the amount of cameras stored in the list as an int.
     */
    public int camerasSize() {
        return cameras.size();
    }

    public int lensesSize() {
        return lenses.size();
    }

    /**
     * Method to return the contents of the ProductList as an Object.
     *
     * @return returns the contents of the ProductList as an Object.
     */
    public Object getCameraArray() {
        return cameras.toArray();
    }

    public Object getLensArray() {
        return lenses.toArray();
    }

    /**
     * Method to return a Camera object.
     *
     * @param i the index of the Camera object you want to get.
     * @return returns a Camera object.
     */
    public Camera getCamera(int i) throws ArrayIndexOutOfBoundsException {
        if (i < cameras.size()) {
            return cameras.get(i);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    /**
     * Method to return a Lens object.
     *
     * @param i the index of the Lens object you want to get.
     * @return returns a Lens object.
     */
    public Lens getLens(int i) throws ArrayIndexOutOfBoundsException {
        if (i < lenses.size()) {
            return lenses.get(i);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    /**
     * Method to search for a camera by the product code.
     *
     * @param code the code to search for.
     * @return Camera object which matches the code.
     * @throws ProductNotFoundException if a camera was not found.
     */
    public Camera getCamera(String code) throws ProductNotFoundException {
        for (Camera c : cameras) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to search for a lens by the product code.
     *
     * @param code the code to search for.
     * @return Lens object which matches the code.
     * @throws ProductNotFoundException if a lens was not found.
     */
    public Lens getLens(String code) throws ProductNotFoundException {
        for (Lens l : lenses) {
            if (l.getCode().equals(code)) {
                return l;
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to get the stock level of a product from its product code.
     *
     * @param code the product code it get the stock for.
     * @return the stock level.
     * @throws ProductNotFoundException if the product was not found.
     */
    public int getStock(String code) throws ProductNotFoundException {
        for (Camera c : cameras) {
            if (c.getCode().equals(code)) {
                return c.getStock();
            }
        }
        for (Lens l : lenses) {
            if (l.getCode().equals(code)) {
                return l.getStock();
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to update a camera in the list. Pass in a camera object and it
     * will look for a camera with the same product code and update the rest of
     * the details.
     *
     * @param c the camera to be updated.
     * @throws clickitserver.ProductNotFoundException if the camera was not
     * found.
     */
    public void updataCamera(Camera c) throws ProductNotFoundException {
        for (int i = 0; i < cameras.size(); i++) {
            if (c.getCode().equals(cameras.get(i).getCode())) {
                cameras.set(i, c);
                return;
            }
        }
        throw new ProductNotFoundException(c.getCode());
    }

    /**
     * Method to update a lens in the list. Pass in a lens object and it will
     * look for a lens with the same product code and update the rest of the
     * details.
     *
     * @param l the lens to be updated.
     * @throws clickitserver.ProductNotFoundException if the lens was not found.
     */
    public void updateLens(Lens l) throws ProductNotFoundException {
        for (int i = 0; i < lenses.size(); i++) {
            if (l.getCode().equals(lenses.get(i).getCode())) {
                lenses.set(i, l);
                return;
            }
        }
        throw new ProductNotFoundException(l.getCode());
    }

    /**
     * Method to purchase a product.
     *
     * @param code the code of the product to be purchased.
     * @throws clickitserver.OutOfStockException if product is out of stock.
     * @throws clickitserver.ProductNotFoundException if product is not found.
     */
    public void purchaseProduct(String code) throws OutOfStockException, ProductNotFoundException {
        for (Camera c : cameras) {
            if (c.getCode().equals(code)) {
                c.purchase();
                return;
            }
        }
        for (Lens l : lenses) {
            if (l.getCode().equals(code)) {
                l.purchase();
                return;
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to increase the stock level of a product.
     *
     * @param code the code of the product to add stock to.
     * @param stock the stock to add to the product.
     * @throws clickitserver.ProductNotFoundException if product is not found.
     */
    public void increaceStock(String code, int stock) throws ProductNotFoundException {
        for (Camera c : cameras) {
            if (c.getCode().equals(code)) {
                c.increaceStock(stock);
                return;
            }
        }
        for (Lens l : lenses) {
            if (l.getCode().equals(code)) {
                l.increaseStock(stock);
                return;
            }
        }
        throw new ProductNotFoundException(code);
    }

    /**
     * Method to save the contents of the list to a file.
     */
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter("cameras.txt", "UTF-8")) {
            System.out.println("Starting to write to cameras file");
            for (int i = 0; i <= (cameras.size() - 1); i++) {
                writer.println(cameras.get(i).toCSV());
            }
            System.out.println("Writing to cameras file complete");
        } catch (IOException ex) {

        }
        try (PrintWriter writer = new PrintWriter("lenses.txt", "UTF-8")) {
            System.out.println("Starting to write to lenses file");
            for (int i = 0; i <= (lenses.size() - 1); i++) {
                writer.println(lenses.get(i).toCSV());
            }
            System.out.println("Writing to lenses file complete");
        } catch (IOException ex) {

        }
    }

    /**
     * Method to open a file and save it to the list.
     */
    public final void openFile() {
        File file = new File("cameras.txt");
        System.out.println("Starting to open cameras file");
        try {
            Scanner fileReader = new Scanner(file);

            if (fileReader.hasNextLine()) {
                do {
                    cameras.add(new Camera(fileReader.nextLine()));
                } while (fileReader.hasNextLine());
                System.out.println("Open cameras file complete");
            } else {
                System.out.println("Cameras file is empty");
            }
        } catch (IOException e) {
            try {
                boolean createNewFile = file.createNewFile();
            } catch (IOException ex) {
            }
        }
        file = new File("lenses.txt");
        System.out.println("Starting to open lenses file");
        try {
            Scanner fileReader = new Scanner(file);

            if (fileReader.hasNextLine()) {
                do {
                    lenses.add(new Lens(fileReader.nextLine()));
                } while (fileReader.hasNextLine());
                System.out.println("Open lenses file complete");
            } else {
                System.out.println("Lenses file is empty");
            }
        } catch (IOException e) {
            try {
                boolean createNewFile = file.createNewFile();
            } catch (IOException ex) {
            }
        }
    }
}
