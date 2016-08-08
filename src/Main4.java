/**
 * Created by Rosiana on 5/3/2016.
 */

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main4 {
    static Crawler crawler = new Crawler();
    static T2a t2a = new T2a();


    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/fdproject";
    static final String user = "root";
    static final String pass = "";

    public static void main (String args[]) throws IOException {


        int[] listkodelelang = t2a.getLelangList2();
        float[][] matrix = t2a.isiMatrix2(listkodelelang);
        t2a.getJSONT2a(listkodelelang, matrix);
    }

}
