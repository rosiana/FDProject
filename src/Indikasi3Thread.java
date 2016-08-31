import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Rosiana on 9/1/2016.
 */
public class Indikasi3Thread extends Thread {
    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    String[][] peserta = new String[1][1];

    Indikasi3Thread(String[][] peserta, String name, ThreadGroup tg) {
        super(tg,name);
        this.peserta = peserta;
    }

    public void run()
    {
        getAllLinkPerPemenang(peserta);
    }

    public static String[] getLinkPerPemenang(String pemenang1, String pemenang2) {
        String[] links = new String[2];
        Connection connect = null;
        Statement statement = null;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();
            int jum = 0;
            String query = "select count(*) from (SELECT p1.idlelang from peserta as p1, peserta as p2 where p1.nama = \"" + pemenang1 +"\" and p2.nama = \"" +pemenang2 + "\" and p1.idlelang = p2.idlelang group by p1.idlelang) as al";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                jum = result.getInt(1);
            }
            result.close();
            if (jum > 0) {
                links[0] = jum + "";
                String lelangsamastring = "";
                query = "SELECT p1.idlelang from peserta as p1, peserta as p2 where p1.nama = \"" + pemenang1 + "\" and p2.nama = \"" +pemenang2 + "\" and p1.idlelang = p2.idlelang group by p1.idlelang";
                result = statement.executeQuery(query);
                while (result.next()) {
                    //Retrieve by column name
                    lelangsamastring += result.getString(1);
                    lelangsamastring += ", ";
                }
                links[1] = lelangsamastring.substring(0, lelangsamastring.length()-2);
            }
            else {
                links[0] = "0";
                links[1] = "-";
            }
        } catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    connect.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connect != null)
                    connect.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
        return links;
    }

    public static void getAllLinkPerPemenang(String[][] listpemenang) {
        for (int i = 0; i < listpemenang.length; i++) {
            boolean ada = false;
            String[] links = new String[6];
            for (int j = i + 1; j < listpemenang.length; j++) {
                String[] temp = getLinkPerPemenang(listpemenang[i][1], listpemenang[j][1]);
                System.out.println(listpemenang[i][1] + " - " + listpemenang[j][1]);
                System.out.println("temp: " + temp[0] + ", " + temp[1]);
                if (!temp[0].equals("0")) {
                    links[0] = listpemenang[i][0];
                    links[1] = listpemenang[i][1];
                    links[2] = listpemenang[i][2];
                    links[3] = listpemenang[j][1];
                    links[4] = temp[0];
                    links[5] = temp[1];
                    System.out.println(links[0] + " - " + links[1] + " - " + links[2] + " - " + links[3] + " - " + links[4] + " - " + links[5]);
                    dbTulisIndikasiIII(links);
                    ada = true;
                }
            }
            if (!ada) {
                links[0] = listpemenang[i][0];
                links[1] = listpemenang[i][1];
                links[2] = listpemenang[i][2];
                links[3] = "-";
                links[4] = "-";
                links[5] = "-";
                System.out.println(links[0] + " - " + links[1] + " - " + links[2] + " - " + links[3] + " - " + links[4] + " - " + links[5]);
                dbTulisIndikasiIII(links);
            }
        }
    }

    public static void dbTulisIndikasiIII(String[] links) {
        //mysql
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);

            preparedStatement = connect.prepareStatement("insert into  indikasi3thread values (?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, links[0]);
            preparedStatement.setString(2, links[1]);
            preparedStatement.setInt(3, Integer.parseInt(links[2]));
            if (!links[3].equals("-")) {
                preparedStatement.setString(4, links[3]);
            }
            else {
                preparedStatement.setObject(4, null);
            }
            if (!links[4].equals("-")) {
                preparedStatement.setInt(5, Integer.parseInt(links[4]));
            }
            else {
                preparedStatement.setObject(5, null);
            }
            if (!links[5].equals("-")) {
                preparedStatement.setString(6, links[5]);
            }
            else {
                preparedStatement.setObject(6, null);
            }
            preparedStatement.addBatch();
            preparedStatement.executeBatch();
            System.out.println("Masuk Batch");
        }
        catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        } catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (preparedStatement != null)
                    connect.close();
            } catch (SQLException se) {
            }// do nothing
            try {
                if (connect != null)
                    connect.close();
            } catch(SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }
}
