// version 1.8.0.6.22.13.40
import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;


public class homework extends JFrame  {
    int counter = 0;
    final int sizeX = 700;
    final int sizeY = 500;
    LinkedList<String> subjects = new LinkedList<String>();
    File config = new File("src/config.txt");
    File homeworks = new File("src/homework.txt");

    ArrayList<assignment> assignmentsList = new ArrayList<>();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    public homework(String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        buildHomework();

    }
    public void loadSubjects() throws FileNotFoundException {
        Scanner sc = new Scanner(config);
        sc.nextLine();
        sc.skip("The subjects are: ");
        String subjects_str = new String(sc.nextLine());
        sc.close();
        String[] split_array = subjects_str.split(" ");
        for(int i = 0; i<split_array.length;i++){
            subjects.add(split_array[i]);
        }
        System.out.println(subjects);

    }
    public void loadHomework(){

        //Loading assignments into array
       boolean cont = true;
        while (cont) {
            System.out.println("Loading");
            try {
                FileInputStream fi = new FileInputStream(homeworks);
                ObjectInputStream oi = new ObjectInputStream(fi);
                assignment a = (assignment) oi.readObject();
                assignmentsList.add(a);
               // oi.close();
               // fi.close();

//                //System.out.println("a");
//                if (a != null) {
//                    //System.out.println("a");
//                    assignmentsList.add(a);
//                } else {
//                    System.out.println("false");
//                    //cont = false;
//                }

            } catch (EOFException exc) {
                cont = false;
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }


    }
    public void writeHomework(int a, JLabel j) {
        System.out.println("Writing homework");
        if(assignmentsList.get(a) != null) {
            String h = assignmentsList.get(a).getSubject() + "\n"
                    + "Краен срок: " + formatter.format(assignmentsList.get(a).getDate()) + "\n"
                    + assignmentsList.get(a).getTitle() + "\n"
                    + assignmentsList.get(a).getDescription();
            j.setText(h);
        }
    }


    private void buildHomework() {


        //Checking if homework exists
        try {
        if(!homeworks.exists()){
            homeworks.createNewFile();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Loading different subjects fom schedule
        try {
            loadSubjects();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Creating AddHomework part
        JLabel cHead = new JLabel("Домашни");
        cHead.setBounds(sizeX/12, sizeY/20, sizeX/3, sizeY/16);
        cHead.setFont(new Font("Courier", Font.PLAIN, 20));
        cHead.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel cHomework = new JLabel();
        cHomework.setBounds(sizeX/12, sizeY*4/20, sizeX/3, sizeY*6/10);
        cHomework.setBackground(Color.WHITE);
        cHomework.setOpaque(true);
        Scanner sc = null;
        try {
            sc = new Scanner(homeworks);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Checking file");
        if(sc.hasNext()) {
            loadHomework();
            writeHomework(0, cHomework);
            System.out.println("Loaded and wrote homework");
        }
        sc.close();
        JComboBox jc = new JComboBox(subjects.toArray(new String[0]));
        jc.setBounds(sizeX*7/12, sizeY*4/20, sizeX/3, sizeY/16);
        JLabel addHead = new JLabel("Добавяне на домашни");
        JLabel titleHead = new JLabel("Заглавие");
        JLabel dscHead = new JLabel("Описание");
        addHead.setBounds(sizeX*7/12, sizeY/20, sizeX/3, sizeY/16);
        addHead.setFont(new Font("Courier", Font.PLAIN, 20));
        addHead.setHorizontalAlignment(SwingConstants.CENTER);
        titleHead.setBounds(sizeX*7/12, sizeY*5/20, sizeX/3, sizeY/16);
        titleHead.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        titleHead.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField  title = new JTextField();
        title.setBounds(sizeX*7/12, sizeY*6/20, sizeX/3, sizeY/16);
        JTextArea textArea = new JTextArea();
        dscHead.setBounds(sizeX*7/12, sizeY*7/20, sizeX/3, sizeY/16);
        dscHead.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        dscHead.setHorizontalAlignment(SwingConstants.CENTER);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane description = new JScrollPane(textArea);
        description.setBounds(sizeX*7/12,sizeY*8/20,sizeX/3, sizeY/4 );
        JLabel dueText = new JLabel("За:");
        dueText.setBounds(sizeX*7/12,sizeY*13/20,sizeX/6, sizeY/16 );
        dueText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        dueText.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField dueDate = new JTextField();
        dueDate.setBounds(sizeX*9/12,sizeY*13/20,sizeX/6, sizeY/16);
        JButton saveHomework = new JButton("Запази");

        saveHomework.setBounds(sizeX*7/12,sizeY*15/20,sizeX/3, sizeY/16);
        saveHomework.addActionListener(new ActionListener() {
            //SaveHomework button function

            public void actionPerformed(ActionEvent e) {

                String longDate = new String(dueDate.getText());
                String[] split_array = longDate.split("/");
                Date assDate = new Date(Integer.parseInt(split_array[2])-1900,Integer.parseInt(split_array[1])-1, Integer.parseInt(split_array[0]));

            assignment a = new assignment((String) jc.getSelectedItem(), title.getText(), textArea.getText(),assDate );
                System.out.println(a.toString());

                try (FileOutputStream fos = new FileOutputStream(homeworks);
                     ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                    oos.writeObject(a);

                } catch (IOException ex) {
                    ex.printStackTrace();

                }
                loadHomework();
                writeHomework(0, cHomework);

            }
        });
        JButton next = new JButton(">");
        next.setBounds(sizeX*4/12, sizeY/20, sizeX/12, sizeY/16);
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(assignmentsList.size()>counter-1) {
                    counter++;
                    writeHomework(counter, cHomework);
                }


            }
        });
        JButton prv = new JButton("<");
        prv.setBounds(sizeX/12, sizeY/20, sizeX/12, sizeY/16);
        prv.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(counter>0) {
                    counter--;
                    writeHomework(counter, cHomework);
                }


            }
        });



add(prv);
add(next);
add(cHead);
add(cHomework);
add(saveHomework);
add(dueDate);
add(dueText);
add(titleHead);
add(dscHead);
add(description);
add(title);
add(addHead);
add(jc);
repaint();
    }

}
