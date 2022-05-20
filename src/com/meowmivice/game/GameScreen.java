package com.meowmivice.game;

import com.meowmivice.game.cast.*;
import com.meowmivice.game.controller.Game;
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

import static java.awt.Transparency.TRANSLUCENT;


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
    private JButton northBtn,southBtn, eastBtn, westBtn, upstairsBtn, downstairsBtn, cluesBtn, volumeUpButton, volumeDownButton, itemImgBtn, lookBtn, solveBtn, npcImgBtn;
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
        image = new ImageIcon(Objects.requireNonNull(this.getClass().getResource( "/" + currentSpot.getName()+".png")));
        itemImgBtn = new JButton("IMAGE");
        npcImgBtn = new JButton("NPC");
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
        itemImgBtn.setMargin(new Insets(0,0,0,0));

        npcImgBtn.setMargin(new Insets(0,0,0,0));

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
        itemImgBtn.addActionListener(this);
        npcImgBtn.addActionListener(this);
        lookBtn.addActionListener(this);
        solveBtn.addActionListener(this);
        commandsOption.addActionListener(this);
        soundsOption.addActionListener(this);
        quitOption.addActionListener(this);
        aboutOption.addActionListener(this);

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
        add(lookBtn);
        add(solveBtn);

        // set all component bounds
        northBtn.setBounds(860, 550, 60, 30);
        southBtn.setBounds(860, 610, 60, 30);
        eastBtn.setBounds(800, 580, 60, 30);
        westBtn.setBounds(920, 580, 60, 30);
        upstairsBtn.setBounds(1000, 550, 70, 30);
        downstairsBtn.setBounds(1000, 610, 70, 30);
        volumeDownButton.setBounds(900, 690, 80, 30);
        volumeUpButton.setBounds(1000, 690, 80, 30);
        cluesBtn.setBounds(580, 650, 100, 30);
        menu.setBounds(0, 0, 1160, 30);
        scroll.setBounds(15, 545, 400, 100);
        bottomDivider.setBounds(0, 530, 1100, 5);
        imgPanel.setBounds(0,20,1100,510);
        scroll2.setBounds(450, 545, 340, 100);
        itemImgBtn.setBounds(50, 150, 95, 120);
        npcImgBtn.setBounds(810, 150, 95, 120);
        lookBtn.setBounds(190, 650, 100, 30);
        solveBtn.setBounds(580, 690, 100, 30);
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
    public ImageIcon npcImageIcon() {
        NPC currentNPC = currentSpot.getNpc();
        image = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource(  currentNPC.getName() + ".png")));
        return image;
    }



    public void itemAndNPCChecker() {
        Item currentItem = currentSpot.getItem();
        NPC currentNPC = currentSpot.getNpc();
        if (currentItem== null) {
            remove(itemImgBtn);
        }else{
            add(itemImgBtn);
            itemImgBtn.repaint();
            itemImgBtn.setIcon(itemImageIcon());
            itemImgBtn.setVisible(true);
//            itemImgBtn.repaint();
        }

        if (currentNPC==null){
            remove(npcImgBtn);
        }else{
            add(npcImgBtn);
            npcImgBtn.repaint();
            npcImgBtn.setIcon(npcImageIcon());
            npcImgBtn.setVisible(true);
//            npcImgBtn.repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command){
            case "North":
                try {
                    remove(itemImgBtn);
                    remove(npcImgBtn);
//                    itemImgBtn.setVisible(false);
//                    npcImgBtn.setVisible(false);
                    TextParser.textParser("go north");
                    currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "South":
                try {
                    remove(itemImgBtn);
                    remove(npcImgBtn);
//                    itemImgBtn.setVisible(false);
//                    npcImgBtn.setVisible(false);
                    TextParser.textParser("go south");
                    currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "East":
                try {
                    remove(itemImgBtn);
                    remove(npcImgBtn);
//                    itemImgBtn.setVisible(false);
//                    npcImgBtn.setVisible(false);
                    TextParser.textParser("go east");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "West":
                try {
                    remove(itemImgBtn);
                    remove(npcImgBtn);
//                    itemImgBtn.setVisible(false);
//                    npcImgBtn.setVisible(false);
                    TextParser.textParser("go west");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "Upstairs":
                try {
                    remove(itemImgBtn);
                    remove(npcImgBtn);
//                    itemImgBtn.setVisible(false);
//                    npcImgBtn.setVisible(false);
                    TextParser.textParser("go upstairs");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "Downstairs":
                try {
                    remove(itemImgBtn);
                    remove(npcImgBtn);
//                    itemImgBtn.setVisible(false);
//                    npcImgBtn.setVisible(false);
                    TextParser.textParser("go downstairs");
                    displayText.setText(currentSpot.getDescription());
                } catch (Exception ex) { ex.printStackTrace(); }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                imgLabel.setIcon(roomImageIcon());
                break;
            case "Vol Up":
                try { Audio.increaseSoundVolume(); }
                catch (Exception ex) { ex.printStackTrace(); }
                break;
            case "Vol Down":
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
                    itemAndNPCChecker();
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
            case "NPC":
                try {
                    TextParser.textParser("talk npc");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                break;
            case "Commands":
                Game.getCommands();
                break;
            case "Game Info":
                Game.gameInfo();
                break;
            case "Quit":
                System.exit(0);
                break;
            default:
        }
    }
}
