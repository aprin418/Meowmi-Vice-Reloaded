package com.meowmivice.game;

import com.apps.util.Prompter;
import com.meowmivice.game.controller.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Splash extends JPanel implements ActionListener {
    JLabel label;
    JButton startButton;
    Font normalFont = new Font ("Times New Roman", Font.PLAIN, 30);
    Game game = new Game(new Prompter(new Scanner(System.in)));

    public Splash () throws Exception {
        setPreferredSize(new Dimension(1094,  730));
        setLayout(null);
        setBackground(Color.black);
        BufferedImage img = ImageIO.read(new File("resources/Images/splash.jpg"));
        ImageIcon imageIcon = new ImageIcon(img);
        Image image = imageIcon.getImage();
        Image img2 = image.getScaledInstance(1094, 730,  Image.SCALE_DEFAULT);
        label = new JLabel( new ImageIcon(img2));
        label.setBounds(0,0,1094, 730);
        add(label);

        startButton = new JButton("START");
        startButton.setOpaque(true);
        startButton.setForeground(Color.MAGENTA);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setBounds (450, 500, 200, 30);
        startButton.setFont(normalFont);
        startButton.addActionListener(this);

        add(startButton);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        if(command.equalsIgnoreCase("start")){
                try {
                    game.execute();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
    }

}