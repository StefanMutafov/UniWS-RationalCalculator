// version 0.8.0.6.22.11.31
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
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
        jc.setBounds(sizeX*8/12, sizeY/8, sizeX/6, sizeY/16);
        JLabel addHead = new JLabel("Добавяне на домашни");
        addHead.setBounds(sizeX*7/12, sizeY/128, sizeX/3, sizeY/8);
        addHead.setFont(new Font("Courier", Font.PLAIN, 20));
        addHead.setHorizontalTextPosition(SwingConstants.CENTER);
        JTextField  title = new JTextField();
        title.setBounds(sizeX*8/12, sizeY*3/16, sizeX/6, sizeY/16);


add(title);
add(addHead);
add(jc);
    }

}
