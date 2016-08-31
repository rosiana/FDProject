import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Rosiana on 8/27/2016.
 */
public class Indikasi2e {
    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static void getPeriodeMasaSanggah(int kodelelang) throws IOException {
        float periode = 0;
        float[] temp = new float[2];
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

            String query = "SELECT timestampdiff(second, mulai, sampai) from tahap where idlelang = " + kodelelang + " and nama = 'Masa Sanggah Hasil Lelang' order by mulai desc limit 1 ";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                periode = (result.getFloat(1)) / (24*60*60);
            }
            result.close();
            temp[0] = kodelelang;
            temp[1] = periode;
            System.out.println(temp[0] + " - " + temp[1]);
            dbTulisIndikasiIIe(temp);

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
    }

    public static void dbTulisIndikasiIIe(float[] temp) throws IOException {
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

            preparedStatement = connect.prepareStatement("insert into  indikasi2etes values (?, ?)");
            preparedStatement.setInt(1, (int)temp[0]);
            preparedStatement.setFloat(2, temp[1]);

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

    public static void IndikasiIIe() throws IOException {
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

            String query = "select id from lelang where lpse = \"Kementerian Keuangan\" and id > 2412011";
            ResultSet result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                int kodelelang = result.getInt(1);
                getPeriodeMasaSanggah(kodelelang);
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
    }
}
