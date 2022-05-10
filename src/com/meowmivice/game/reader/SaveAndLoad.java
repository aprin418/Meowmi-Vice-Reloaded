/*
Save and load methods are stubbed out but do not work. The save creates a saved file but nothing is loaded.
 */
package com.meowmivice.game.reader;

import com.meowmivice.game.cast.Player;

import java.io.*;

public class SaveAndLoad {
    static Player player;

    //Creates a file called "SavedGame"
    // ObjectOutputStream writes primitive data types and graphs of Java objects to an OutputStream.
    // writeObject method writes the byte stream in physical location.
    // Flushes the output stream and forces any buffered output bytes to be written out.
    // Then OutPutStream is closed and output that game was saved is given
    public static void save(){
        try {
            FileOutputStream fos = new FileOutputStream("SavedGame");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(player);
            oos.flush();
            oos.close();
            System.out.println("Game saved...");
        }catch (IOException e) {
            System.out.println("Game could not be saved...");
        }
    }

    // Loads the file "SavedGame"
    // ObjectInputStream ensures that the types of all objects in the graph created from the stream match the classes present
    // readObject method is used to read byte stream from physical location and type cast to required class.
    //Closes the input stream and outputs that the game was loaded
    public static void load(){
        try {
            FileInputStream fis = new FileInputStream("SavedGame");
            ObjectInputStream ois = new ObjectInputStream(fis);
            player = (Player) ois.readObject();
            ois.close();
            System.out.println("Game loaded...");
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Game could not be loaded...");
        }
    }
}