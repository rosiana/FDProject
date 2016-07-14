/**
 * Created by Rosiana on 7/3/2016.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Date;
import java.util.Scanner;
import java.util.Properties;

import java.sql.*;

public class T4 {

    //mysql cred
    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/fdproject";
    static final String user = "root";
    static final String pass = "";

    public static String getMulaiPenetapanPemenang(int kodelelang) throws IOException {
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

            String query = "select  mulai from  tahap where  lelangnum = " + kodelelang + " and  tahap = \"Penetapan Pemenang\"";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                mulai = result.getTimestamp(1);
            }
            result.close();
            stringmulai = String.valueOf(mulai);
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

    public static String getSampaiPenetapanPemenang(int kodelelang) throws IOException {
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

            String query = "select  sampai from  tahap where  lelangnum = " + kodelelang + " and  tahap = \"Penetapan Pemenang\"";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                //Retrieve by column name
                sampai = result.getTimestamp(1);
            }
            result.close();
            stringsampai = String.valueOf(sampai);
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

    public static float[] getAllPeriodePenetapanPemenang(String[] kodelelang) throws IOException {
        float[] periode = new float[kodelelang.length];
        for (int i = 0; i < kodelelang.length; i++) {
            System.out.println(kodelelang[i].substring(2));
            int lelangnum = Integer.parseInt(kodelelang[i].substring(2));
            String mulai = getMulaiPenetapanPemenang(lelangnum);
            String sampai = getSampaiPenetapanPemenang(lelangnum);
            periode[i] = getPeriode(mulai,sampai);
        }
        return periode;
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

    public static int[] getOutlierPenetapanPemenang (String[] kodelelang) throws IOException {
        int[] outlierp = new int[kodelelang.length];
        for (int i = 0; i < outlierp.length; i++) {
            outlierp[i] = 0;
        }
        float[] data = new float[kodelelang.length];
        data = getAllPeriodePenetapanPemenang(kodelelang);
        float ufence = getUpperFence(kodelelang);
        for (int j = 0; j < data.length; j++) {
            if (data[j] > ufence) {
                outlierp[j] = 1;
            }
            else {
                outlierp[j] = 0;
            }
        }
        return outlierp;
    }

    public static void dbInsertT4(String[] kodelelang) throws IOException {
        //mysql
        float[] periodepenetapan = getAllPeriodePenetapanPemenang(kodelelang);
        float[] tempperiode = periodepenetapan;
        int[] outlierp = getOutlierPenetapanPemenang(kodelelang);
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);

            preparedStatement = connect.prepareStatement("insert into  t4 values (?, ?, ?)");
            for (int i = 0; i < kodelelang.length; i++) {
                int lelangnum = Integer.parseInt(kodelelang[i].substring(2));
                preparedStatement.setInt(1, lelangnum);
                preparedStatement.setFloat(2, tempperiode[i]);
                if (outlierp[i] == 0) {
                    preparedStatement.setObject(3, null);
                }
                else {
                    preparedStatement.setInt(3, outlierp[i]);
                }
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

    public static void emptyT4() throws IOException {
        Connection connect = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(myDriver);
            Properties props = new Properties();
            props.put("user", user);
            props.put("password", pass);
            props.put("autoReconnect", "true");
            connect = DriverManager.getConnection(myUrl, props);

            preparedStatement = connect.prepareStatement("delete from  t4");

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

    public static float getUpperFence(String[] kodelelang) throws IOException {
        float ufence = 0;
        float[] sorted = sort(kodelelang);
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

    public static float[] sort(String[] kodelelang) throws IOException {
        float[] sort = new float[kodelelang.length];
        sort = getAllPeriodePenetapanPemenang(kodelelang);
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
}

