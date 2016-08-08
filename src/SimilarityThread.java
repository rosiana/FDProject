import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Rosiana on 8/3/2016.
 */
public class SimilarityThread {
    private String[] listpeserta1;
    private String[] listpeserta2;
    private float[][] matriks;
    private Connection connection;
    private int kodelelang;
    private int pi;
    private int pj;

    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/fdproject";
    static final String user = "root";
    static final String pass = "";

    public SimilarityThread(String[] listpeserta1,int kodelelang,float[][] matriks,int pi,int pj,Connection connection){
        this.listpeserta1 = listpeserta1;
        this.matriks = matriks;
        this.kodelelang = kodelelang;
        this.pi = pi;
        this.pj  = pj;
        this.connection = connection;
    }

    public void start(){
        run();
    }

    public void run() {
        try {
            listpeserta2 = getPesertaPerLelang(kodelelang);
            matriks[pi][pj] = getSimilarity();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getSimilarity() throws IOException {
        float sim = 0;
        float simx = 0;
        float max = 0;
        float same = 0;
        for (int i = 0; i < listpeserta1.length; i++) {
            loop:
            for (int j = 0; j < listpeserta2.length; j++) {
                if (listpeserta1[i].equals(listpeserta2[j])) {
                    same += 1;
                    break loop;
                }
            }
        }
        if (listpeserta1.length >= listpeserta2.length) {
            max = listpeserta1.length;
        }
        else {
            max = listpeserta2.length;
        }
        sim = same / max;
        simx = 1 - sim;
        System.out.println(pi+" "+pj+" t simx: " + simx);
        return simx;
    }

    public String[] getPesertaPerLelang(int kodelelang) throws IOException {
        Connection connect = connection;
        Statement statement = null;
        String[] pesertalelang = new String[1];
        int numnama = 0;
        try {
            statement = connect.createStatement();

            String query = "select count(nama) from  peserta as numnama where  lelangnum = \"" + kodelelang + "\"";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                numnama = result.getInt(1);
            }
            result.close();

            pesertalelang = new String[numnama];
            query = "select  nama from  peserta where  lelangnum = \"" + kodelelang + "\"";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                pesertalelang[i] = result.getString(1);
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
//            try {
//                if (statement != null)
//                    connect.close();
//            } catch (SQLException se) {
//            }// do nothing
//            try {
//                if (connect != null)
//                    connect.close();
//            } catch(SQLException se) {
//                se.printStackTrace();
//            }//end finally try
        }
        return pesertalelang;
    }
}
