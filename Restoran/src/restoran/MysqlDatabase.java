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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kemal
 */
public class MysqlDatabase extends BaseDatabase{
    
    public MysqlDatabase(String connectonstrng) {
        super(connectonstrng);
    }
    private String kullanici_adi = "root";
    private String parola = "";
    
    private String db_ismi = "restoran";
    
    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getDb_ismi() {
        return db_ismi;
    }

    public void setDb_ismi(String db_ismi) {
        this.db_ismi = db_ismi;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    private String host =  "localhost";
    
    private int port = 3306;
    
    private Connection con = null;
    
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    
    @Override
    public void add(Siparis siparis) {
         //System.out.println("Mysql Database: "+siparis.getSiparisID()+" eklendi");
         
      
  
    }
    
    
}
