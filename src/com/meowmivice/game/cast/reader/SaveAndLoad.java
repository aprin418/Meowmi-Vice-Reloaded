package com.meowmivice.game.cast.reader;

import com.meowmivice.game.cast.cast.Player;

import java.io.*;

public class SaveAndLoad {
    static Player player;

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