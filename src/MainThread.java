import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Rosiana on 9/1/2016.
 */
public class MainThread {

    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static String[][] getAllPemenang() throws IOException {
        String stringpemenang[][] = new String[1][3];
        int jumpemenang = 0;
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

            String query = "SELECT count(1) from (select distinct lpse, pemenang from lelang where pemenang is not null and lpse = \"Kementerian Keuangan\") al";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                jumpemenang = result.getInt(1);
            }
            result.close();

            stringpemenang = new String[jumpemenang][3];
            query = "select distinct lpse, pemenang from lelang where pemenang is not null and lpse = \"Kementerian Keuangan\"";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                stringpemenang[i][0] = result.getString(1);
                stringpemenang[i][1] = result.getString(2);
                System.out.println(stringpemenang[i][0] + " - " + stringpemenang[i][1]);
                i++;
            }
            result.close();

            for (int j = 0; j < stringpemenang.length; j++) {
                query = "SELECT count(*) from (select id from lelang where pemenang = \"" + stringpemenang[j][1] + "\") al";
                result = statement.executeQuery(query);
                while (result.next()) {
                    //Retrieve by column name
                    stringpemenang[j][2] = result.getString(1);
                }
                result.close();
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
        return stringpemenang;
    }

    public static void main(String[] args) throws IOException {

        String[][] pemenang = getAllPemenang();

        ThreadGroup tg = new ThreadGroup("main");

        int np = Runtime.getRuntime().availableProcessors();

        int i, ns=24;



        List<Indikasi3Thread> ithreads = new ArrayList<Indikasi3Thread>();



        for (i=0;i<ns;i++)

            ithreads.add(new Indikasi3Thread(pemenang,"x"+i, tg));



        i=0;

        while (i<ithreads.size())

        {

			/*do we have available CPUs?*/

            if (tg.activeCount()<np)

            {

                Indikasi3Thread ith = ithreads.get(i);

                ith.start();

                i++;

            } else

                try {Thread.sleep(100);} /*wait 0.1 second before checking again*/

                catch (InterruptedException e) {e.printStackTrace();}

        }





		/*wait for threads to finish*/

        while(tg.activeCount()>0)

        {

            try {Thread.sleep(100);}

            catch (InterruptedException e) {e.printStackTrace();}

        }
    }
}
