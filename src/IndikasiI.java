/**
 * Created by Rosiana on 5/3/2016.
 */

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import java.util.Properties;

import java.sql.*;

public class IndikasiI {

    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static String[][] getAllSatker() throws IOException {
        String stringsatker[][] = new String[1][4];
        int jumsatker = 0;
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

            String query = "SELECT count(*) from (select distinct lpse, agency, satker jumsatker from lelang where lpse = \"Kementerian Keuangan\") al";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                jumsatker = result.getInt(1);
            }
            result.close();
            stringsatker = new String[jumsatker][4];
            query = "SELECT distinct lpse, agency, satker from lelang where lpse = \"Kementerian Keuangan\"";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                stringsatker[i][0] = result.getString(1);
                stringsatker[i][1] = result.getString(2);
                stringsatker[i][2] = result.getString(3);
                System.out.println(stringsatker[i][0] + " - " + stringsatker[i][1] + " - " + stringsatker[i][2]);
                i++;
            }
            result.close();

            for (int j = 0; j < stringsatker.length; j++) {
                query = "SELECT count(*) from (select id from lelang where satker = \"" + stringsatker[j][2] + "\") al";
                result = statement.executeQuery(query);
                while (result.next()) {
                    //Retrieve by column name
                    stringsatker[j][3] = result.getString(1);
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
        return stringsatker;
    }

    public static String[][] getLelangPerSatker(String satker, int jumlelang) throws IOException {
        System.out.println("satker" + satker);
        int jumpeserta = getJumLelangPerSatker(satker);
        String stringpeserta[][] = new String[jumpeserta][5];
        int idxpeserta = 0;
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

            String query = "SELECT distinct peserta.nama from lelang join peserta where lelang.id = peserta.idlelang and lelang.satker = \"" + satker + "\"";
            ResultSet result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                stringpeserta[i][0] = result.getString(1);
                i++;
            }
            result.close();
            for (int j = 0; j < stringpeserta.length; j++) {
                int jumpenawaran = 0;
                query = "select count(*) from (select al2.id from lelang as al2 join peserta as al3 where al2.id = al3.idlelang and al2.satker = \"" + satker + "\" and al3.nama = \"" + stringpeserta[j][0] + "\" group by al2.id) as al";
                result = statement.executeQuery(query);
                while (result.next()) {
                    jumpenawaran = result.getInt(1);
                }
                result.close();
                stringpeserta[j][1] = jumpenawaran + "";
                String stringidpenawaran = "";
                query = "SELECT lelang.id from lelang join peserta where lelang.id = peserta.idlelang and lelang.satker = \"" + satker + "\" and peserta.nama = \"" + stringpeserta[j][0] + "\"";
                result = statement.executeQuery(query);
                while (result.next()) {
                    //Retrieve by column name
                    stringidpenawaran += result.getString(1);
                    stringidpenawaran += ", ";
                }
                result.close();
                stringpeserta[j][3] = stringidpenawaran.substring(0, stringidpenawaran.length()-2);

                int jumlahmenang = 0;
                query = "select count(*) from (select al2.id from lelang as al2 join peserta as al3 where al2.id = al3.idlelang and al2.satker = \"" + satker + "\" and al3.nama = \"" + stringpeserta[j][0] + "\" and al2.pemenang = \"" + stringpeserta[j][0] + "\" group by al2.id) as al";
                result = statement.executeQuery(query);
                while (result.next()) {
                    //Retrieve by column name
                    jumlahmenang = result.getInt(1);
                }
                result.close();
                if (jumlahmenang > 0) {
                    stringpeserta[j][2] = jumlahmenang + "";
                    String stringidmenang = "";
                    query = "SELECT lelang.id from lelang join peserta where lelang.id = peserta.idlelang and lelang.satker = \"" + satker + "\" and peserta.nama = \"" + stringpeserta[j][0] + "\" and lelang.pemenang = \"" + stringpeserta[j][0] + "\"";
                    result = statement.executeQuery(query);
                    while (result.next()) {
                        //Retrieve by column name
                        stringidmenang += result.getString(1);
                        stringidmenang += ", ";
                    }
                    result.close();
                    stringpeserta[j][4] = stringidmenang.substring(0, stringidmenang.length() - 2);
                }
                else {
                    stringpeserta[j][2] = "0";
                    stringpeserta[j][4] = "-";
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
        return stringpeserta;
    }

    public static String[][] getLelangPerSatkerNew(String satker, int jumlelang) throws IOException {
        System.out.println("satker" + satker);
        int jumpeserta = getJumLelangPerSatker(satker);
        String stringpeserta[][] = new String[jumpeserta][5];
        int idxpeserta = 0;
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

            String query = "SELECT distinct peserta.nama from lelang join peserta where lelang.id = peserta.idlelang and lelang.satker = \"" + satker + "\"";
            ResultSet result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                stringpeserta[i][0] = result.getString(1);
                i++;
            }
            result.close();
            for (int j = 0; j < stringpeserta.length; j++) {
                int jumlahmenang = 0;
                query = "select count(*) from (select al2.id from lelang as al2 join peserta as al3 where al2.id = al3.idlelang and al2.satker = \"" + satker + "\" and al3.nama = \"" + stringpeserta[j][0] + "\" and al2.pemenang = \"" + stringpeserta[j][0] + "\" group by al2.id) as al";
                result = statement.executeQuery(query);
                while (result.next()) {
                    //Retrieve by column name
                    jumlahmenang = result.getInt(1);
                }
                result.close();
                if (jumlahmenang > 0) {
                    stringpeserta[j][1] = jumlahmenang + "";
                    String stringidmenang = "";
                    query = "SELECT lelang.id from lelang join peserta where lelang.id = peserta.idlelang and lelang.satker = \"" + satker + "\" and peserta.nama = \"" + stringpeserta[j][0] + "\" and lelang.pemenang = \"" + stringpeserta[j][0] + "\"";
                    result = statement.executeQuery(query);
                    while (result.next()) {
                        //Retrieve by column name
                        stringidmenang += result.getString(1);
                        stringidmenang += ", ";
                    }
                    result.close();
                    stringpeserta[j][2] = stringidmenang.substring(0, stringidmenang.length() - 2);
                }
                else {
                    stringpeserta[j][2] = "0";
                    stringpeserta[j][4] = "-";
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
        return stringpeserta;
    }

    public static int getJumLelangPerSatker(String satker) throws IOException {
        int jumpeserta = 0;
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

            String query = "select count(*) from (SELECT count(al2.nama) from lelang as al3 join peserta as al2 on al3.id = al2.idlelang and al3.satker = \"" + satker + "\" group by al2.nama) as al";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                jumpeserta = result.getInt(1);
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
        return jumpeserta;
    }

    public static void unitString(String[][] satker) throws IOException {
        String unit[] = new String[9];
        for (int j = 0; j < satker.length; j++) {
            System.out.println(satker[j][2]);
            int satkerlelang = Integer.parseInt(satker[j][3]);
            String[][] persatker = getLelangPerSatker(satker[j][2], satkerlelang);
            for (int k = 0; k < persatker.length; k++) {
                unit[0] = satker[j][0];
                unit[1] = satker[j][1];
                unit[2] = satker[j][2];
                unit[3] = satker[j][3];
                unit[4] = persatker[k][0];
                unit[5] = persatker[k][1];
                unit[6] = persatker[k][2];
                unit[7] = persatker[k][3];
                unit[8] = persatker[k][4];
                System.out.println(unit[0] + " - " + unit[1] + " - " + unit[2] + " - " + unit[3] + " - " + unit[4] + " - " + unit[5] + " - " + unit[6] + " - " + unit[7] + " - " + unit[8]);
                dbTulisIndikasiI(unit);
            }
        }
    }

    public static void unitStringNew(String[][] satker) throws IOException {
        String unit[] = new String[9];
        for (int j = 0; j < satker.length; j++) {
            System.out.println(satker[j][2]);
            int satkerlelang = Integer.parseInt(satker[j][3]);
            String[][] persatker = getLelangPerSatkerNew(satker[j][2], satkerlelang);
            for (int k = 0; k < persatker.length; k++) {
                unit[0] = satker[j][0];
                unit[1] = satker[j][1];
                unit[2] = satker[j][2];
                unit[3] = satker[j][3];
                unit[4] = persatker[k][0];
                unit[5] = persatker[k][1];
                unit[6] = persatker[k][2];
                System.out.println(unit[0] + " - " + unit[1] + " - " + unit[2] + " - " + unit[3] + " - " + unit[4] + " - " + unit[5] + " - " + unit[6]);
                dbTulisIndikasiINew(unit);
            }
        }
    }

    public static void dbTulisIndikasiI(String[] unitstring) throws IOException {
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

            preparedStatement = connect.prepareStatement("insert into  indikasi1tes values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, unitstring[0]);
            preparedStatement.setString(2, unitstring[1]);
            preparedStatement.setString(3, unitstring[2]);
            preparedStatement.setInt(4, Integer.parseInt(unitstring[3]));
            preparedStatement.setString(5, unitstring[4]);
            preparedStatement.setInt(6, Integer.parseInt(unitstring[5]));
            preparedStatement.setInt(7, Integer.parseInt(unitstring[6]));
            preparedStatement.setString(8, unitstring[7]);
            if (unitstring[8].equals("-")) {
                preparedStatement.setObject(9, null);
            }
            else {
                preparedStatement.setString(9, unitstring[8]);
            }
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

    public static void dbTulisIndikasiINew(String[] unitstring) throws IOException {
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

            preparedStatement = connect.prepareStatement("insert into  indikasi1tes2 values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, unitstring[0]);
            preparedStatement.setString(2, unitstring[1]);
            preparedStatement.setString(3, unitstring[2]);
            preparedStatement.setInt(4, Integer.parseInt(unitstring[3]));
            preparedStatement.setString(5, unitstring[4]);
            preparedStatement.setInt(6, Integer.parseInt(unitstring[5]));
            preparedStatement.setString(7, unitstring[6]);
            if (Integer.parseInt(unitstring[5]) > 0) {
                preparedStatement.addBatch();
                preparedStatement.executeBatch();
                System.out.println("Masuk Batch");
            }
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

    public static void IndikasiI() throws IOException {
        String[][] satker = getAllSatker();
        unitStringNew(satker);
        System.out.println("Selesai");
    }
}