/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import java.util.ArrayList;
import java.util.List;

/**
 * CameraData object which stores details about all the cameras.
 *
 * @author David
 */
public class CameraData {

    private final List<Camera> cameras;

    /**
     * Constructor which initialises the List.
     */
    public CameraData() {
        cameras = new ArrayList<>();
    }

    /**
     * Method to add a new camera to the list.
     *
     * @param c The new camera to be added.
     */
    public void addCamera(Camera c) {
        cameras.add(c);
    }

    /**
     * Method to update a camera in the list.
     *
     * @param c the camera to be updated.
     */
    public void updataCamera(Camera c) {
        for (int i = 0; i < cameras.size(); i++) {
            if (c.getCode().equals(cameras.get(i).getCode())) {
                cameras.set(i, c);
            }
        }
    }

    /**
     * Method to remove a camera from the list.
     *
     * @param code the product code of the camera to be removed.
     */
    public void removeCamera(String code) {
        for (int i = 0; i < cameras.size(); i++) {
            if (cameras.get(i).getCode().equals(code)) {
                cameras.remove(i);
            }
        }
    }

    /**
     * Method to get the stock level of a camera.
     *
     * @param code the code to get the stock level for.
     * @return the stock level as a double.
     */
    public int getStockLevel(String code) {
        for (Camera c : cameras) {
            if (c.getCode().equals(code)) {
                return c.getStock();
            }
        }
        return 0;
    }
    
    /**
     * Method to purchase a camera.
     * 
     * @param code the code of the camera to be purchased.
     * @throws clickitserver.OutOfStockException if the camera is out of stock.
     */
    public void purchaseCamera(String code) throws OutOfStockException{
        for(Camera c: cameras){
            if(c.getCode().equals(code)){
                c.purchase();
            }
        }
    }

    public void updataDatabase() {

    }
}
