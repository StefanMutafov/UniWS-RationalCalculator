// version 0.01.0.6.22.11.31
import javax.swing.*;
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

        try {
            loadSubjects();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
