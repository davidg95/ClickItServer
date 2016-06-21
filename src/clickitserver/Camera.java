/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import java.util.Scanner;

/**
 * A class of type <code>Camera</code> which stores details about a
 * <code>Camera</code>.
 *
 * @author David
 * @version 23/03/2015
 */
public class Camera {

    private String code;
    private String make;
    private String model;
    private double megapixles;
    private boolean full;
    private int stock;
    private double price;

    /**
     * Constructor method for the <code>Camera</code> class which creates an
     * instance of the <code>Camera</code> object.
     *
     * @param make the make of the <code>Camera</code> as a String.
     * @param model the model of the <code>Camera</code> as a String.
     * @param megapixles the megapixels of the <code>Camera</code> as a double.
     * @param ifFull a boolean value to state whether the <code>Camera</code> as
     * a full frame sensor or a crop, true for full and false for crop.
     * @param stock the current stock level of the <code>Camera</code> as an
     * int.
     * @param price the price of the <code>Camera</code> as a double.
     */
    public Camera(String make, String model, double megapixles, boolean ifFull, int stock, double price) {
        this.make = make;
        this.model = model;
        this.megapixles = megapixles;
        this.full = ifFull;
        this.stock = stock;
        this.price = price;
    }

    /**
     * Constructor method for the <code>Camera</code> object which creates an
     * instance of the <code>Camera</code> object.
     *
     * @param notepad a line from the notepad file as a String.
     */
    public Camera(String notepad) {
        Scanner in = new Scanner(notepad);

        in = in.useDelimiter(",");

        this.make = in.next();
        this.model = in.next();
        this.megapixles = Double.parseDouble(in.next());
        String sensor = in.next();
        this.full = !sensor.equals("CROP");
        this.stock = in.nextInt();
        this.price = in.nextDouble();
        this.code = in.next();
    }

    //Getter Methods
    /**
     * Gets the product code of the <code>Camera</code>.
     *
     * @return returns the product code of the <code>Camera</code>.
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Gets the make of the <code>Camera</code>.
     *
     * @return returns the make of the <code>Camera</code> as a String.
     */
    public String getMake() {
        return make;
    }

    /**
     * Gets the model of the <code>Camera</code>.
     *
     * @return returns the model of the <code>Camera</code> as a String.
     */
    public String getModel() {
        return model;
    }

    /**
     * Gets the megapixels of the <code>Camera</code>.
     *
     * @return returns the megapixels of the camera as a double.
     */
    public double getMegapixles() {
        return megapixles;
    }

    /**
     * Method to get whether the <code>Camera</code> is full frame or crop
     * sensor.
     *
     * @return returns true or false to indicate whether the <code>Camera</code>
     * is full frame or crop sensor. Returns true for full frame, false for
     * crop.
     */
    public boolean isFull() {
        return full;
    }

    /**
     * Method to return whether the sensor is full frame or crop sensor.
     *
     * @return returns a String value indicating whether the sensor is FULL or
     * CROP.
     */
    public String getSensor() {
        if (full) {
            return "FULL";
        }

        return "CROP";
    }

    /**
     * Gets the current stock level of the <code>Camera</code>.
     *
     * @return returns the current stock level of the <code>Camera</code> as an
     * int.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets the price of the <code>Camera</code>.
     *
     * @return returns the price of the camera as a double.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the product code of the <code>Camera</code>.
     *
     * @param code the product code of the <code>Camera</code> as a String.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the make of the <code>Camera</code>.
     *
     * @param make the value for the make as a String.
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Sets the model of the <code>Camera</code>.
     *
     * @param model the value for the model as a String.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Sets the megapixles of the <code>Camera</code>.
     *
     * @param megapixles the value for the megapixles as a double.
     */
    public void setMegapixles(double megapixles) {
        this.megapixles = megapixles;
    }

    /**
     * Sets whether the <code>Camera</code> is full frame or crop sensor.
     *
     * @param full true or false to indicate whether the <code>Camera</code> is
     * full frame or crop sensor. Set true for full or false for crop.
     */
    public void setFull(boolean full) {
        this.full = full;
    }

    /**
     * Sets the stock level of the <code>Camera</code>.
     *
     * @param stock the value for the stock level as an int.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets the price of the <code>Camera</code>.
     *
     * @param price the value for the price of the camera as a double.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method to increase the stock level of the <code>Camera</code> by the
     * amount given.
     *
     * @param stock the amount of stock to be added to the Cameras stock level.
     */
    public void increaceStock(int stock) {
        this.stock += stock;
        System.out.println("Stock of camera increace by " + stock);
    }

    /**
     * Method to purchase a <code>Camera</code>. It checks whether the
     * <code>Camera</code> is in stock or not before reducing stock level and
     * returns true or false to indicate whether the <code>Camera</code> has
     * been purchased.
     *
     * @throws clickitserver.OutOfStockException if the camera is out of stock
     */
    public void purchase() throws OutOfStockException {
        if (this.stock == 0) {
            throw new OutOfStockException(this.code);
        }
        this.stock--;
    }

    /**
     * Method to generate the product code.
     *
     * Code is generated by taking the first three letters from the make and
     * then adding 3 random numbers.
     */
    public void generateProductCode() {
        code = make.substring(0, 3) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10);
    }

    /**
     * Method to format output for saving to a notepad file.
     *
     * @return returns a String formatted for output to a notepad file.
     */
    public String toCSV() {
        return this.make + "," + this.model + "," + this.megapixles + "," + (this.full ? "FULL" : "CROP") + "," + this.stock + "," + this.price + "," + this.code;
    }

    /**
     * Method to output details of the <code>Camera</code> in a single line with
     * all the data separated by a single tab space.
     *
     * @return returns a String value of the details of the <code>Camera</code>
     * in a single line.
     */
    public String toList() {
        return this.make + "\t" + this.model + "\t" + this.megapixles + "\t" + (this.full ? "FULL" : "CROP") + "\t" + this.stock + "\t£" + this.price;
    }

    /**
     * ToString method which outputs details of the <code>Camera</code> as a
     * String.
     *
     * @return returns details of the <code>Camera</code> as a String.
     */
    @Override
    public String toString() {
        return "Product Code: " + this.code + "\nMake: " + this.make + "\nModel: " + this.model + "\nMegapixels: " + this.megapixles + "\nSensor: " + (this.full ? "FULL" : "CROP") + "\nStock: " + this.stock + "\nPrice: £" + this.price;
    }

}
