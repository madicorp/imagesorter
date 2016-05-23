/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package imagesorters;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author sega
 */
public class Configuration {
    
    public static Properties properties = new Properties();
    
    public void saveConfiguration(String key, String value){
        
        try{
        properties.setProperty(key, value);
        properties.store(new FileOutputStream("settings.config"), null);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public String loadConfiguration(String key){
        String value ="";
        
          try{
            properties.load(new FileInputStream("settings.config"));
            value = properties.getProperty(key);
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        return value;
    }
    
}
