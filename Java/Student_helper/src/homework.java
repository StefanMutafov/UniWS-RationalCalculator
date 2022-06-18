// version 1.8.0.6.22.13.40
import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;

public class homework extends JFrame {
    final int sizeX = 700;
    final int sizeY = 500;
    LinkedList<String> subjects = new LinkedList<String>();
    File config = new File("src/config.txt");
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

    private void buildHomework() {
        // Loading different subjects fom schedule
        try {
            loadSubjects();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //Creating AddHomework part
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

add(titleHead);
add(dscHead);
add(description);
add(title);
add(addHead);
add(jc);
    }

}
