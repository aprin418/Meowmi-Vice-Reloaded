package com.meowmivice.game;

import com.meowmivice.game.cast.Location;
import com.meowmivice.game.cast.LocationsLoader;
import com.meowmivice.game.cast.Player;
import com.meowmivice.game.controller.Game;
import com.meowmivice.game.logic.Logic;
import com.meowmivice.game.reader.TextParser;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Time;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class Locations extends JPanel  implements ActionListener{
    private LocationsLoader locLoader  = new LocationsLoader(); // loads from json
    private Map<String, Location> mapLocations = locLoader.load(); // creates a map of all the rooms
    private Location currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());

    private static JTextArea displayText;
    private static JTextField commandInput;
    private static String text;
    private static JLabel pop;
    private static JPanel popContainer;

    //constants
    private static final String SMOKE_GREY_LINING = "#848884";
    private static final String GUNMETAL_GREY = "#818589";

    // directional and volume control buttons
    private JButton north, south, east, west, upstairs, downstairs, clues, volumeUpButton, volumeDownButton;

    //left-hand drop down menu
    private JMenuBar menu;

    //components for user command input
    private JButton enterButton;

    //player inventory
    private JList inventory;

    //separator
    private JSeparator bottomDivider;

    private JScrollPane scroll;

    private JPanel imgPanel;

    private  JLabel imgLabel;

    //room image icon
    private ImageIcon image;



    public Locations() throws Exception {
        //Help dropdown menu
        JMenu helpMenu = new JMenu ("Help"); //2nd dropdown header
        JMenuItem commandsOption = new JMenuItem ("Commands");
        JMenuItem soundsOption = new JMenuItem ("Sound");
        JMenuItem aboutOption = new JMenuItem ("Game Info");
        JMenuItem quitOption = new JMenuItem ("Quit");
        helpMenu.add(commandsOption);
        helpMenu.add(soundsOption);
        helpMenu.add(aboutOption);
        helpMenu.add(quitOption);

        //instantiate frame components
        menu = new JMenuBar();
        north = new JButton("North");
        south = new JButton("South");
        east = new JButton("East");
        west = new JButton("West");
        upstairs = new JButton("Upstairs");
        downstairs = new JButton("Downstairs");
        clues = new JButton("Clues");
        enterButton = new JButton ("ENTER");
        volumeUpButton = new JButton("Volume Up");
        volumeDownButton = new JButton("Volume Down");
        bottomDivider = new JSeparator();
        commandInput = new JTextField ();
        displayText = new JTextArea (10, 5);
        imgPanel = new JPanel();
        imgLabel = new JLabel();
        image = new ImageIcon(this.getClass().getClassLoader().getResource( currentSpot.getName() + ".png"));

        // add file and help to menus
        menu.add(helpMenu);

        // clues button styling
        clues.setBackground(Color.WHITE);
        clues.setForeground(Color.MAGENTA);
        clues.setFocusPainted(false);
        clues.setBorder(null);

        //label image styling
        imgLabel.setIcon(image);

        //img panel styling
        imgPanel.setBackground(Color.black);

        // directional button styling
        north.setBackground(Color.WHITE);
        north.setForeground(Color.MAGENTA);
        north.setFocusPainted(false);
        north.setBorder(null);

        south.setBackground(Color.WHITE);
        south.setForeground(Color.MAGENTA);
        south.setFocusPainted(false);
        south.setBorder(null);;

        east.setBackground(Color.WHITE);
        east.setForeground(Color.CYAN);
        east.setFocusPainted(false);
        east.setBorder(null);

        west.setBackground(Color.WHITE);
        west.setForeground(Color.CYAN);
        west.setFocusPainted(false);
        west.setBorder(null);

        downstairs.setBackground(Color.WHITE);
        downstairs.setForeground(Color.CYAN);
        downstairs.setFocusPainted(false);
        downstairs.setBorder(null);

        upstairs.setBackground(Color.WHITE);
        upstairs.setForeground(Color.CYAN);
        upstairs.setFocusPainted(false);
        upstairs.setBorder(null);

        //Volume button styling
        volumeUpButton.setBackground(Color.WHITE);
        volumeDownButton.setBackground(Color.WHITE);

        volumeUpButton.setForeground(Color.MAGENTA);
        volumeDownButton.setForeground(Color.MAGENTA);

        volumeUpButton.setFocusPainted(false);
        volumeDownButton.setFocusPainted(false);

        volumeUpButton.setBorder(null);
        volumeDownButton.setBorder(null);

        // enter button styling
        enterButton.setBackground(Color.WHITE);
        enterButton.setForeground(Color.MAGENTA);
        enterButton.setFocusPainted(false);
        enterButton.setBorder(null);

        //input command box styling
        commandInput.setBackground(Color.decode(GUNMETAL_GREY));
        commandInput.setForeground(Color.WHITE);
        commandInput.setBorder(new LineBorder(Color.decode(SMOKE_GREY_LINING)));
        commandInput.setCaretColor(Color.WHITE);

        //text display properties
        displayText.setEnabled (false);
        displayText.setBackground(Color.decode(GUNMETAL_GREY));
        displayText.setDisabledTextColor(Color.WHITE);
        displayText.setLineWrap(true);
        scroll = new JScrollPane(displayText); //enable scrollable text-box
        scroll.setBorder(new LineBorder(Color.decode(SMOKE_GREY_LINING)));

        // separator at bottom of frame
        bottomDivider.setBackground(Color.decode(SMOKE_GREY_LINING)); //bottom line color

        //all button listeners
        enterButton.addActionListener(this);
        north.addActionListener(this);
        south.addActionListener(this);
        east.addActionListener(this);
        west.addActionListener(this);
        upstairs.addActionListener(this);
        downstairs.addActionListener(this);
        commandInput.addActionListener(this);
        volumeUpButton.addActionListener(this);
        volumeDownButton.addActionListener(this);
        soundsOption.addActionListener(this);
        quitOption.addActionListener(this);

       //override default frame layout and set background
        setLayout(null);
        setBackground(Color.black);

        //add label to img panel
        imgPanel.add(imgLabel);

        //add menu components to frame
        add(menu);
        add(north);
        add(south);
        add(west);
        add(east);
        add(upstairs);
        add(downstairs);
        add(clues);
        add(volumeUpButton);
        add(volumeDownButton);
        add(enterButton);
        add(commandInput);
        add(bottomDivider);
        add(scroll);
        add(imgPanel);

        // set all component bounds
        north.setBounds(810, 500, 60, 30);
        south.setBounds(810, 560, 60, 30);
        east.setBounds(750, 530, 60, 30);
        west.setBounds(870, 530, 60, 30);
        upstairs.setBounds(950, 500, 70, 30);
        downstairs.setBounds(950, 560, 70, 30);
        volumeDownButton.setBounds(750, 640, 80, 30);
        volumeUpButton.setBounds(850, 640, 80, 30);
        clues.setBounds(580, 600, 100, 30);
        menu.setBounds(0, 0, 1160, 30);
        scroll.setBounds(30, 495, 450, 100);
        commandInput.setBounds(30, 620, 250, 30);
        enterButton.setBounds(250, 620, 100, 30);
        bottomDivider.setBounds(0, 450, 1100, 5);
        imgPanel.setBounds(300,30,400,400);

        //player info
        mapLocations();
        seeInventory();
    }

    public void seeInventory(){
        if (inventory != null) {
            remove(inventory);
        }
        inventory = new JList(Player.getInstance().getInventory().toArray());
        inventory.setBackground(Color.decode(GUNMETAL_GREY));
        inventory.setBounds(550, 495, 150, 100);
        inventory.setForeground(Color.WHITE);
        add(inventory);
        revalidate();
        repaint();
    }

    public void mapLocations() {
        //current spot is not changing. only have access to initial east and north directions from
        //kitchen start point
        textDisplayer(currentSpot.getDescription());
    }

    //parses text input
    public static void textParser() throws Exception {
        text = commandInput.getText();
        TextParser.textParser(text);
        commandInput.setText("");
    }

    //prints text to scroll box
    public static void textDisplayer(String displayText){
        Locations.displayText.setText("");
        Locations.displayText.setText(displayText);
    }

    //displays images
