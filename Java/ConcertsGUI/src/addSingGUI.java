import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class addSingGUI extends JFrame {
    final int sizeX = 400;
    final int sizeY = 400;
    public addSingGUI(String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        buildSingGui();


    }

    private void buildSingGui() {
        //GUI
            //Labels
        JLabel l_id = new JLabel("ID");
        l_id.setBounds(sizeX/4, sizeY/8, sizeX/4, sizeY/8);
        l_id.setFont(new Font("Courier", Font.PLAIN, 17));
        l_id.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_fName= new JLabel("First Name");
        l_fName.setBounds(sizeX/4, sizeY*2/8, sizeX/4, sizeY/8);
        l_fName.setFont(new Font("Courier", Font.PLAIN, 17));
        l_fName.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_lName= new JLabel("Last Name");
        l_lName.setBounds(sizeX/4, sizeY*3/8, sizeX/4, sizeY/8);
        l_lName.setFont(new Font("Courier", Font.PLAIN, 17));
        l_lName.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_nationality= new JLabel("Nationality");
        l_nationality.setBounds(sizeX/4, sizeY*4/8, sizeX/4, sizeY/8);
        l_nationality.setFont(new Font("Courier", Font.PLAIN, 17));
        l_nationality.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel l_style= new JLabel("Style");
        l_style.setBounds(sizeX/4, sizeY*5/8, sizeX/4, sizeY/8);
        l_style.setFont(new Font("Courier", Font.PLAIN, 17));
        l_style.setHorizontalAlignment(SwingConstants.CENTER);

            //Text Areas
        JTextField id = new JTextField();
        id.setBounds(sizeX/2, sizeY/8, sizeX/4, sizeY/8);

        JTextField fName = new JTextField();
        fName.setBounds(sizeX/2, sizeY*2/8, sizeX/4, sizeY/8);

        JTextField lName = new JTextField();
        lName.setBounds(sizeX/2, sizeY*3/8, sizeX/4, sizeY/8);

        JTextField nationality = new JTextField();
        nationality.setBounds(sizeX/2, sizeY*4/8, sizeX/4, sizeY/8);

        JTextField style = new JTextField();
        style.setBounds(sizeX/2, sizeY*5/8, sizeX/4, sizeY/8);



        //Database
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        int rows;
        boolean run=true;
        String db="jdbc:mysql://127.0.0.1:3306/concerts";
        String user="root";
        String pass="StefanM951659";
        try {
            con= DriverManager.getConnection(db,user,pass);
            if(con != null){System.out.println("Connection to db for addSinger successful!");}else{ System.out.println("Connection to db for addSinger NOT successful!");}




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        add(l_id);
        add(l_fName);
        add(l_lName);
        add(l_nationality);
        add(l_style);
        add(id);
        add(fName);
        add(lName);
        add(nationality);
        add(style);
        repaint();
    }//end of build


}//class end
