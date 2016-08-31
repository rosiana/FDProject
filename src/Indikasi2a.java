import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Rosiana on 8/27/2016.
 */
public class Indikasi2a {
    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static float getPeriodePendaftaran(int kodelelang) throws IOException {
        float periode = 0;
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

            String query = "SELECT timestampdiff(second, t1.mulai, t2.sampai) from tahap t1, tahap t2 where t1.idlelang = t2.idlelang and t1.idlelang = " + kodelelang + " and t2.nama ='Upload Dokumen Penawaran' order by t1.mulai asc limit 1 ";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                periode = (result.getFloat(1)) / (24*60*60);
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
        return periode;
    }

    public static String[] getInfo(int kodelelang) throws IOException {
        String[] info = new String[4];
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

            String query = "SELECT id, kategori, jenisusaha, hps from lelang where id = " + kodelelang;
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                info[0] = result.getString(1);
                info[1] = result.getString(2);
                info[2] = result.getString(3);
                info[3] = result.getString(4);
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
        return info;
    }

    public static void IndikasiIIa() throws IOException {
        int[] kodelelang = getKodeLelang();
        String[] temp = new String[5];
        for (int i = 0; i < kodelelang.length; i++) {
            String[] info = getInfo(kodelelang[i]);
            float periode = getPeriodePendaftaran(kodelelang[i]);
            temp[0] = info[0];
            temp[1] = info[1];
            temp[2] = info[2];
            temp[3] = info[3];
            temp[4] = periode + "";
            System.out.println(temp[0] + " - " + temp[1] + " - " + temp[2] + " - " + temp[3] + " - " + temp[4]);
            dbTulisIndikasiIIa(temp);
        }
    }

    public static void dbTulisIndikasiIIa(String[] temp) throws IOException {
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

            preparedStatement = connect.prepareStatement("insert into  indikasi2ates values (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, Integer.parseInt(temp[0]));
            preparedStatement.setString(2, temp[1]);
            preparedStatement.setString(3, temp[2]);
            preparedStatement.setFloat(4, Float.parseFloat(temp[3]));
            preparedStatement.setFloat(5, Float.parseFloat(temp[4]));

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

            String query = "select count(id) from lelang as jumkode where lpse = \"Kementerian Keuangan\"";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                jumkode = result.getInt(1);
            }
            result.close();
            */

            jumlahkode = new int[8926];
            String query = "SELECT * FROM `lelang` WHERE lpse = \"Kementerian Keuangan\" and ((id < 9720011 and id > 3312011) or (id > 9789011))";
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
