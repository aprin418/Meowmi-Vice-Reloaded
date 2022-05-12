package com.meowmivice.game;

import com.meowmivice.game.cast.Location;
import com.meowmivice.game.cast.LocationsLoader;
import com.meowmivice.game.cast.Player;
import com.meowmivice.game.reader.TextParser;
import org.json.simple.parser.ParseException;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;


public class Locations extends JPanel  implements ActionListener{
    private LocationsLoader locLoader  = new LocationsLoader(); // loads from json
    private Map<String, Location> mapLocations = locLoader.load(); // creates a map of all the rooms
    Location currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());

    private static JTextArea displayText;
    private static JTextField commandInput;
    private static String text;

    //constants
    private static final String SMOKE_GREY_LINING = "#848884";
    private static final String GUNMETAL_GREY = "#818589";

    // directional buttons
    private JButton north;
    private JButton south;
    private JButton east;
    private JButton west;
    private JButton upstairs;
    private JButton downstairs;
    private JButton clues;

    //left-hand drop down menu
    private JMenuBar menu;

    //components for user command input
    private JButton enterButton;

    //player inventory
    private JList inventory;

    //separator
    private JSeparator bottomDivider;

    private JScrollPane scroll;


    public Locations() throws IOException, ParseException {
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
        bottomDivider = new JSeparator();
        commandInput = new JTextField ();
        displayText = new JTextArea (10, 5);

        // add file and help to menus
        menu.add(helpMenu);

        // clues button styling
        clues.setBackground(Color.WHITE);
        clues.setForeground(Color.MAGENTA);
        clues.setFocusPainted(false);
        clues.setBorder(null);

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
        soundsOption.addActionListener(this);
        quitOption.addActionListener(this);

       //override default frame layout and set background
        setLayout(null);
        setBackground(Color.black);

        //add menu components to frame
        add(menu);
        add(north);
        add(south);
        add(west);
        add(east);
        add(upstairs);
        add(downstairs);
        add(clues);
        add(enterButton);
        add(commandInput);
        add(bottomDivider);
        add(scroll);

        // set all component bounds
        north.setBounds(810, 500, 60, 30);
        south.setBounds(810, 560, 60, 30);
        east.setBounds(750, 530, 60, 30);
        west.setBounds(870, 530, 60, 30);
        upstairs.setBounds(950, 500, 70, 30);
        downstairs.setBounds(950, 560, 70, 30);
        clues.setBounds(580, 600, 100, 30);
        menu.setBounds(0, 0, 1160, 30);
        scroll.setBounds(30, 495, 450, 100);
        commandInput.setBounds(30, 620, 250, 30);
        enterButton.setBounds(250, 620, 100, 30);
        bottomDivider.setBounds(0, 450, 1100, 5);

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
                break;
            case "South":
                try {
                    TextParser.textParser("go south");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                break;
            case "East":
                try {
                    TextParser.textParser("go east");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                break;
            case "West":
                try {
                    TextParser.textParser("go west");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                break;
            case "Upstairs":
                try {
                    TextParser.textParser("go upstairs");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
                break;
            case "Downstairs":
                try {
                    TextParser.textParser("go downstairs");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                currentSpot = mapLocations.get(Player.getInstance().getCurrentLocation());
                textDisplayer(currentSpot.getDescription());
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
