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
 * A class which stores a list of objects of type Camera.
 *
 * @author David
 * @version 23/03/2015
 */
public class CameraList {

    protected List<Camera> cameras;

    /**
     * Constructor method for the <code>CameraList</code> class which creates an
     * instance of the CameraList object. It creates an ArrayList of type Camera
     * and then calls the method to read from the file.
     */
    public CameraList() {
        cameras = new ArrayList<>();

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

    /**
     * Sets the ArrayList of type Camera.
     *
     * @param cameras the new ArrayList of type Camera.
     */
    public void setCameras(ArrayList<Camera> cameras) {
        this.cameras = cameras;
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
        cameras.add(c);
        System.out.println(c);
    }

    /**
     * Method to remove a camera from the list.
     *
     * @param camera the camera to be removed from the list as a Camera.
     * @throws clickitserver.CameraNotFoundException if the product code is not
     * found.
     */
    public void removeCamera(Camera camera) throws CameraNotFoundException {
        for (int i = 0; i < cameras.size(); i++) {
            if (cameras.get(i).equals(camera)) {
                cameras.remove(i);
                System.out.println("Camera removed from list");
                return;
            }
        }
        throw new CameraNotFoundException(camera.getCode());
    }

    /**
     * Method to remove a camera from the list.
     *
     * @param i the location of the camera to remove.
     */
    public void removeCamera(int i) {
        cameras.remove(i);
    }

    /**
     * Method to remove a camera from the list.
     *
     * @param code the product code of the camera to remove
     * @throws clickitserver.CameraNotFoundException if the product code is not
     * found.
     */
    public void removeCamera(String code) throws CameraNotFoundException {
        for (int i = 0; i < cameras.size(); i++) {
            if (cameras.get(i).getCode().equals(code)) {
                cameras.remove(i);
                System.out.println("Camera removed from list");
                return;
            }
        }
        throw new CameraNotFoundException(code);
    }

    /**
     * Method to return the amount of cameras stored in the list.
     *
     * @return returns the amount of cameras stored in the list as an int.
     */
    public int size() {
        return cameras.size();
    }

    /**
     * Method to return the contents of the CameraList as an Object.
     *
     * @return returns the contents of the CameraList as an Object.
     */
    public Object getArray() {
        return cameras.toArray();
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
     * Method to search for a camera by the product code.
     *
     * @param code the code to search for.
     * @return Camera object which matches the code.
     * @throws CameraNotFoundException if a camera was not found.
     */
    public Camera getCamera(String code) throws CameraNotFoundException {
        for (Camera c : cameras) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }
        throw new CameraNotFoundException(code);
    }

    /**
     * Method to update a camera in the list. Pass in a camera object and it
     * will look for a camera with the same product code and update the rest of
     * the details.
     *
     * @param c the camera to be updated.
     * @throws clickitserver.CameraNotFoundException if the camera was not
     * found.
     */
    public void updataCamera(Camera c) throws CameraNotFoundException {
        for (int i = 0; i < cameras.size(); i++) {
            if (c.getCode().equals(cameras.get(i).getCode())) {
                cameras.set(i, c);
                return;
            }
        }
        throw new CameraNotFoundException(c.getCode());
    }

    /**
     * Method to purchase a camera.
     *
     * @param code the code of the camera to be purchased.
     * @throws clickitserver.OutOfStockException if camera is out of stock.
     * @throws clickitserver.CameraNotFoundException if camera is not found.
     */
    public void purchaseCamera(String code) throws OutOfStockException, CameraNotFoundException {
        for (Camera c : cameras) {
            if (c.getCode().equals(code)) {
                c.purchase();
                return;
            }
        }
        throw new CameraNotFoundException(code);
    }

    /**
     * Method to purchase a camera.
     *
     * @param code the code of the camera to be purchased.
     * @param stock the stock to add to the camera.
     * @throws clickitserver.CameraNotFoundException if camera is not found.
     */
    public void increaceStock(String code, int stock) throws CameraNotFoundException {
        for (Camera c : cameras) {
            if (c.getCode().equals(code)) {
                c.increaceStock(stock);
                return;
            }
        }

        throw new CameraNotFoundException(code);
    }

    /**
     * Method to save the contents of the list to a file.
     */
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter("cameras.txt", "UTF-8")) {
            System.out.println("Starting to write to file");
            for (int i = 0; i <= (cameras.size() - 1); i++) {
                writer.println(cameras.get(i).writeToFile());
                System.out.println("Camera written to file");
            }
            System.out.println("Writing to file complete");
        } catch (IOException ex) {

        }
    }

    /**
     * Method to open a file and save it to the list.
     */
    public final void openFile() {
        File file = new File("cameras.txt");
        System.out.println("Starting to open file");
        try {
            Scanner fileReader = new Scanner(file);

            if (fileReader.hasNextLine()) {
                do {
                    cameras.add(new Camera(fileReader.nextLine()));
                } while (fileReader.hasNextLine());
                System.out.println("Open file complete");
            } else {
                System.out.println("File is empty");
            }
        } catch (IOException e) {
            try {
                boolean createNewFile = file.createNewFile();
            } catch (IOException ex) {
            }
        }
    }
}
