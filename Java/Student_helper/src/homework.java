// version 0.16.0.4.22.16.05
import javax.swing.*;
import java.util.LinkedList;

public class homework extends JFrame {
    final int sizeX = 700;
    final int sizeY = 500;
    LinkedList<String> sbj = new LinkedList<String>();
    public homework(String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        buildHomework();

    }


    private void buildHomework() {


    }

}
