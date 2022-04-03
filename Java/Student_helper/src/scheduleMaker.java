// version 0.3.0.1.22.11.22
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.nio.file.NotDirectoryException;
import java.util.Scanner;


public class scheduleMaker extends JFrame {
    final int sizeX = 1400;
    final int sizeY = 800;
    JTextField importField = new JTextField();
    File file;
    JLabel[] wDays;
    String[][] schedule = new String[5][8];
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
    private void createConfig(){
        try {
            config.createNewFile();
            FileWriter fw = new FileWriter(config);
            fw.write("Schedule Path: " + importField.getText() + "\n");

        }catch(IOException e ){
            JOptionPane.showMessageDialog(null, "Something wrong with path");
        }

    }
     private void loadSchedule(File config){
        String location = new String();
         try {
             Scanner sc = new Scanner(config)
            location =  sc.findInLine("Schedule Path: ")
                    //TODO: Find out how replace method woks to trim "Schedule Path" out of the location string:
            location.re



         }catch(IOException e ){

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

        if(!config.exists()){
            createConfig();
        }

        try  {
            FileWriter fw = new FileWriter(file, false);

            for (int p = 0; p < 5; p++) {
                fw.write(wDays[p].getText() +":"+ "  ");
                for (int k = 0; k < 8; k++) {
                    fw.write(k+1 + "." + schedule[p][k] + " ");

                }
                fw.write("\n");
            }
            fw.flush();

        }catch(IOException e ){

        }




    }


//	private boolean checkIfTableExists(String tableLocation) {
//		boolean exists = false;
//		File table = new File(tableLocation);
//		
//		if(table.exists()) {
//			exists = true;
//		}		
//		return exists;
//	}




    public void buildScheduleMaker() {
        final int boxSizeX = sizeX * 3 / 20;
        final int boxSizeY = sizeY * 3 / 32;
        Border border = BorderFactory.createLineBorder(Color.BLACK);

        JButton save = new JButton("Save");
        JButton importButton = new JButton("Import");
        JLabel importLabel = new JLabel();
        wDays = new JLabel[5];

        JTextArea[][] table = new JTextArea[5][8];

        ///
        ///Creating table and labels
        ///

        // TODO: Make working borders(text doesn't go out of box)

        for (int p = 0; p < 5; p++) {
            wDays[p] = new JLabel();
            wDays[p].setBounds(sizeX / 32 + p * boxSizeX, sizeY / 32 + 20, boxSizeX, 20);
            add(wDays[p]);

            for (int k = 0; k < 8; k++) {
                table[p][k] = new JTextArea();
                table[p][k].setBounds(sizeX / 32 + p * boxSizeX, 40 + sizeY / 32 + k * boxSizeY, boxSizeX, boxSizeY);
                table[p][k].setBorder(BorderFactory.createCompoundBorder(border,
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                add(table[p][k]);
            }
        }


        // TODO: Text Font and design
        wDays[0].setText("Понеделник");
        wDays[1].setText("Вторник");
        wDays[2].setText("Сряда");
        wDays[3].setText("Четвъртък");
        wDays[4].setText("Петък");

        ///
        ///Creating Save button, Import button import label and importField
        ///

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

        add(save);
        add(importLabel);
        add(importField);
        add(importButton);
        repaint();
    }
}


