/**
 * Created by Rosiana on 5/3/2016.
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Date;
import java.util.Scanner;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.*;

public class Main {
    static Crawler crawler = new Crawler();
    static T1 t1 = new T1();

    public static void main (String args[]) throws IOException {
        int pagenum = crawler.getPageNum("lpse.itb.ac.id");
        String[] kodelelang = crawler.getKodeLelang("lpse.itb.ac.id", pagenum);
        float[] periodelelang = t1.getAllPeriodeLelang(kodelelang);
        float[] meanselisihtahap = t1.getAllMeanSelisihTahap(kodelelang);
        int[] outlierp = t1.getOutlierPeriode(kodelelang);
        int[] outliers = t1.getOutlierSelisih(kodelelang);
        t1.emptyT1();
        t1.dbInsertT1(kodelelang,periodelelang,meanselisihtahap,outlierp,outliers);
    }
}
