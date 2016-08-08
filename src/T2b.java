/**
 * Created by Rosiana on 7/3/2016.
 */

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Date;
import java.util.Scanner;
import java.util.Properties;
import java.lang.Math;

import java.sql.*;

public class T2b {

    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/fdproject";
    static final String user = "root";
    static final String pass = "";

    public static String[][] getAllHPS() throws IOException {

        String[][] hps = new String[1][2];
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

            String query = "select count(id) from  lelang as  numlelang";
            ResultSet result = statement.executeQuery(query);
            int numlelang = 0;
            while (result.next()) {
                numlelang = result.getInt(1);
            }
            result.close();
            hps = new String[numlelang][2];

            query = "select  id,  hps from  lelang";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                hps[i][0] = result.getString(1);
                hps[i][1] = result.getString(2);
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
        return hps;
    }

    public static float[] getAllHargaMenang() throws IOException {

        float[] hargamenang = new float[1];
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

            String query = "select count(id) from  lelang as  numlelang";
            ResultSet result = statement.executeQuery(query);
            int numlelang = 0;
            while (result.next()) {
                numlelang = result.getInt(1);
            }
            result.close();
            hargamenang = new float[numlelang];

            query = "select  penawaranmenang from  lelang";
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                hargamenang[i] = result.getFloat(1);
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
        return hargamenang;
    }

    public static float[] menangPerHPS(String[][] hps, float[] menang) throws IOException {
        float[] mph = new float[menang.length];
        for (int i = 0; i < mph.length; i++) {
            float hpsf = Float.parseFloat(hps[i][1]);
            mph[i] = (menang[i] / hpsf);
        }
        return mph;
    }

    //outlier biasa
    public static float[] getOutlierMPH (float[] mph) throws IOException {
        float ufence = getUpperFence(mph);
        float[] outlier = new float[mph.length];
        for (int i = 0; i < outlier.length; i++) {
            if (mph[i] > ufence) {
                outlier[i] = 1;
            }
            else {
                outlier[i] = 0;
            }
        }
        return outlier;
    }

    //outlier berdasarkan stdev
    public static float[] getOutlierMPH2 (float[] mph) throws IOException {
        float mean = getMean(mph);
        System.out.println("mean " + mean);
        float stdev = getStdev(mph);
        System.out.println("stdev " + stdev);
        float[] outlier = new float[mph.length];
        for (int i = 0; i < outlier.length; i++) {
            System.out.println("mph " + mph[i]);
            if (mph[i] > (mean + stdev)) {
                outlier[i] = 1;
            }
            else {
                outlier[i] = 0;
            }
            System.out.println(outlier[i]);
        }
        return outlier;
    }

    public static float getMean(float[] mph) throws IOException {
        float sum = 0;
        for (int i = 0; i < mph.length; i++) {
            sum += mph[i];
        }
        return sum / mph.length;
    }

    public static float getStdev(float[] mph) throws IOException {
        double stdev = 0;
        float mean = getMean(mph);
        float[] xmean = new float[mph.length];
        for (int i = 0; i < xmean.length; i++) {
            xmean[i] = (mph[i] - mean)*(mph[i] - mean);
        }
        float sumxmean = 0;
        for (int j = 0; j < xmean.length; j++) {
            sumxmean += xmean[j];
        }
        float sperx = sumxmean / xmean.length;
        stdev = Math.pow((double)sperx, 0.5);
        float fstdev = (float)stdev;
        return fstdev;
    }

