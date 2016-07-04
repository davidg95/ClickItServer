/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

import java.util.Scanner;

/**
 * A class of type <code>Lens</code> which stores details about a lens.
 *
 * @author David
 */
public class Lens {

    private String make;
    private int min_mm;
    private int max_mm;
    private double min_f;
    private double max_f;
    private boolean isFull;
    private boolean hasVR;
    private boolean isMacro;
    private boolean hasAF;
    private double price;
    private int stock;
    private String code;

    /**
     * Constructor for <code>Lens</code> which takes in make, model, minimum and
     * maximum focal lengths, minimum and maximum f stop values, what frame
     * format it is, if it has VR and macro, the price and the stock.
     *
     * @param make the make of the lens as a String.
     * @param min_mm the minimum focal length of the lens as an int.
     * @param max_mm the maximum focal length of the lens as an int.
     * @param min_f the minimum f stop of the lens as an int.
     * @param max_f the maximum f stop of the lens as an int.
     * @param isFull whether the lens is for full frame sensors or not.
     * @param hasVR if the lens has built in VR.
     * @param isMacro if the lens is a macro lens.
     * @param hasAF if the lens has AF.
     * @param price the price of the lens.
     * @param stock the stock level.
     */
    public Lens(String make, int min_mm, int max_mm, double min_f, double max_f, boolean isFull, boolean hasVR, boolean isMacro, boolean hasAF, double price, int stock) {
        this.make = make;
        this.min_mm = min_mm;
        this.max_mm = max_mm;
        this.min_f = min_f;
        this.max_f = max_f;
        this.isFull = isFull;
        this.hasVR = hasVR;
        this.isMacro = isMacro;
        this.hasAF = hasAF;
        this.price = price;
        this.stock = stock;
    }
    
    public Lens(String notepad){
        Scanner in = new Scanner(notepad);

        in = in.useDelimiter(",");

        this.make = in.next();
        this.min_mm = in.nextInt();
        this.max_mm = in.nextInt();
        this.min_f = in.nextInt();
        this.max_f = in.nextInt();
        this.isFull = in.next().equals("FULL");
        this.hasVR = in.next().equals("YES");
        this.isMacro = in.next().equals("YES");
        this.hasAF = in.next().equals("YES");
        this.price = Double.parseDouble(in.next());
        this.stock = in.nextInt();
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getMin_mm() {
        return min_mm;
    }

    public void setMin_mm(int min_mm) {
        this.min_mm = min_mm;
    }

    public int getMax_mm() {
        return max_mm;
    }

    public void setMax_mm(int max_mm) {
        this.max_mm = max_mm;
    }

    public double getMin_f() {
        return min_f;
    }

    public void setMin_f(double min_f) {
        this.min_f = min_f;
    }

    public double getMax_f() {
        return max_f;
    }

    public void setMax_f(double max_f) {
        this.max_f = max_f;
    }

    public boolean isIsFull() {
        return isFull;
    }

    public void setIsFull(boolean isFull) {
        this.isFull = isFull;
    }

    public boolean isHasVR() {
        return hasVR;
    }

    public void setHasVR(boolean hasVR) {
        this.hasVR = hasVR;
    }

    public boolean isIsMacro() {
        return isMacro;
    }

    public void setIsMacro(boolean isMacro) {
        this.isMacro = isMacro;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isHasAF() {
        return hasAF;
    }

    public void setHasAF(boolean hasAF) {
        this.hasAF = hasAF;
    }

    /**
     * Method to increase the lenses stock level.
     *
     * @param stock the amount of stock to add.
     */
    public void increaseStock(int stock) {
        this.stock += stock;
    }

    /**
     * Method to purchase the lens. Its stock level gets reduced by 1.
     *
     * @throws OutOfStockException if the lens is out of stock.
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
     * then adding 3 random numbers. An "L" is then added to the end to specify
     * it as a lens.
     */
    public void generateProductCode() {
        code = make.substring(0, 3) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + (int) (Math.random() * 10) + "L";
    }

    /**
     * Method to format output for saving to a notepad file.
     *
     * @return returns a String formatted for output to a notepad file.
     */
    public String toCSV() {
        return this.make + this.min_mm + "," + this.max_mm + "," + this.min_f + "," + this.max_f + "," + (this.isFull ? "FULL" : "CROP") + "," + (this.hasVR ? "YES" : "NO") + "," + (this.isMacro ? "YES" : "NO") + "," + (this.hasAF ? "YES" : "NO") + "," + this.price + "," + this.stock + "," + this.code;
    }

    /**
     * ToString method which outputs details of the <code>Lens</code> as a
     * String.
     *
     * @return returns details of the <code>Lens</code> as a String.
     */
    @Override
    public String toString() {
        String focal;
        if (this.min_mm == this.max_mm) {
            focal = "" + this.min_mm;
        } else {
            focal = min_mm + "-" + max_mm;
        }
        String fStop;
        if (this.min_f == this.max_f) {
            fStop = "" + min_f;
        } else {
            fStop = min_f + "-" + max_f;
        }
        return "Make: " + this.make + "Focal Length: " + focal + "mm\nF Stop: f/" + fStop + "\nFrame Format: " + (this.isFull ? "FULL" : "CROP") + "\nVR: " + (this.hasVR ? "YES" : "NO") + "\nMacro: " + (this.isMacro ? "YES" : "NO") + "\nAF: " + (this.hasAF ? "YES" : "NO") + "\nPrice: £" + this.price + "\nStock: " + this.stock + "\nProduct Code: " + this.code;
    }
}
