package connect.db;
import java.sql.*;

public class DatabaseConnection {


    public static void main(String[] args)  {
        Connection c;
        Statement st;
        ResultSet r;
        String dbURL = "jdbc:mysql://localhost:3306/concerts";
        String user = "root";
        String pass = "StefanM951659";
        String statement = "SELECT * FROM singers";
        try {
            c = DriverManager.getConnection(dbURL, user, pass);
             st = c.createStatement();
             r =  st.executeQuery(statement);
             System.out.print("id ");
             System.out.print("f_name ");
            System.out.print("l_name ");
            System.out.print("nationaliti ");
            System.out.println("style ");

             while(r.next()){
                    System.out.print(r.getInt(1) + " ");
                 System.out.print(r.getString(2)+ " ");
                 System.out.print(r.getString(3)+ " ");
                 System.out.print(r.getString(4)+ " ");
                 System.out.println(r.getString(5)+ " ");
             }


        }catch (SQLException e) {
            e.printStackTrace();
        }
    }







}