    public static float getUpperFence(float[] mph) throws IOException {
        float ufence = 0;
        float[] sorted = sort(mph);
        float q1 = 0;
        float q3 = 0;
        float iqr = 0;
        int x = sorted.length;
        int y = sorted.length % 4;
        switch (y) {
            case 0:
                q1 = (sorted[x/4] + sorted[x/4 + 1]) / 2;
                q3 = (sorted[3*x/4] + sorted[3*x/4 + 1]) / 2;
                break;
            case 1:
                q1 = (sorted[x/4] + sorted[x/4 + 1]) / 2;
                q3 = (sorted[3*x/4 + 1] + sorted[3*x/4 + 2]) / 2;
                break;
            case 2:
                q1 = sorted[x/4 + 1];
                q3 = sorted[3*x/4 + 1];
                break;
            case 3:
                q1 = sorted[x/4 + 1];
                q3 = sorted[3*x/4 + 1];
                break;
            default:
                q1 = 0;
                q3 = 0;
        }
        iqr = q3 - q1;
        ufence = q3 + 3/2*iqr;
        System.out.println("ufence");
        System.out.println("jum " + sorted.length);
        for (int i = 0; i < sorted.length; i++) {
            System.out.println(sorted[i]);
        }
        System.out.println("q1 " + q1);
        System.out.println("q3 " + q3);
        System.out.println("iqr " + iqr);
        System.out.println("up " + ufence);
        return ufence;
    }

    public static float[] sort(float[] mph) throws IOException {
        float[] sort = new float[mph.length];
        sort = mph;
        //sort
        boolean swapped = true;
        int j = 0;
        float tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < sort.length - j; i++) {
                if (sort[i] > sort[i + 1]) {
                    tmp = sort[i];
                    sort[i] = sort[i + 1];
                    sort[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
        return sort;
    }

    public static void dbInsertT2b() throws IOException {
        //mysql
        float[] menang = getAllHargaMenang();
        String[][] hps = getAllHPS();
        float[] mph = menangPerHPS(hps, menang);
        float[] mphtemp = mph;

        Connection connect = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);

            preparedStatement = connect.prepareStatement("insert into  t2b values (?, ?)");
            for (int i = 0; i < mph.length; i++) {
                int lelangnum = Integer.parseInt(hps[i][0]);
                preparedStatement.setInt(1, lelangnum);
                float hpsf = Float.parseFloat(hps[i][1]);
                float mphx = menang[i] / hpsf;
                preparedStatement.setFloat(2, mphx);
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

    public static void getJSONT2b() throws IOException{

        JSONObject obj = new JSONObject();
        String jsonstring = "";
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

            String query = "select  lelang.id,  lelang.nama, lelang.lpse, lelang.tahun,  lelang.status,  lelang.pagu,  lelang.hps,  lelang.penawaranmenang,  lelang.pemenang,  t2b.menangperhps from  lelang join  t2b on lelang.id = t2b.lelangnum where lelang.id <= 326042";
            ResultSet result = statement.executeQuery(query);
            FileWriter writer = new FileWriter("web/json/new/v1_t2b.json");
            jsonstring += "[";
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                jsonstring += "{";
                jsonstring += "\"id\":" + result.getInt(1) + ",";
                jsonstring += "\"namalelang\":\"" + (result.getString(2)).replace("\"","") + "\",";
                jsonstring += "\"lpse\":\"" + result.getString(3) + "\",";
                jsonstring += "\"tahun\":" + result.getInt(4) + ",";
                int status = result.getInt(5);
                if (status == 0) {
                    jsonstring += "\"status\":\"Lelang sudah selesai\",";
                }
                else {
                    jsonstring += "\"status\":\"Lelang belum selesai\",";
                }
                jsonstring += "\"pagu\":" + result.getString(6) + ",";
                jsonstring += "\"hps\":" + result.getString(7) + ",";
                String penawaranmenang = result.getString(8);
                if (penawaranmenang == "null") {
                    jsonstring += "\"penawaranmenang\":\"-\",";
                }
                else {
                    jsonstring += "\"penawaranmenang\":" + penawaranmenang + ",";
                }
                String namapemenang = result.getString(9);
                if (penawaranmenang == "null") {
                    jsonstring += "\"namapemenang\":\"-\",";
                }
                else {
                    jsonstring += "\"namapemenang\":\"" + namapemenang + "\",";
                }
                jsonstring += "\"menangperhps\":" + result.getFloat(10);
                jsonstring += "},";
                i++;
            }
            jsonstring += ("]");
            jsonstring.replace(",]", "]");

            writer.append(jsonstring);

            result.close();
            writer.flush();
            writer.close();
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
