import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Rosiana on 8/27/2016.
 */
public class Indikasi2b {
    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static int getJumlahPerubahan(int kodelelang) throws IOException {
        int jumperubahan = 0;
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

            String query = "SELECT sum(perubahan) from tahap where idlelang = " + kodelelang;
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                jumperubahan = result.getInt(1);
            }
            result.close();
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
        return jumperubahan;
    }

    public static void IndikasiIIb() throws IOException {
        int[] kodelelang = getKodeLelang();
        String[] temp = new String[2];
        for (int i = 0; i < kodelelang.length; i++) {
            int jumperubahan = getJumlahPerubahan(kodelelang[i]);
            temp[0] = kodelelang[i] + "";
            temp[1] = jumperubahan + "";
            System.out.println(temp[0] + " - " + temp[1]);
            dbTulisIndikasiIIb(temp);
        }
    }

    public static void dbTulisIndikasiIIb(String[] temp) throws IOException {
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

            preparedStatement = connect.prepareStatement("insert into  indikasi2btes values (?, ?)");
            preparedStatement.setInt(1, Integer.parseInt(temp[0]));
            preparedStatement.setInt(2, Integer.parseInt(temp[1]));

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

    public static int[] getKodeLelang() throws IOException {
        int[] jumlahkode = new int[1];
        int jumkode = 0;
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

            /*

            String query = "select count(id) from lelang as jumkode";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                jumkode = result.getInt(1);
            }
            result.close();
            */
            jumlahkode = new int[8720];
            String query = "select id from lelang where lpse = \"Kementerian Keuangan\" and id > 3786011";
            ResultSet result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                jumlahkode[i] = result.getInt(1);
                System.out.println(jumlahkode[i]);
                i++;
            }
            result.close();
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
        return jumlahkode;
    }
}
