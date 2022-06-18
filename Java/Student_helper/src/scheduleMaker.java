// version 1.8.0.6.22.13.40

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.io.*;
import java.security.cert.CertStoreSpi;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;


public class scheduleMaker extends JFrame {
    final int sizeX = 1400;
    final int sizeY = 800;

    String location = new String();
    LinkedList<String> subjects = new LinkedList<String>();
    JTextField importField = new JTextField();
    File file;
    JLabel[] wDays;
    String[][] schedule = new String[5][8];
    JButton save;
    JButton importButton;
    JLabel importLabel;
    JTextPane[][] table = new JTextPane[5][8];
    File config = new File("src/config.txt");

    public void saveSubjects(File f) throws IOException {
        FileWriter fw;
        Scanner sc = new Scanner(f);
       sc.nextLine();
        if(sc.hasNextLine()){
            sc.close();
            sc = new Scanner(f);
            String tmp = new String(sc.nextLine());
            sc.close();
            System.out.println(tmp);
            fw = new FileWriter(f);
            fw.append(tmp + "\n");
            fw.append("The subjects are: ");
            for(int i = 0; i< subjects.size();i++){
                fw.append(subjects.get(i)+ " ");
            }
            sc.close();
        }else {
            fw = new FileWriter(f, true);
            fw.append("\n"+"The subjects are: ");
            for (int i = 0; i < subjects.size(); i++) {
                fw.append(subjects.get(i) + " ");
            }
        }
        fw.flush();
       fw.close();

    }

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
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Something went wrong, please restart the program");
        }

    }


   //Loading schedule file from a cfg path
    private void loadSchedule(File config) {
        System.out.println("Started loading schedule, ln89");
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
            subjects.clear();
            for (int p = 0; sc.hasNextLine(); p++) {
                line = sc.nextLine();
                System.out.println("The line at the moment is " + p + "." + line);
                String[] split_array = line.split("\\d\\.");
                System.out.println("split_array is " + Arrays.toString(split_array));
                for (int k = 0; k + 1 < split_array.length; k++) {
                    table[p][k].setText(split_array[k + 1].trim());
                    // Sorting subjects
                    subjects.add(table[p][k].getText());
                    for(int l = 0; l< subjects.size()-1; l++){
                        //System.out.println("Checking if " + subjects.getLast() +" == " +subjects.get(l));
                        if(subjects.getLast().equals(subjects.get(l)) || subjects.getLast().isEmpty()){
                            // System.out.println("Removed " + subjects.getLast());
                            subjects.removeLast();
                        }
                    }
                    System.out.println("Table " + p + ":" + k + " is " + table[p][k].getText());
                }
            }
            System.out.println(subjects);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't load your schedule");
        }
    }



    //TODO: Make Font size And design of export Table.
    private void saveSchedule(JTextPane[][] t) throws IOException {
        for (int p = 0; p < 5; p++) {
            for (int k = 0; k < 8; k++) {
                schedule[p][k] = t[p][k].getText().trim();
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
        }else if (!importField.getText().equals("") && !importField.getText().equals(location)){
            createConfig();
        }

        try {
            FileWriter fw = new FileWriter(file, false);
            subjects.clear();
            for (int p = 0; p < 5; p++) {
                fw.write(wDays[p].getText() + ":" + "  ");
                for (int k = 0; k < 8; k++) {
                    fw.write(k + 1 + "." + schedule[p][k] + " ");
                    // Sorting subjects
                    subjects.add(schedule[p][k].trim());
                    for(int l = 0; l< subjects.size()-1; l++){
                        //System.out.println("Checking if " + subjects.getLast() +" == " +subjects.get(l));
                        if(subjects.getLast().trim().equals(subjects.get(l)) || subjects.getLast().isEmpty()){
                           // System.out.println("Removed " + subjects.getLast());
                            subjects.removeLast();
                        }
                    }
                }
                fw.write("\n");
            }
            fw.flush();
            fw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Couldn't save your schedule");
        }

        System.out.println(subjects);
        saveSubjects(config);
    }


    public void buildScheduleMaker() {
        final int boxSizeX = sizeX * 3 / 20;
        final int boxSizeY = sizeY * 3 / 32;


        save = new JButton("Save");
        importButton = new JButton("Import");
        importLabel = new JLabel();
        wDays = new JLabel[5];
        JLabel[] number =new JLabel[8];

        ///Creating table and label

        for (int p = 0; p < 5; p++) {
            wDays[p] = new JLabel();
            wDays[p].setBounds(sizeX / 32 + p * boxSizeX, sizeY / 32 + 20, boxSizeX, 20);
            add(wDays[p]);

            for (int k = 0; k < 8; k++) {
                number[k] = new JLabel();
                number[k].setBounds(sizeX/32 - 10, 40 + sizeY / 32 + k * boxSizeY,10, boxSizeY );
                number[k].setText(String.valueOf(k+1));
                add(number[k]);
                table[p][k] = new JTextPane();
                table[p][k].setBounds(sizeX / 32 + p * boxSizeX, 40 + sizeY / 32 + k * boxSizeY, boxSizeX, boxSizeY);
                table[p][k].setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                //Centering
                StyledDocument doc = table[p][k].getStyledDocument();
                SimpleAttributeSet center = new SimpleAttributeSet();
                StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
                doc.setParagraphAttributes(0, doc.getLength(), center, false);


                add(table[p][k]);
            }

        }


        // TODO: Text Font and design
        wDays[0].setText("Понеделник");
        wDays[1].setText("Вторник");
        wDays[2].setText("Сряда");
        wDays[3].setText("Четвъртък");
        wDays[4].setText("Петък");

        //Loading saved schedule if exists


        System.out.println("Checking if config exists, ln 169");
        if (config.exists()) {
            System.out.println("Config exists, ln170");
            loadSchedule(config);
        }

        ///Creating Save button, Import button import label and importField

        //importButton.setBounds(table[4][1].getX()+boxSizeX+((sizeX-table[4][1].getX()-sizeX)/4), table[4][1].getY(), (sizeX-table[4][1].getX()-sizeX)/2, boxSizeY);
        //TODO Make the scale(no numbers in setBounds())
        save.setBounds(table[4][1].getX() + boxSizeX + 50, table[4][1].getY(), 200, boxSizeY);
        save.addActionListener(e -> {
            try {
                saveSchedule(table);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "There was a problem saving your file");
                ex.printStackTrace();
            }


        });

        //TODO Make the scale(no numbers in setBounds())
        importField.setBounds(table[4][1].getX() + boxSizeX + 50, table[4][3].getY(), 200, boxSizeY);

        importLabel.setBounds(table[4][1].getX() + boxSizeX + 50, table[4][3].getY() - 30, 200, 30);
        importLabel.setText("Enter Location");

        importButton.setBounds(table[4][1].getX() + boxSizeX + 50, table[4][5].getY(), 200, boxSizeY);
        importButton.addActionListener(e -> {
            try {
                if (!config.exists()) {
                    config.createNewFile();
                    createConfig();
                    System.out.println("Created config ln232");
                } else if (!importField.getText().equals("") && !importField.getText().equals(location)) {
                    createConfig();
                }

                loadSchedule(config);
            }catch (IOException ex){
                    ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "There was a problem importing your file");
                }

                });

        add(save);
        add(importLabel);
        add(importField);
        add(importButton);
        repaint();
    }
}


