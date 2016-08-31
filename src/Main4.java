/**
 * Created by Rosiana on 5/3/2016.
 */

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main4 {
    static Crawler crawler = new Crawler();
    static Indikasi2e t2e = new Indikasi2e();


    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static void main (String args[]) throws IOException {
        t2e.IndikasiIIe();
    }

}
