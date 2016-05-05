/**
 * Created by Rosiana on 5/5/2016.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSON {

    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/fdproject";
    static final String user = "root";
    static final String pass = "";

    public static void main(String args[]) {

        JSONObject obj = new JSONObject();
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

            String query = "select * from  lelang";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                obj.put("id", new Integer(result.getInt(1)));
                obj.put("nama", new String(result.getString(2)));
                obj.put("status", new Integer(result.getInt(3)));
                obj.put("agency", new String(result.getString(4)));
                obj.put("pagu", new Float(result.getFloat(5)));
                obj.put("hps", new Float(result.getFloat(6)));
            }
            result.close();

            FileWriter file = new FileWriter("lelang.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();

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