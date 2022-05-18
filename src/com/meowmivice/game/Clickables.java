package com.meowmivice.game;

import com.meowmivice.game.cast.Item;
import com.meowmivice.game.cast.Location;
import com.meowmivice.game.cast.NPC;
import com.meowmivice.game.cast.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;



public class Clickables extends JPanel{
     JLabel  bag, safe, box, envelope;


    Clickables() throws IOException {
//        Item currentItem = currentSpot.getItem();
//        System.out.println(currentItem.getName());
//        BufferedImage trashCan = ImageIO.read(Objects.requireNonNull(this.getClass().getResource(  "/bag.png")));

//        BufferedImage trashCan = ImageIO.read((this.getClass().getResource("/" + Locations.currentSpot.getItem().getName() + ".png")));
//        ImageIcon trashIcon = new ImageIcon(trashCan);
//        Image trashImage = trashIcon.getImage();
//        Image trashImageScaled = trashImage.getScaledInstance(100, 730,  Image.SCALE_DEFAULT);
////       imgBtn = new JButton("image",new ImageIcon(trashImageScaled));
//        imgBtn = new JButton("image",new ImageIcon(trashImageScaled));
//        imgBtn.setBackground(Color.BLACK);
//        imgBtn.setBounds(100, 150, 200, 100);
    }


//    public static void itemClicked() throws IOException {
//        trash.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("clicked");
//            }
//        });
//    }


//    public JButton showItems(Location currentSpot) throws IOException {
////        Location currentSpot = new Location();
//        NPC currentNpc = currentSpot.getNpc(); // get currentNPC
//        Item currentItem = currentSpot.getItem(); // get currentItem
//        JButton item = null;
//
//        JButton imgBtn;
//
//        BufferedImage trashCan = ImageIO.read((this.getClass().getResource("/" + Locations.currentSpot.getItem().getName() + ".png")));
//        ImageIcon trashIcon = new ImageIcon(trashCan);
////        Image trashImage = trashIcon.getImage();
////        Image trashImageScaled = trashImage.getScaledInstance(100, 730,  Image.SCALE_DEFAULT);
//////        imgBtn = new JButton("image",new ImageIcon(trashImageScaled));
////        imgBtn = new JButton("image",new ImageIcon(trashImageScaled));
////        imgBtn.setBackground(Color.BLACK);
////        imgBtn.setBounds(100, 150, 200, 100);
//
////        if (currentItem.getName().contains("trash")) {
////
////            item = trash;
////        }
//        item = imgBtn;
//
//        return item;
//    }
}