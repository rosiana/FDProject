import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Rosiana on 8/27/2016.
 */
public class Indikasi2c {
    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static void getHPS() throws IOException {
        float[] hps = new float[4];
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

            String query = "SELECT id, penawaranmenang, hps from lelang where penawaranmenang is not null";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                hps[0] = result.getFloat(1);
                hps[1] = result.getFloat(2);
                hps[2] = result.getFloat(3);
                hps[3] = hps[1] / hps[2];
                System.out.println(hps[0] + " - " + hps[1] + " - " + hps[2] + " - " + hps[3]);
                dbTulisIndikasiIIc(hps);
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

    public static void dbTulisIndikasiIIc(float[] hps) throws IOException {
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

            preparedStatement = connect.prepareStatement("insert into  indikasi2c values (?, ?, ?, ?)");
            preparedStatement.setInt(1, (int)hps[0]);
            preparedStatement.setFloat(2, hps[1]);
            preparedStatement.setFloat(3, hps[2]);
            preparedStatement.setFloat(4, hps[3]);

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
