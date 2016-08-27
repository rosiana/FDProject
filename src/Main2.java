/**
 * Created by Rosiana on 5/3/2016.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class Main2 {
    static Crawler crawler = new Crawler();
    static Indikasi2b t2b = new Indikasi2b();


    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static void main(String[] args) throws IOException {
        t2b.IndikasiIIb();
    }
}