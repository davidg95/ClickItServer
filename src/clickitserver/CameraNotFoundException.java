/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clickitserver;

/**
 * CameraNotFound Exception
 *
 * @author David
 */
public class CameraNotFoundException extends Exception {
    
    private final String code;
    
    public CameraNotFoundException(String code){
        this.code = code;
    }
    
    @Override
    public String getMessage(){
        return code + " not found";
    }
    
    @Override
    public String toString(){
        return "Exception: " + code + " not found";
    }
}
