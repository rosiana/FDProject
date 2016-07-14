import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

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

            String query = "select distinct  nama from  peserta limit 20";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                numpeserta++;
            }
            result.close();
            matrixpeserta = new float[numpeserta][numpeserta];
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

    public static int[] getLelangPerPeserta(String peserta) throws IOException {
        Connection connect = null;
        Statement statement = null;
        int[] lelangpeserta = new int[1];
        int numlelang = 0;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            String query = "select lelangnum from  peserta where  nama = \"" + peserta + "\"";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                numlelang++;
            }
            result.close();

            lelangpeserta = new int[numlelang];
            query = "select  lelangnum from  peserta where  nama = \"" + peserta + "\"";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                lelangpeserta[i] = result.getInt(1);
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
        return lelangpeserta;
    }

    public static void isiMatrix(float[][] matrix) throws IOException {
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

            String query;
            ResultSet result;

            String namapeserta = "";

            for (int i = 0; i < matrix.length; i++) {
                int a = i + 1;
                query = "select distinct  nama from  peserta limit " + a;
                result = statement.executeQuery(query);
                result.last();
                namapeserta = result.getString(1);
                System.out.println(namapeserta);
                result.close();
                int[] listpeserta1 = getLelangPerPeserta(namapeserta);
                for (int j = 0; j < matrix.length; j++) {
                    int b = j + 1;
                    query = "select distinct  nama from  peserta limit " + b;
                    result = statement.executeQuery(query);
                    result.last();
                    namapeserta = result.getString(1);
                    System.out.println(namapeserta);
                    result.close();
                    int[] listpeserta2 = getLelangPerPeserta(namapeserta);
                    matrix[i][j] = getSimilarity(listpeserta1, listpeserta2);
                }
            }

            for (int k = 0; k < matrix.length; k++) {
                for (int l = 0; l < matrix.length; l++) {
                    System.out.printf(matrix[k][l] + " ");
                }
                System.out.printf("\n");
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
    }

    public static float getSimilarity(int[] listpeserta1, int[] listpeserta2) throws IOException {
        float sim = 0;
        float simx = 0;
        float max = 0;
        float same = 0;
        for (int i = 0; i < listpeserta1.length; i++) {
            loop:
            for (int j = 0; j < listpeserta2.length; j++) {
                if (listpeserta1[i] == listpeserta2[j]) {
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
        System.out.println("simx: " + simx);
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

            String query = "select distinct  nama from  peserta";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                numpeserta++;
            }
            result.close();
            peserta = new String[numpeserta];
            query = "select distinct  nama from  peserta";
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

    public static String[] getPesertaList2() throws IOException {
        int[] arrlelang = new int[20];
        String[] pesertaall = new String[500];
        arrlelang[0] = 23564014;
        arrlelang[1] = 24683014;
        arrlelang[2] = 25048014;
        arrlelang[3] = 25047014;
        arrlelang[4] = 25046014;
        arrlelang[5] = 25044014;
        arrlelang[6] = 25041014;
        arrlelang[7] = 25036014;
        arrlelang[8] = 23309014;
        arrlelang[9] = 23310014;
        arrlelang[10] = 23311014;
        arrlelang[11] = 23312014;
        arrlelang[12] = 23313014;
        arrlelang[13] = 23314014;
        arrlelang[14] = 23687014;
        arrlelang[15] = 22683014;
        arrlelang[16] = 25278014;
        arrlelang[17] = 24368014;
        arrlelang[18] = 24652014;
        arrlelang[19] = 22684014;

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

            String query = "select distinct  nama from  peserta where  (lelangnum = " + arrlelang[0] + " or  lelangnum = " +
                    arrlelang[1] + " or  lelangnum = " +
                    arrlelang[2] + " or  lelangnum = " +
                    arrlelang[3] + " or  lelangnum = " +
                    arrlelang[4] + " or  lelangnum = " +
                    arrlelang[5] + " or  lelangnum = " +
                    arrlelang[6] + " or  lelangnum = " +
                    arrlelang[7] + " or  lelangnum = " +
                    arrlelang[8] + " or  lelangnum = " +
                    arrlelang[9] + " or  lelangnum = " +
                    arrlelang[10] + " or  lelangnum = " +
                    arrlelang[11] + " or  lelangnum = " +
                    arrlelang[12] + " or  lelangnum = " +
                    arrlelang[13] + " or  lelangnum = " +
                    arrlelang[14] + " or  lelangnum = " +
                    arrlelang[15] + " or  lelangnum = " +
                    arrlelang[16] + " or  lelangnum = " +
                    arrlelang[17] + " or  lelangnum = " +
                    arrlelang[18] + " or  lelangnum = " +
                    arrlelang[19] + ")";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                numpeserta++;
            }
            result.close();
            peserta = new String[numpeserta];
            query = "select distinct  nama from  peserta where  (lelangnum = " + arrlelang[0] + " or  lelangnum = " +
                    arrlelang[1] + " or  lelangnum = " +
                    arrlelang[2] + " or  lelangnum = " +
                    arrlelang[3] + " or  lelangnum = " +
                    arrlelang[4] + " or  lelangnum = " +
                    arrlelang[5] + " or  lelangnum = " +
                    arrlelang[6] + " or  lelangnum = " +
                    arrlelang[7] + " or  lelangnum = " +
                    arrlelang[8] + " or  lelangnum = " +
                    arrlelang[9] + " or  lelangnum = " +
                    arrlelang[10] + " or  lelangnum = " +
                    arrlelang[11] + " or  lelangnum = " +
                    arrlelang[12] + " or  lelangnum = " +
                    arrlelang[13] + " or  lelangnum = " +
                    arrlelang[14] + " or  lelangnum = " +
                    arrlelang[15] + " or  lelangnum = " +
                    arrlelang[16] + " or  lelangnum = " +
                    arrlelang[17] + " or  lelangnum = " +
                    arrlelang[18] + " or  lelangnum = " +
                    arrlelang[19] + ")";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                peserta[i] = result.getString(1);
                i++;
            }
            result.close();

            query = "select distinct  nama from  peserta limit 500";
            result = statement.executeQuery(query);
            for (int l = 0; l < peserta.length; l++) {
                pesertaall[l] = peserta[l];
            }

            String[] pesertatemp = new String[500];
            int p = 0;
            while (result.next()) {
                pesertatemp[p] = result.getString(1);
                System.out.println("pesertatemp: " + pesertatemp[p]);
                p++;
            }
            result.close();
            for (int m = 0; m < pesertatemp.length; m++) {
                boolean found = false;
                for (int n = 0; n < 20; n++) {
                    if (pesertatemp[m] == pesertaall[n]) {
                        found = true;
                    }
                    n++;
                }
                if (found == false) {
                    pesertaall[peserta.length + m] = pesertatemp[m];
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
        return pesertaall;
    }

    public static void getJSONT2a(float[][] matrix, String[] peserta) throws IOException {
        JSONObject obj = new JSONObject();
        String jsonstring = "";

        FileWriter writer = new FileWriter("web/json/t2a1.json");

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
        jsonstring += "\"peserta\":[";
        for (int i = 0; i < peserta.length; i++) {
            jsonstring += "{";
            jsonstring += "\"nama\":\"" + peserta[i] + "\",";
            jsonstring += "\"lelang\":";
            int[] lelang = getLelangPerPeserta(peserta[i]);
            jsonstring += "[";
            for (int j = 0; j < lelang.length; j++) {
                jsonstring += "" + lelang[j];
                if (j < lelang.length - 1) {
                    jsonstring += ",";
                }
            }
            jsonstring += "]";
            jsonstring += "\"jumlahlelang\":\"" + lelang.length + "\"";
            jsonstring += "}";
            if (i < peserta.length - 1) {
                jsonstring += ",";
            }
        }
        jsonstring += "]}";

        writer.append(jsonstring);
        writer.flush();
        writer.close();
    }

    public static float[][] isiMatrix2(String[] peserta) throws IOException {
        float[][] matrix = new float[peserta.length][peserta.length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                matrix[i][j] = -999;
            }
        }

        String namapeserta = "";

        for (int i = 0; i < matrix.length; i++) {
            int[] listpeserta1 = getLelangPerPeserta(peserta[i]);
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    if (matrix[i][j] == -999) {
                        int[] listpeserta2 = getLelangPerPeserta(peserta[j]);
                        matrix[i][j] = getSimilarity(listpeserta1, listpeserta2);
                        matrix[j][i] = matrix[i][j];
                    }
                }
                System.out.println(i + "," + j);
            }
        }
    return matrix;
    }
}