//    public JLabel imageReader() throws IOException {
//        BufferedImage myPicture = ImageIO.read(this.getClass().getResource("/Kitchen.png"));
//        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
//        picLabel.setBackground(Color.WHITE);
//        BufferedImage img = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/kitchen.png")));
//        ImageIcon imageIcon = new ImageIcon(img);
//        Image image = imageIcon.getImage();
//        Image img2 = image.getScaledInstance(400, 400,  Image.SCALE_DEFAULT);
//        label = new JLabel( new ImageIcon(img2));
//        label.setBounds(0,0,1094, 730);
//        return label;
//    }

    public static void showPopUp(String plug) throws InterruptedException {
//        JPanel popContainer = new JPanel();
//        popContainer.setBackground(Color.MAGENTA);
//        pop = new JLabel(plug);
//        pop.setForeground(Color.BLACK);
//        popContainer.add(pop);
//        PopupFactory msg = new PopupFactory();
//        Popup p = msg.getPopup(MainFrame.frame, popContainer, 450, 100);
//        p.show();

        JOptionPane.showMessageDialog(MainFrame.frame, plug);
    }

    public ImageIcon imageIcon() {
        image = new ImageIcon(this.getClass().getClassLoader().getResource( currentSpot.getName() + ".png"));
        return image;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == commandInput) {
            try {
                textParser();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        String command = e.getActionCommand();
        switch(command){
            case "North":
                try {
                    TextParser.textParser("go north");


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(imageIcon());
                break;
            case "South":
                try {
                    TextParser.textParser("go south");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(imageIcon());
                break;
            case "East":
                try {
                    TextParser.textParser("go east");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(imageIcon());
                break;
            case "West":
                try {
                    TextParser.textParser("go west");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(imageIcon());
                break;
            case "Upstairs":
                try {
                    TextParser.textParser("go upstairs");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(imageIcon());
                break;
            case "Downstairs":
                try {
                    TextParser.textParser("go downstairs");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(imageIcon());
                break;
            case "ENTER":
                try {
                    textParser();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Quit":
                System.exit(0);
                break;
            default:
        }
    }

}
