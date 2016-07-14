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
    static T2a t2a = new T2a();

    public static void main (String args[]) throws IOException {

        /*
        FileInputStream fstream = new FileInputStream("kodelelang");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        String[] kodelelang = new String[2352];
        String strLine;
        int i = 0;

        //Read File Line By Line
        while ((strLine = br.readLine()) != null) {
            kodelelang[i] = strLine;
            i++;
        }
        */
        String[] peserta = t2a.getPesertaList2();
        //float[][] matrix = t2a.isiMatrix2(peserta);
        //t2a.getJSONT2a(matrix,peserta);
        for (int i = 0; i < peserta.length; i++) {
            System.out.println(peserta[i]);
        }
    }
}
