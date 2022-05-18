package com.meowmivice.game;

import com.meowmivice.game.cast.*;
import com.meowmivice.game.reader.Audio;
import com.meowmivice.game.reader.TextParser;
import org.json.simple.parser.ParseException;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;


public class GameScreen extends JPanel  implements ActionListener{
    // see line 325 for Clickable
    private static LocationsLoader locLoader; // loads from json

    static {
        try {
            locLoader = new LocationsLoader();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Location> mapLocations = locLoader.load(); // creates a map of all the rooms
    public static Location currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
    private Clickables click = new Clickables();

    private static JTextArea displayText;
    private static JTextField commandInput;
    private static String text;
    private static JLabel pop;
    private static JPanel popContainer;

    //constants
    private static final String SMOKE_GREY_LINING = "#848884";
    private static final String GUNMETAL_GREY = "#818589";

    // directional and volume control buttons
    private JButton northBtn;
    private JButton southBtn;
    private JButton eastBtn;
    private JButton westBtn;
    private JButton upstairsBtn;
    private JButton downstairsBtn;
    private JButton cluesBtn;
    private JButton volumeUpButton;
    private JButton volumeDownButton;
    private static JButton imgBtn;
    private JButton lookBtn;
    private JButton solveBtn;
    //left-hand drop down menu
    private JMenuBar menu;
    //player inventory
    private static JTextArea inventoryTextArea;
    //separator
    private JSeparator bottomDivider;
    //scrollable pane
    private JScrollPane scroll, scroll2;
    //image panel
    private JPanel imgPanel;
    //image label
    private  JLabel imgLabel;
    //room image icon
    private ImageIcon image;

    public GameScreen() throws Exception {
        //Help dropdown menu
        JMenu helpMenu = new JMenu ("Help"); //2nd dropdown header
        JMenuItem commandsOption = new JMenuItem ("Commands");
        JMenuItem soundsOption = new JMenuItem ("Toggle Sound");
        JMenuItem aboutOption = new JMenuItem ("Game Info");
        JMenuItem quitOption = new JMenuItem ("Quit");
        helpMenu.add(commandsOption);
        helpMenu.add(soundsOption);
        helpMenu.add(aboutOption);
        helpMenu.add(quitOption);

        //instantiate frame components
        menu = new JMenuBar();
        northBtn = new JButton("North");
        southBtn = new JButton("South");
        eastBtn = new JButton("East");
        westBtn = new JButton("West");
        upstairsBtn = new JButton("Upstairs");
        downstairsBtn = new JButton("Downstairs");
        cluesBtn = new JButton("Clues");
        inventoryTextArea = new JTextArea(10, 5);
        volumeUpButton = new JButton("Vol Up");
        volumeDownButton = new JButton("Vol Down");
        bottomDivider = new JSeparator();
        displayText = new JTextArea (10, 5);
        imgPanel = new JPanel();
        imgLabel = new JLabel();
        image = new ImageIcon(this.getClass().getClassLoader().getResource( currentSpot.getName() + ".png"));
        imgBtn = new JButton("IMAGE");
        lookBtn = new JButton("Look");
        solveBtn = new JButton("Solve");

        // add file and help to menus
        menu.add(helpMenu);

        // clues button styling
        cluesBtn.setBackground(Color.WHITE);
        cluesBtn.setForeground(Color.MAGENTA);
        cluesBtn.setFocusPainted(false);
        cluesBtn.setBorder(null);

        //look button styling
        lookBtn.setBackground(Color.WHITE);
        lookBtn.setForeground(Color.MAGENTA);
        lookBtn.setFocusPainted(false);
        lookBtn.setBorder(null);

        solveBtn.setBackground(Color.WHITE);
        solveBtn.setForeground(Color.MAGENTA);
        solveBtn.setFocusPainted(false);
        solveBtn.setBorder(null);

        //label image styling
        imgLabel.setIcon(image);

        //img panel styling
        imgPanel.setBackground(Color.black);

        // directional button styling
        northBtn.setBackground(Color.WHITE);
        northBtn.setForeground(Color.MAGENTA);
        northBtn.setFocusPainted(false);
        northBtn.setBorder(null);

        southBtn.setBackground(Color.WHITE);
        southBtn.setForeground(Color.MAGENTA);
        southBtn.setFocusPainted(false);
        southBtn.setBorder(null);;

        eastBtn.setBackground(Color.WHITE);
        eastBtn.setForeground(Color.CYAN);
        eastBtn.setFocusPainted(false);
        eastBtn.setBorder(null);

        westBtn.setBackground(Color.WHITE);
        westBtn.setForeground(Color.CYAN);
        westBtn.setFocusPainted(false);
        westBtn.setBorder(null);

        downstairsBtn.setBackground(Color.WHITE);
        downstairsBtn.setForeground(Color.CYAN);
        downstairsBtn.setFocusPainted(false);
        downstairsBtn.setBorder(null);

        upstairsBtn.setBackground(Color.WHITE);
        upstairsBtn.setForeground(Color.CYAN);
        upstairsBtn.setFocusPainted(false);
        upstairsBtn.setBorder(null);

        //img button styling
        imgBtn.setBackground(Color.BLACK);


        //Volume button styling
        volumeUpButton.setBackground(Color.WHITE);
        volumeDownButton.setBackground(Color.WHITE);

        volumeUpButton.setForeground(Color.MAGENTA);
        volumeDownButton.setForeground(Color.MAGENTA);

        volumeUpButton.setFocusPainted(false);
        volumeDownButton.setFocusPainted(false);

        volumeUpButton.setBorder(null);
        volumeDownButton.setBorder(null);

        // inventory text area styling
        inventoryTextArea.setBackground(Color.decode(GUNMETAL_GREY));
        inventoryTextArea.setForeground(Color.WHITE);

        //text display properties
        displayText.setEnabled (false);
        displayText.setBackground(Color.decode(GUNMETAL_GREY));
        displayText.setDisabledTextColor(Color.WHITE);
        displayText.setLineWrap(true);
        scroll = new JScrollPane(displayText); //enable scrollable text-box
        scroll2 = new JScrollPane(inventoryTextArea);
        scroll.setBorder(new LineBorder(Color.decode(SMOKE_GREY_LINING)));
        scroll2.setBorder(new LineBorder(Color.decode(SMOKE_GREY_LINING)));

        // separator at bottom of frame
        bottomDivider.setBackground(Color.decode(SMOKE_GREY_LINING)); //bottom line color

        //all button listeners
        northBtn.addActionListener(this);
        southBtn.addActionListener(this);
        eastBtn.addActionListener(this);
        westBtn.addActionListener(this);
        upstairsBtn.addActionListener(this);
        downstairsBtn.addActionListener(this);
        cluesBtn.addActionListener(this);
        volumeUpButton.addActionListener(this);
        volumeDownButton.addActionListener(this);
        soundsOption.addActionListener(this);
        quitOption.addActionListener(this);
        imgBtn.addActionListener(this);
        lookBtn.addActionListener(this);
        solveBtn.addActionListener(this);

       //override default frame layout and set background
        setLayout(null);
        setBackground(Color.black);

        //add label to img panel
        imgPanel.add(imgLabel);

        //add menu components to frame
        add(menu);
        add(northBtn);
        add(southBtn);
        add(westBtn);
        add(eastBtn);
        add(upstairsBtn);
        add(downstairsBtn);
        add(cluesBtn);
        add(click);
        add(volumeUpButton);
        add(volumeDownButton);
        add(bottomDivider);
        add(scroll);
        add(scroll2);
        add(imgPanel);
        add(imgBtn);
        add(lookBtn);
        add(solveBtn);
        imgBtn.setVisible(false);

        // set all component bounds
        northBtn.setBounds(810, 500, 60, 30);
        southBtn.setBounds(810, 560, 60, 30);
        eastBtn.setBounds(750, 530, 60, 30);
        westBtn.setBounds(870, 530, 60, 30);
        upstairsBtn.setBounds(950, 500, 70, 30);
        downstairsBtn.setBounds(950, 560, 70, 30);
        volumeDownButton.setBounds(750, 640, 80, 30);
        volumeUpButton.setBounds(850, 640, 80, 30);
        cluesBtn.setBounds(580, 600, 100, 30);
        menu.setBounds(0, 0, 1160, 30);
        scroll.setBounds(30, 495, 450, 100);
        bottomDivider.setBounds(0, 450, 1100, 5);
        imgPanel.setBounds(300,30,400,400);
        scroll2.setBounds(550, 495, 150, 100);
        imgBtn.setBounds(100, 150, 200, 100);
        lookBtn.setBounds(580, 650, 100, 30);
        solveBtn.setBounds(480, 650, 100, 30);

        //player info
        mapLocations();

    }


    public static JTextArea getInventoryTextArea() { return inventoryTextArea; }

    private static void seeClues() {
        inventoryTextArea.setText("");
        for (String clue: Player.getInstance().getClues().values()) {
            inventoryTextArea.append(clue + "\n");
        }
    }

    public static void removeImageButton() {
        Item currentItem = currentSpot.getItem();
        if (Player.getInstance().getInventory().contains(currentItem)) {
            imgBtn.setVisible(false);
        }
    }

    public void mapLocations() { textDisplayer(currentSpot.getDescription()); }

    //prints text to scroll box
    public static void textDisplayer(String displayText) {
        GameScreen.displayText.setText("");
        GameScreen.displayText.setText(displayText);
    }

    public static void showPopUp(String plug) { JOptionPane.showMessageDialog(MainFrame.frame, plug); }

    public ImageIcon roomImageIcon() {
        image = new ImageIcon(this.getClass().getClassLoader().getResource( currentSpot.getName() + ".png"));
        return image;
    }
    public ImageIcon itemImageIcon() {
        image = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource(  currentSpot.getItem().getName() + ".png")));
        return image;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command){
            case "North":
                try {
                    imgBtn.setVisible(false);
                    TextParser.textParser("go north");
                    currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "South":
                try {
                    imgBtn.setVisible(false);
                    TextParser.textParser("go south");
                    currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "East":
                try {
                    imgBtn.setVisible(false);
                    TextParser.textParser("go east");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "West":
                try {
                    imgBtn.setVisible(false);
                    TextParser.textParser("go west");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "Upstairs":
                try {
                    imgBtn.setVisible(false);
                    TextParser.textParser("go upstairs");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "Downstairs":
                try {
                    imgBtn.setVisible(false);
                    TextParser.textParser("go downstairs");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "Volume Up":
                try { Audio.increaseSoundVolume(); }
                catch (Exception ex) { ex.printStackTrace(); }
                break;
            case "Volume Down":
                try { Audio.lowerSoundVolume(); }
                catch (Exception ex) { ex.printStackTrace(); }
                break;
            case "Toggle Sound":
                try { Audio.toggleSound(); }
                catch (Exception ex) { ex.printStackTrace(); }
                break;
            case "Clues":
                seeClues();
                break;
            case "Look":
                try {
                    TextParser.textParser("look");
                    currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                    imgBtn.setIcon(itemImageIcon());
                    imgBtn.setVisible(true);
                } catch (Exception ex) { ex.printStackTrace(); }
                break;
            case "Solve":
                try {
                    TextParser.textParser("solve");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "IMAGE":
                try {
                    Item currentItem = currentSpot.getItem();
                    TextParser.textParser("get " + currentItem.getName());
                    showPopUp(currentItem.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                break;
            case "Quit":
                System.exit(0);
                break;
            default:
        }
    }
}
