/**
 * Created by Rosiana on 5/3/2016.
 */

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class Main1 {
    static Crawler crawler = new Crawler();
    static IndikasiIII t3 = new IndikasiIII();


    static final String myDriver = "org.gjt.mm.mysql.Driver";
    static final String myUrl = "jdbc:mysql://localhost/ta";
    static final String user = "root";
    static final String pass = "";

    public static void main(String[] args) throws IOException {
        t3.IndikasiIII();
    }
}
