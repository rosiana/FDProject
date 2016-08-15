/**
 * Created by Rosiana on 5/3/2016.
 */

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main {
    static Crawler crawler = new Crawler();
    static T1 t1 = new T1();


    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/fdproject";
    static final String user = "root";
    static final String pass = "";

    public static String[] get() throws IOException {

        String[] pemenang = new String[1];
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
            int numlelang = 0;

            String query = "select distinct count(nama) from  lelang where nama like \'%" + "\"" +"%\'";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                numlelang = result.getInt(1);
            }
            result.close();

            pemenang = new String[numlelang];

            query = "select distinct(nama) from  lelang where nama like \'%" + "\"" +"%\'";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                pemenang[i] = result.getString(1);
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
        return pemenang;
    }

    public static void set(String[] pemenang) throws IOException {
        Connection conn = null;
        Statement stmt = null;
        try{
            //STEP 2: Register JDBC driver
            Class.forName(myDriver);

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(myUrl, user, pass);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            for (int i = 0; i < pemenang.length; i++) {
                String pemenangnew = pemenang[i].replace("\"","");
                System.out.println(pemenangnew);
                stmt = conn.createStatement();
                String sql = "UPDATE lelang SET nama = \"" + pemenangnew + "\" WHERE nama = \"" + pemenang[i] + "\"";
                stmt.executeUpdate(sql);
            }
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    conn.close();
            }catch(SQLException se){
            }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }

    public static void main (String[] args) throws IOException {
        String[] pemenang = get();
        set(pemenang);
    }

}
