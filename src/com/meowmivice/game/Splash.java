package com.meowmivice.game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Objects;


public class Splash extends JPanel implements ActionListener {
    JLabel label;
    JButton startButton;
    Font normalFont = new Font ("Times New Roman", Font.PLAIN, 30);

    public Splash () throws Exception {
        setPreferredSize(new Dimension(1094,  730));
        setLayout(null);
        setBackground(Color.black);
        BufferedImage img = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/splash.jpg")));
        ImageIcon imageIcon = new ImageIcon(img);
        Image image = imageIcon.getImage();
        Image img2 = image.getScaledInstance(1094, 730,  Image.SCALE_DEFAULT);
        label = new JLabel( new ImageIcon(img2));
        label.setBounds(0,0,1094, 730);
        add(label);

        // INSTANTIATE START BUTTON
        startButton = new JButton("START");
        startButton.setOpaque(true); // ALLOWS US TO SEE BUTTON
        startButton.setForeground(Color.MAGENTA); // SET COLOR ON THE BUTTON
        startButton.setFocusPainted(false); // PREVENTS THE BUTTON OUTLINE FROM BEING PAINTED
        startButton.setBorderPainted(false); // PREVENTS PAINTED BORDER ON BUTTON
        startButton.setBounds (450, 500, 200, 30);
        startButton.setFont(normalFont);
        startButton.addActionListener(this);
        add(startButton);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();
        if(command.equalsIgnoreCase("start")){
                try {
                    MainFrame.clearContent();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
    }

}