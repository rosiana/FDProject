import org.json.simple.JSONObject;

import java.io.*;
import java.sql.*;
import java.util.*;

/**
 * Created by Rosiana on 7/11/2016.
 */
public class T2a {

    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/fdproject";
    static final String user = "root";
    static final String pass = "";

    public static float[][] initMatrixPeserta() throws IOException {
        Connection connect = null;
        Statement statement = null;
        float[][] matrixpeserta = new float[1][1];
        int numpeserta = 0;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            String query = "select count(id) from  lelang as numpeserta";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                numpeserta = result.getInt(1);
            }
            result.close();
            System.out.println("dimensi: " + numpeserta);
            matrixpeserta = new float[numpeserta][numpeserta];
            for (int i = 0; i < matrixpeserta.length; i++) {
                for (int j = 0; j < matrixpeserta.length; j++) {
                    matrixpeserta[i][j] = -999;
                }
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
        return matrixpeserta;
    }

    public static int[] getLelangList() throws IOException {
        Connection connect = null;
        Statement statement = null;
        int[] pesertalist = new int[1];
        int numpeserta = 0;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            String query = "select count(id) from  lelang as numpeserta";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                numpeserta = result.getInt(1);
            }
            result.close();
            pesertalist = new int[numpeserta];
            query = "select id from  lelang";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                pesertalist[i] = result.getInt(1);
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
        return pesertalist;
    }

    public static Map<Integer,String[]> cachePeserta = new HashMap<>();
    public static String[] getPesertaPerLelang(int kodelelang) throws IOException {
        if (cachePeserta.containsKey(kodelelang)){
            return cachePeserta.get(kodelelang);
        }
        Connection connect = null;
        Statement statement = null;
        String[] pesertalelang = new String[1];
        int numnama = 0;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
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
        cachePeserta.put(kodelelang,pesertalelang);
        return pesertalelang;
    }

    public static String[] getAllNamaLelang(String peserta) throws IOException {
        Connection connect = null;
        Statement statement = null;
        String[] namalelang = new String[1];
        int numlelang = 0;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            String query = "select jabarprov_lelang.nama from  jabarprov_lelang inner join  jabarprov_peserta where  jabarprov_peserta.nama = \"" + peserta + "\"";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                numlelang++;
            }
            result.close();

            namalelang = new String[numlelang];
            query = "select jabarprov_lelang.nama from  jabarprov_lelang inner join  jabarprov_peserta where  jabarprov_peserta.nama = \"" + peserta + "\"";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                namalelang[i] = result.getString(1);
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
        return namalelang;
    }

    public static float[] getBaris(int kodelelang, int[] listkodelelang) throws IOException {

        float[] baris = new float[listkodelelang.length];
        for (int i = 0; i < baris.length; i++) {
            baris[i] = getSimilarity(kodelelang, listkodelelang[i]);
        }
        return baris;
    }

    public static void isiMatrix(int[] listkodelelang) throws IOException {
        System.out.println("dimensi " + listkodelelang.length);
        for (int i = 0; i < listkodelelang.length; i++) {
            System.out.println(listkodelelang[i]);
        }
        for (int i = 0; i <= listkodelelang.length; i++) {
            float[] baris = getBaris(listkodelelang[i], listkodelelang);
            dbInsertT2a(listkodelelang[i], listkodelelang, baris);
            System.out.println("insert");
        }
    }

    public static void dbInsertT2a(int kodelelang, int[] listkodelelang, float[] baris) throws IOException {
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);

            preparedStatement = connect.prepareStatement("insert into  t2a values (?, ?, ?)");
            for (int i = 0; i < listkodelelang.length; i++) {
                preparedStatement.setInt(1, kodelelang);
                preparedStatement.setFloat(2, listkodelelang[i]);
                preparedStatement.setFloat(3, baris[i]);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
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

    public static float getSimilarity(int i, int j) throws IOException {
        Connection connect = null;
        Statement statement = null;
        float x= 0;
        float simx = 0;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            String query = "select count(*) from (select p1.nama, p2.nama al1 from  peserta p1,  peserta p2 where p1.nama = p2.nama and p1.lelangnum = " + i + " and p2.lelangnum = " + j + ") al2";
            ResultSet result = statement.executeQuery(query);

            int jum = 0;
            while (result.next()) {
                //Retrieve by column name
                jum = result.getInt(1);
            }

            result.close();

            int p1 = 0;
            int p2 = 0;
            query = "select count(*) from peserta as p1 where lelangnum = " + i;
            result = statement.executeQuery(query);

            while (result.next()) {
                p1 = result.getInt(1);
            }
            result.close();

            query = "select count(*) from peserta as p2 where lelangnum = " + j;
            result = statement.executeQuery(query);

            while (result.next()) {
                p2 = result.getInt(1);
            }
            result.close();

            float fjum = (float)jum;
            float fp1 = (float)p1;
            float fp2 = (float)p2;

            if (fp1 > fp2) {
                x = fjum / fp1;
            }
            else {
                x = fjum / fp2;
            }
            simx = 1 - x;
            System.out.println(i + "," + j + " " + simx);

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
        return simx;
    }

    public static String[] getPesertaList() throws IOException {
        Connection connect = null;
        Statement statement = null;
        String[] peserta = new String[1];
        int numpeserta = 0;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            String query = "select distinct  nama from  jabarprov_peserta";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                numpeserta++;
            }
            result.close();
            peserta = new String[numpeserta];
            query = "select distinct  nama from  jabarprov_peserta";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                peserta[i] = result.getString(1);
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
        return peserta;
    }

    public static int[] getLelangList2() throws IOException {

        int[] arrlelang = new int[496];
        int[] pesertaall = new int[5000];

        FileInputStream fstream = new FileInputStream("case");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String strLine;

        int i = 0;
        while ((strLine = br.readLine()) != null) {
            arrlelang[i] = Integer.parseInt(strLine);
            i++;
        }

        Connection connect = null;
        Statement statement = null;
        int[] lelang = new int[5000];
        int numpeserta = 0;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            String query = "select distinct  lelangnum from  peserta order by rand() limit 5000";
            ResultSet result = statement.executeQuery(query);
            for (int l = 0; l < pesertaall.length; l++) {
                if (l < 496) {
                    pesertaall[l] = arrlelang[l];
                }
                else {
                    pesertaall[l] = -999;
                }
            }

            int[] pesertatemp = new int[5000];
            int p = 0;
            while (result.next()) {
                pesertatemp[p] = result.getInt(1);
                p++;
            }
            result.close();
            int x = 0;
            for (int m = 0; m < pesertatemp.length; m++) {
                boolean found = false;
                loop:
                for (int n = 0; n < 496; n++) {
                    if (pesertatemp[m] == pesertaall[n]) {
                        found = true;
                        break loop;
                    }
                    else {

                    }
                }
                if (found == false && x < 4504) {
                    System.out.println(m);
                    pesertaall[496 + x] = pesertatemp[m];
                    x++;
                }
            }

            for (int j = 0; j < pesertaall.length; j++) {
                System.out.println(pesertaall[j]);
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
        return pesertaall;
    }

    public static void getJSONT2a(int[] kodelelang, float[][] matrix) throws IOException {
        JSONObject obj = new JSONObject();
        String jsonstring = "";

        int[] jumlahpeserta = getAllJumlahPeserta(kodelelang);

        FileWriter writer = new FileWriter("web/json/t2ax.json");

        //start matrix
        jsonstring += "{\"matrix\":[";
        for (int i = 0; i < matrix.length; i++) {
            jsonstring += "[";
            for (int j = 0; j < matrix.length; j++) {
                jsonstring += matrix[i][j];
                if (j < matrix.length - 1) {
                    jsonstring += ",";
                }
            }
            jsonstring += "]";
            if (i < matrix.length - 1) {
                jsonstring += ",";
            }
        }
        jsonstring += "],"; //end matrix

        //start peserta
        jsonstring += "\"lelang\":[";
        for (int i = 0; i < kodelelang.length; i++) {
            jsonstring += "{";
            jsonstring += "\"id\":" + kodelelang[i] + ",";
            String namalelang = getNamaLelang(kodelelang[i]);
            jsonstring += "\"nama\":\"" + namalelang + "\",";
            jsonstring += "\"peserta\":";
            String[] peserta = getPesertaPerLelang(kodelelang[i]);
            jsonstring += "[";
            for (int j = 0; j < peserta.length; j++) {
                jsonstring += "\"" + ((peserta[j]).replace(",","")).replace("\"","") + "\"";
                System.out.println(peserta[j]);
                if (j < peserta.length - 1) {
                    jsonstring += ",";
                }
            }

            jsonstring += "],";
            jsonstring += "\"jumlahpeserta\":" + peserta.length + ",";
            int label = 0;
            if (i <= 496) {
                label = 1;
            }
            else {
                label = 0;
            }
            jsonstring += "\"label\":" + label + ",";
            jsonstring += "}";
            if (i < kodelelang.length - 1) {
                jsonstring += ",";
            }
            System.out.println(kodelelang[i]);
        }
        jsonstring += "]}";

        writer.append(jsonstring);
        writer.flush();
        writer.close();
    }

    public static int[] getAllJumlahPeserta(int[] kodelelang) throws IOException {
        Connection connect = null;
        Statement statement = null;
        int[] jumlahpeserta = new int[kodelelang.length];
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            for (int i = 0; i < kodelelang.length; i++) {
                String query = "select count(nama) from  peserta as  jumpeserta where  lelangnum = " + kodelelang[i];
                ResultSet result = statement.executeQuery(query);
                while (result.next()) {
                    //Retrieve by column name
                    jumlahpeserta[i] = result.getInt(1);
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
        return jumlahpeserta;
    }

    public static String getNamaLelang(int kodelelang) throws IOException {
        Connection connect = null;
        Statement statement = null;
        String nama = "";
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            String query = "select nama from  peserta where  lelangnum = " + kodelelang;
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                 nama = result.getString(1);
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
        return nama;
    }

    public static float[][] isiMatrix2(int[] kodelelang) throws IOException {
        float[][] matrix = new float[kodelelang.length][kodelelang.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = -999;
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    if (matrix[i][j] == -999) {
                       matrix[i][j] = getSimilarity(kodelelang[i], kodelelang[j]);
                       matrix[j][i] = matrix[i][j];
                    }
                }
                System.out.println(i + "," + j);
            }
        }
        return matrix;
    }
}