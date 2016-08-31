import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Rosiana on 8/27/2016.
 */
public class Indikasi2d {
    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static float getPeriodePenetapanPemenang(int kodelelang) throws IOException {
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

            String query = "SELECT timestampdiff(second, mulai, sampai) from tahap where idlelang = " + kodelelang + " and nama ='Penetapan Pemenang' order by mulai desc limit 1 ";
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

    public static int getJumlahPeserta(int kodelelang) throws IOException {
        int jumlahpeserta = 0;
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

            String query = "SELECT jumlahpeserta from lelang where id = " + kodelelang;
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                jumlahpeserta = result.getInt(1);
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
        return jumlahpeserta;
    }

    public static void IndikasiIId() throws IOException {
        int[] kodelelang = getKodeLelang();
        float[] temp = new float[4];
        for (int i = 0; i < kodelelang.length; i++) {
            int jumlah = getJumlahPeserta(kodelelang[i]);
            float periode = getPeriodePenetapanPemenang(kodelelang[i]);
            temp[0] = (float)kodelelang[i];
            temp[1] = periode;
            temp[2] = (float)jumlah;
            if (temp[2] > 0) {
                temp[3] = temp[1] / temp[2];
            }
            System.out.println(temp[0] + " - " + temp[1] + " - " + temp[2] + " - " + temp[3]);
            dbTulisIndikasiIId(temp);
        }
    }

    public static void dbTulisIndikasiIId(float[] temp) throws IOException {
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

            preparedStatement = connect.prepareStatement("insert into  indikasi2dtes values (?, ?, ?, ?)");
            preparedStatement.setInt(1, (int)temp[0]);
            preparedStatement.setFloat(2, temp[1]);
            preparedStatement.setFloat(3, temp[2]);
            preparedStatement.setFloat(4, temp[3]);

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
            jumlahkode = new int[9251];
            String query = "select id from lelang where lpse = \"Kementerian Keuangan\" and id > 2786011";
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
