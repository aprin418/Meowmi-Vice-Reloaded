package com.meowmivice.game;

import com.meowmivice.game.cast.Item;
import com.meowmivice.game.cast.Location;
import com.meowmivice.game.cast.NPC;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

class Clickables extends JPanel{
    static JLabel trash, bag, safe, box, envelope;

    Clickables() throws IOException {
        BufferedImage trashCan = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/index.jpg")));
        ImageIcon trashIcon = new ImageIcon(trashCan);
        Image trashImage = trashIcon.getImage();
        Image trashImageScaled = trashImage.getScaledInstance(100, 730,  Image.SCALE_DEFAULT);
        trash = new JLabel(new ImageIcon(trashImageScaled));
        trash.setBounds(100, 150, 200, 100);
    }

//    public static void itemClicked() throws IOException {
//        trash.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("clicked");
//            }
//        });
//    }


    public static Component showItems(Location currentSpot) {
        NPC currentNpc = currentSpot.getNpc(); // get currentNPC
        Item currentItem = currentSpot.getItem(); // get currentItem
        JLabel item = null;

        if (currentItem.getName().contains("trash")) {
            item = trash;
        }
        
        return item;
    }
}