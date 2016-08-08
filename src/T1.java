/**
 * Created by Rosiana on 5/3/2016.
 */

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.Properties;

import java.sql.*;

public class T1 {

    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/fdproject";
    static final String user = "root";
    static final String pass = "";

    public static String getMulaiPeriodeLelang(int kodelelang) throws IOException {
        String stringmulai = "";
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

            Timestamp mulai = new Timestamp(0000,00,00,00,00,00,000000000);

            String query = "select  mulai from  tahap where  lelangnum = " + kodelelang + " limit 1";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                mulai = result.getTimestamp(1);
            }
            result.close();
            stringmulai = (String.valueOf(mulai)).substring(0, (String.valueOf(mulai)).length()-2);
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
        return stringmulai;
    }

    public static String getSampaiPeriodeLelang(int kodelelang) throws IOException {
        String stringsampai = "";
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

            Timestamp sampai = new Timestamp(0000,00,00,00,00,00,000000000);

            String query = "select  sampai from  tahap where  lelangnum = " + kodelelang + " and tahap = \"Penandatanganan Kontrak\"";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                sampai = result.getTimestamp(1);
            }
            result.close();
            stringsampai = (String.valueOf(sampai)).substring(0, (String.valueOf(sampai)).length()-2);
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
        return stringsampai;
    }

    public static float[] getAllPeriodeLelang(int[] kodelelang) throws IOException {
        float[] periode = new float[kodelelang.length];
        for (int i = 0; i < kodelelang.length; i++) {
            int lelangnum = kodelelang[i];
            System.out.println("periode " + lelangnum);
            String mulai = getMulaiPeriodeLelang(lelangnum);
            System.out.println(mulai);
            String sampai = getSampaiPeriodeLelang(lelangnum);
            System.out.println(sampai);
            periode[i] = getPeriode2(mulai,sampai);
            System.out.println(periode[i]);
        }
        return periode;
    }

    public static float[] getAllMeanSelisihTahap(int[] kodelelang) throws IOException {
        float[] ptahap = new float[kodelelang.length];
        for (int i = 0; i < kodelelang.length; i++) {
            int lelangnum = kodelelang[i];
            System.out.println("selisih " + lelangnum);
            ptahap[i] = getMeanSelisihTahap(lelangnum);
        }
        return ptahap;
    }

    public static float getMeanSelisihTahap(int kodelelang) throws IOException {
        float mean = 0;
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

            int numtahap = 0;
            String query = "select distinct count(lelangnum) from  tahap as  numtahap where  lelangnum = " + kodelelang;
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                numtahap = result.getInt(1);
            }
            result.close();

            String[] smulai = new String[numtahap-1];
            int mintahap = numtahap - 1;
            query = "select  mulai from  tahap where  lelangnum = " + kodelelang + " limit " + mintahap;
            result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                //Retrieve by column name
                Timestamp mulai = new Timestamp(0000,00,00,00,00,00,000000000);
                mulai = result.getTimestamp(1);
                smulai[i] = String.valueOf(mulai);
                i++;
            }
            result.close();

            String[] ssampai = new String[numtahap-1];
            query = "select  mulai from  tahap where  lelangnum = " + kodelelang + " limit " + numtahap + " offset 1";
            result = statement.executeQuery(query);
            i = 0;
            while (result.next()) {
                //Retrieve by column name
                Timestamp sampai = new Timestamp(0000,00,00,00,00,00,000000000);
                sampai = result.getTimestamp(1);
                ssampai[i] = String.valueOf(sampai);
                i++;
            }
            result.close();

            for (int j = 0; j < numtahap-1; j++) {
                float x = getPeriode2(smulai[j],ssampai[j]);
                mean += x;
            }
            mean = mean/numtahap;
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
        return mean;
    }

    public static float getPeriode(String mulai, String sampai) throws IOException {
        float periode = 0;
        mulai = mulai.replace(".0", "");
        String datemulai = ((mulai).substring(0, mulai.length()-8).replace(" ","")).replace("-", "");
        String timemulai = ((mulai).substring(mulai.length()-8).replace(":", "")).replace(" ","");
        int yyyymulai = Integer.valueOf(datemulai.substring(0,datemulai.length()-4));
        int mmmulai = Integer.valueOf(datemulai.substring(4, datemulai.length()-2));
        int yyyymulaitemp = Integer.valueOf(datemulai.substring(0, datemulai.length()-4));
        int mmmulaitemp = Integer.valueOf(datemulai.substring(4, datemulai.length()-2));
        int ddmulai = Integer.valueOf(datemulai.substring(6, datemulai.length()));
        int hhmulai = Integer.valueOf(timemulai.substring(0, 2));
        int minmulai = Integer.valueOf(timemulai.substring(2, 4));
        sampai = sampai.replace(".0", "");
        String datesampai = ((sampai).substring(0, sampai.length()-8).replace(" ","")).replace("-", "");
        String timesampai = ((sampai).substring(sampai.length()-8).replace(":", "")).replace(" ","");
        int yyyysampai = Integer.valueOf(datesampai.substring(0, datesampai.length()-4));
        int mmsampai = Integer.valueOf(datesampai.substring(4, datesampai.length()-2));
        int yyyysampaitemp = Integer.valueOf(datesampai.substring(0, datesampai.length()-4));
        int mmsampaitemp = Integer.valueOf(datesampai.substring(4, datesampai.length()-2));
        int ddsampai = Integer.valueOf(datesampai.substring(6, datemulai.length()));
        int hhsampai = Integer.valueOf(timesampai.substring(0, 2));
        int minsampai = Integer.valueOf(timesampai.substring(2, 4));
        float min = 0;
        float hour = 0;
        float date = 0;
        float mon = 0;
        float year = 0;
        if (minsampai >= minmulai) {
            min = minsampai - minmulai;
        }
        else {
            min = 60 + minsampai - minmulai;
            hhsampai -= 1;
        }
        if (hhsampai >= hhmulai) {
            hour = hhsampai - hhmulai;
        }
        else {
            hour = 24 + minsampai - minmulai;
            ddsampai -= 1;
        }
        if (ddsampai >= ddmulai) {
            date = ddsampai - ddmulai;
        }
        else {
            if (mmmulai == 12 || mmmulai+1 == 3 || mmmulai+1 == 5 || mmmulai+1 == 7 || mmmulai+1 == 8 || mmmulai+1 == 10 || mmmulai+1 == 12) {
                date = 31 + ddsampai - ddmulai;
            }
            else {
                if (mmmulai+1 == 4 || mmmulai+1 == 6 || mmmulai+1 == 9 || mmmulai+1 == 11) {
                    date = 30 + ddsampai - ddmulai;
                }
                else {
                    if (((yyyysampai%100 == 0) && (yyyysampai%4 != 0)) || ((yyyysampai%100 != 0) && (yyyysampai%4 == 0))) {
                        date = 29 + ddsampai - ddmulai;
                    }
                    else {
                        date = 28 + ddsampai - ddmulai;
                    }
                }
            }
            mmsampai -= 1;
        }
        if (mmsampai >= mmmulai) {
            mon = mmsampai - mmmulai;
        }
        else {
            mon = 12 + mmsampai - mmmulai;
            yyyysampai -= 1;
        }
        year = yyyysampai - yyyymulai;
        periode += year*365;
        if (mon >= 1) {
            if (yyyymulaitemp == yyyysampaitemp) {
                for (int j = mmmulaitemp; j <= mmsampaitemp; j++) {
                    if (j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10 || j == 12) {
                        periode += 31;
                    }
                    else {
                        if (j == 4 || j == 6 || j == 9 || j == 11) {
                            periode += 30;
                        }
                        else {
                            if (((yyyysampaitemp%100 == 0) && (yyyysampaitemp%4 != 0)) || ((yyyysampaitemp%100 != 0) && (yyyysampaitemp%4 == 0))) {
                                periode += 29;
                            }
                            else {
                                periode += 28;
                            }
                        }
                    }
                }
            }
            else {
                for (int j = mmmulaitemp; j <= 12; j++) {
                    if (j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10 || j == 12) {
                        periode += 31;
                    }
                    else {
                        if (j == 4 || j == 6 || j == 9 || j == 11) {
                            periode += 30;
                        }
                        else {
                            if (((yyyymulaitemp%100 == 0) && (yyyymulaitemp%4 != 0)) || ((yyyymulaitemp%100 != 0) && (yyyymulaitemp%4 == 0))) {
                                periode += 29;
                            }
                            else {
                                periode += 28;
                            }
                        }
                    }
                }
                for (int j = 1; j <= mmsampaitemp; j++) {
                    if (j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10 || j == 12) {
                        periode += 31;
                    }
                    else {
                        if (j == 4 || j == 6 || j == 9 || j == 11) {
                            periode += 30;
                        }
                        else {
                            if (((yyyysampaitemp%100 == 0) && (yyyysampaitemp%4 != 0)) || ((yyyysampaitemp%100 != 0) && (yyyysampaitemp%4 == 0))) {
                                periode += 29;
                            }
                            else {
                                periode += 28;
                            }
                        }
                    }
                }
            }
        }
        periode += date;
        periode += hour/24;
        periode += min/1440;
        if (periode < 0) {
            periode = 0;
        }
        return periode;
    }

    public static float getPeriode2(String mulai, String sampai) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date d1 = null;
        Date d2 = null;

        float diffDays = 0;

        try {
            d1 = format.parse(mulai);
            d2 = format.parse(sampai);

            //in milliseconds
            float diff = d2.getTime() - d1.getTime();

            diffDays = diff / (24 * 60 * 60 * 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return diffDays;
    }

    public static void dbInsertT1(int[] kodelelang) throws IOException {
        //mysql
        float[] periodelelang = getAllPeriodeLelang(kodelelang);
        float[] meanselisihtahap = getAllMeanSelisihTahap(kodelelang);
        float[] tempperiode = periodelelang;
        float[] tempmean = meanselisihtahap;
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);

            preparedStatement = connect.prepareStatement("insert into  t1 values (?, ?, ?)");
            for (int i = 0; i < kodelelang.length; i++) {
                int lelangnum = kodelelang[i];
                preparedStatement.setInt(1, lelangnum);
                preparedStatement.setFloat(2, tempperiode[i]);
                preparedStatement.setFloat(3, tempmean[i]);
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

    public static void emptyT1() throws IOException {
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);

            preparedStatement = connect.prepareStatement("delete from  t1");

            preparedStatement.executeUpdate();
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

    public static float getLowerFence(int[] kodelelang, String by) throws IOException {
        float lfence = 0;
        float[] sorted = sort(kodelelang, by);
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
        lfence = q1 - 3/2*iqr;
        System.out.println("lfence " + by);
        System.out.println("jum " + sorted.length);
        for (int i = 0; i < sorted.length; i++) {
            System.out.println(sorted[i]);
        }
        System.out.println("q1 " + q1);
        System.out.println("q3 " + q3);
        System.out.println("iqr " + iqr);
        System.out.println("lw " + lfence);
        return lfence;
    }

    public static float getUpperFence(int[] kodelelang, String by) throws IOException {
        float ufence = 0;
        float[] sorted = sort(kodelelang, by);
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
        System.out.println("ufence " + by);
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

    public static float[] sort(int[] kodelelang, String by) throws IOException {
        float[] sort = new float[kodelelang.length];
        if (by == "periodelelang") {
            sort = getAllPeriodeLelang(kodelelang);
        }
        else {
            sort = getAllMeanSelisihTahap(kodelelang);
        }
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

    public static void getJSONT1Periode() throws IOException{

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

            String query = "select  lelang.id,  lelang.nama,  lelang.lpse,  lelang.tahun,  lelang.status,  lelang.pagu,  lelang.hps,  lelang.penawaranmenang,  lelang.pemenang,  t1.periodelelang from  lelang join  t1 on lelang.id = t1.lelangnum";
            ResultSet result = statement.executeQuery(query);
            FileWriter writer = new FileWriter("web/json/new/v1_t1periode.json");
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
                jsonstring += "\"periodelelang\":" + result.getFloat(10);
                jsonstring += "},";
                i++;
            }
            jsonstring += ("]");
            jsonstring.replace("},]", "}]");

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

    public static void getJSONT1MeanSelisih() throws IOException{

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

            String query = "select  lelang.id,  lelang.nama,  lelang.lpse,  lelang.tahun,  lelang.status,  lelang.pagu,  lelang.hps,  lelang.penawaranmenang,  lelang.pemenang,  t1.meanselisihtahap from  lelang join  t1 on lelang.id = t1.lelangnum";
            ResultSet result = statement.executeQuery(query);
            FileWriter writer = new FileWriter("web/json/new/v1_t1meanselisih.json");
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
                jsonstring += "\"meanselisihtahap\":" + result.getFloat(10);
                jsonstring += "},";
                i++;
            }
            jsonstring += ("]");
            jsonstring.replace("},]", "}]");

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
