// version 0.13.0.4.22.12.45

import javax.security.auth.Subject;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class scheduleMaker extends JFrame {
    final int sizeX = 1400;
    final int sizeY = 800;
    //TODO remove global variables, where necessary
    String location = new String();
    LinkedList<String> subjects = new LinkedList<String>();
    JTextField importField = new JTextField();
    File file;
    JLabel[] wDays;
    String[][] schedule = new String[5][8];
    JButton save;
    JButton importButton;
    JLabel importLabel;
    JTextArea[][] table = new JTextArea[5][8];
    File config = new File("src/config.txt");


    public scheduleMaker(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        buildScheduleMaker();

    }


    private void createConfig() {
        try {

            FileWriter fw = new FileWriter(config);
            fw.write("Schedule Path: " + importField.getText() + "\n");
            System.out.println("Wrote in config : " + importField.getText() );
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something wrong with path");
        }

    }


   //Loading schedule file from a cfg path
    private void loadSchedule(File config) {
        System.out.println("Started loading schedule, ln51");
        System.out.println("Using config at location: " + config.getPath());

        String line = new String();
        try {
            Scanner sc = new Scanner(config);
            location = sc.nextLine();
            sc.close();
            sc = new Scanner(location);
             sc.skip("Schedule Path: ");
             location = sc.nextLine();
            importField.setText(location);
            System.out.println("The location of the schedule to load is " + location);
            sc.close();
            File f = new File(location);
            sc = new Scanner(f);
            for (int p = 0; sc.hasNextLine(); p++) {
                line = sc.nextLine();
                System.out.println("The line at the moment is " + p + "." + line);
                String[] split_array = line.split("\\d\\.");
                System.out.println("split_array is " + Arrays.toString(split_array));
                for (int k = 0; k + 1 < split_array.length; k++) {
                    table[p][k].setText(split_array[k + 1].trim());
                    System.out.println("Table " + p + ":" + k + " is" + table[p][k].getText());
                }
            }
        } catch (IOException e) {

        }
    }



    //TODO: close FileWriter, ...Reader, etc.
    //TODO: Make Font size And design of export Table.
    //TODO: Exception management
    private void saveSchedule(JTextArea[][] t) throws IOException {
        for (int p = 0; p < 5; p++) {
            for (int k = 0; k < 8; k++) {
                schedule[p][k] = t[p][k].getText();

            }

        }
        file = new File(importField.getText());
        if (!file.exists()) {
            file.createNewFile();
        }

        if (!config.exists()) {
            config.createNewFile();
            createConfig();
            System.out.println("Created config ln108");
            //TODO Writes in config every time save is pressed
            //Some problem test
        }else if (importField.getText()!="" && !importField.getText().equals(location)){
            createConfig();

        }

        try {
            FileWriter fw = new FileWriter(file, false);

            for (int p = 0; p < 5; p++) {
                fw.write(wDays[p].getText() + ":" + "  ");
                for (int k = 0, m=0; k < 8; k++) {
                    fw.write(k + 1 + "." + schedule[p][k] + " ");
                    // Sorting subjects
                    subjects.add(schedule[p][k]);
                    for(int l = 0; l< subjects.size()-1; l++){
                        //System.out.println("Checking if " + subjects.getLast() +" == " +subjects.get(l));
                        if(subjects.getLast().equals(subjects.get(l)) || subjects.getLast().isEmpty()){
                           // System.out.println("Removed " + subjects.getLast());
                            subjects.removeLast();
                        }
                    }
                }
                fw.write("\n");
            }
            fw.flush();
        } catch (IOException e) {
        }
        //TODO Maybe sort elements alphabetically
        System.out.println(subjects);
    }


    public void buildScheduleMaker() {
        final int boxSizeX = sizeX * 3 / 20;
        final int boxSizeY = sizeY * 3 / 32;
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        save = new JButton("Save");
        importButton = new JButton("Import");
        importLabel = new JLabel();
        wDays = new JLabel[5];
        JLabel[] number =new JLabel[8];

        ///Creating table and label
        // TODO: Maybe make scrollable text area and simple borders.
        for (int p = 0; p < 5; p++) {
            wDays[p] = new JLabel();
            wDays[p].setBounds(sizeX / 32 + p * boxSizeX, sizeY / 32 + 20, boxSizeX, 20);
            add(wDays[p]);

            for (int k = 0; k < 8; k++) {
                number[k] = new JLabel();
                number[k].setBounds(sizeX/32 - 10, 40 + sizeY / 32 + k * boxSizeY,10, boxSizeY );
                number[k].setText(String.valueOf(k+1));
                add(number[k]);
                table[p][k] = new JTextArea();
                table[p][k].setBounds(sizeX / 32 + p * boxSizeX, 40 + sizeY / 32 + k * boxSizeY, boxSizeX, boxSizeY);
                table[p][k].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                table[p][k].setLineWrap(true);
                table[p][k].setWrapStyleWord(true);

                add(table[p][k]);
            }
        }


        // TODO: Text Font and design
        wDays[0].setText("Понеделник");
        wDays[1].setText("Вторник");
        wDays[2].setText("Сряда");
        wDays[3].setText("Четвъртък");
        wDays[4].setText("Петък");

        //Loading saved schedule if excists


        System.out.println("Checking if config excists, ln 169");
        if (config.exists()) {
            System.out.println("Config exists, ln170");
            loadSchedule(config);
        }

        ///Creating Save button, Import button import label and importField

        //importButton.setBounds(table[4][1].getX()+boxSizeX+((sizeX-table[4][1].getX()-sizeX)/4), table[4][1].getY(), (sizeX-table[4][1].getX()-sizeX)/2, boxSizeY);
        //TODO Make the scale(no numbers in setBounds())
        save.setBounds(table[4][1].getX() + boxSizeX + 50, table[4][1].getY(), 200, boxSizeY);
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    saveSchedule(table);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


            }
        });

        //TODO Make the scale(no numbers in setBounds())
        importField.setBounds(table[4][1].getX() + boxSizeX + 50, table[4][3].getY(), 200, boxSizeY);

        importLabel.setBounds(table[4][1].getX() + boxSizeX + 50, table[4][3].getY() - 30, 200, 30);
        importLabel.setText("Enter Location");

        importButton.setBounds(table[4][1].getX() + boxSizeX + 50, table[4][5].getY(), 200, boxSizeY);
        importButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    if (!config.exists()) {
                        config.createNewFile();
                        createConfig();
                        System.out.println("Created config ln232");
                        //Some problem test
                    } else if (importField.getText() != "" && !importField.getText().equals(location)) {
                        createConfig();
                    }

                    loadSchedule(config);
                }catch (IOException ex){
                        ex.printStackTrace();
                    }

                    }




        });

        add(save);
        add(importLabel);
        add(importField);
        add(importButton);
        repaint();
    }
}


