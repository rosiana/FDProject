/**
 * Created by Rosiana on 5/3/2016.
 */

import java.io.IOException;

public class Main2 {
    static Crawler crawler = new Crawler();
    static T2a t2a = new T2a();


    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/fdproject";
    static final String user = "root";
    static final String pass = "";

    public static void main (String args[]) throws IOException {
        /*
        int[] kodelelang = new int[1];
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

            String query = "select count(id) from  lelang as numlelang";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                numlelang = result.getInt(1);
            }
            result.close();
            kodelelang = new int[numlelang];
            query = "select id from  lelang";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                kodelelang[i] = result.getInt(1);
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
        */
        String url = "lpse.jatengprov.go.id";
        String lpse = "Provinsi Jawa Tengah";
        //crawler.getAllJumlahPesertaS();
        crawler.getAllPesertaLelangS(url);
    }

}
