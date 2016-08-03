import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import java.util.Arrays;
import java.util.Collections;

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

    public static String[] getPesertaPerLelang(int kodelelang) throws IOException {
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

            int lelang = 0;

            for (int i = 0; i < matrix.length; i++) {
                int a = i + 1;
                query = "select distinct  lelangnum from  peserta limit " + a;
                result = statement.executeQuery(query);
                result.last();
                lelang = result.getInt(1);
                System.out.println(lelang);
                result.close();
                String[] listpeserta1 = getPesertaPerLelang(lelang);
                for (int j = 0; j < matrix.length; j++) {
                    int b = j + 1;
                    query = "select distinct  lelangnum from  peserta limit " + b;
                    result = statement.executeQuery(query);
                    result.last();
                    lelang = result.getInt(1);
                    System.out.println(lelang);
                    result.close();
                    String[] listpeserta2 = getPesertaPerLelang(lelang);
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

    public static float getSimilarity(String[] listpeserta1, String[] listpeserta2) throws IOException {
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
        int[] arrlelang = new int[45];
        int[] pesertaall = new int[250];
        arrlelang[0] = 209011;
        arrlelang[1] = 816011;
        arrlelang[2] = 1024011;
        arrlelang[3] = 1079011;
        arrlelang[4] = 2830011;
        arrlelang[5] = 2831011;
        arrlelang[6] = 3188011;
        arrlelang[7] = 6943011;
        arrlelang[8] = 6917011;
        arrlelang[9] = 7140011;
        arrlelang[10] = 11124011;
        arrlelang[11] = 7624011;
        arrlelang[12] = 10847011;
        arrlelang[13] = 10878011;
        arrlelang[14] = 10940011;
        arrlelang[15] = 11110011;
        arrlelang[16] = 11243011;
        arrlelang[17] = 13263011;
        arrlelang[18] = 13279011;
        arrlelang[19] = 16327011;
        arrlelang[20] = 16414011;
        arrlelang[21] = 16429011;
        arrlelang[22] = 16643011;
        arrlelang[23] = 16642011;
        arrlelang[24] = 16646011;
        arrlelang[25] = 15011;
        arrlelang[26] = 48011;
        arrlelang[27] = 73011;
        arrlelang[28] = 759025;
        arrlelang[29] = 757025;
        arrlelang[30] = 758025;
        arrlelang[31] = 4809025;
        arrlelang[32] = 158025;
        arrlelang[33] = 157025;
        arrlelang[34] = 1221025;
        arrlelang[35] = 156025;
        arrlelang[36] = 3829025;
        arrlelang[37] = 3928025;
        arrlelang[38] = 3894025;
        arrlelang[39] = 38025;
        arrlelang[40] = 438025;
        arrlelang[41] = 1239025;
        arrlelang[42] = 1468025;
        arrlelang[43] = 1717025;
        arrlelang[44] = 3964025;

        Connection connect = null;
        Statement statement = null;
        int[] lelang = new int[250];
        int numpeserta = 0;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);
            statement = connect.createStatement();

            String query = "select distinct  lelangnum from  peserta order by rand() limit 250";
            ResultSet result = statement.executeQuery(query);
            for (int l = 0; l < lelang.length; l++) {
                pesertaall[l] = arrlelang[l];
            }

            int[] pesertatemp = new int[250];
            int p = 0;
            while (result.next()) {
                pesertatemp[p] = result.getInt(1);
                p++;
            }
            result.close();
            for (int m = 0; m < pesertatemp.length; m++) {
                boolean found = false;
                loop:
                for (int n = 0; n < 45; n++) {
                    if (pesertatemp[m] == pesertaall[n]) {
                        found = true;
                        break loop;
                    }
                    else {

                    }
                }
                if (found == false && m < 206) {
                    System.out.println(m);
                    pesertaall[44 + m] = pesertatemp[m];
                }
            }

            for (int i = 0; i < pesertaall.length; i++) {
                System.out.println(pesertaall[i]);
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
            System.out.println(peserta[i] + " " + peserta.length);
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

        String namapeserta = "";

        for (int i = 0; i < matrix.length; i++) {
            String[] listpeserta1 = getPesertaPerLelang(kodelelang[i]);
            for (int j = 0; j < matrix.length; j++) {
                if (i == j) {
                    matrix[i][j] = 0;
                } else {
                    if (matrix[i][j] == -999) {
                        String[] listpeserta2 = getPesertaPerLelang(kodelelang[j]);
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