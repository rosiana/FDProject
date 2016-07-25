/**
 * Created by Rosiana on 5/3/2016.
 */

import java.io.*;
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

        String[] kodelelang = new String[1273];
        FileInputStream fstream = new FileInputStream("kodelelang_kemenkeu");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String strLine;

        int i = 0;
        while ((strLine = br.readLine()) != null) {
            kodelelang[i] = strLine;
            i++;
        }
        t1.getJSONT1MeanSelisih(kodelelang);
    }
}
