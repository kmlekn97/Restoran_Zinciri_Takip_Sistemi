/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal
 */
public class Restoran_Islem {

    MysqlDatabase databaseinf = new MysqlDatabase("");
    static Connection con;
    static ResultSet rs;

    public Restoran_Islem() {
        

        String url = "jdbc:mysql://" + databaseinf.getHost() + ":" + databaseinf.getPort() + "/" + databaseinf.getDb_ismi() + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
        SiparisManager productManager = new SiparisManager(new MysqlDatabase(url));
        try {
            //Class.forName("com.mysql.jdbc.Driver");  
            con = DriverManager.getConnection(url, "root", "");
        } catch (Exception e) {
            System.out.println(e);
        }
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Bulunamadı....");
        }

        try {
            databaseinf.setCon(DriverManager.getConnection(url, databaseinf.getKullanici_adi(), databaseinf.getParola()));
            System.out.println("Bağlantı Başarılı...");

        } catch (SQLException ex) {
            System.out.println("Bağlantı Başarısız...");
            //ex.printStackTrace();
        }
    }
    private static Statement statement = null;

    public void veritabani_islem(String sorgu) {
        try {
            statement = con.createStatement();
            statement.executeUpdate(sorgu);
        } catch (SQLException ex) {
            Logger.getLogger(Restoran_Islem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void veritabani_cek(String sorgu) {
        try {
            Statement stmt = con.createStatement();
                 rs = stmt.executeQuery(sorgu);
        } catch (SQLException ex) {
            Logger.getLogger(Restoran_Islem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
