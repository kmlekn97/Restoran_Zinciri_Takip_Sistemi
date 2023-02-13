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
public class RestoranDB {

    MysqlDatabase databaseinf = new MysqlDatabase("");
    static Connection con;

    public RestoranDB() {

        LocalDateTime kayitZaman = LocalDateTime.now();
        Urun urun = new Urun(2, "Makarna", "yemek", 5.0, "", 0, null);
        Masa masa = new Masa(0, 0, 0, 0, "", "", "", "", null, 0, 0, 0);

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
        //Siparis siparis=new Siparis(2, true, 5,urun,5, kayitZaman, 1, true, 8);
        //productManager.add(siparis);

    }

    public static String DurumToString(int durum) {
        String donus = null;
        switch (durum) {
            case 0:
                donus = "Gönderildi";
                break;
            case 1:
                donus = "Alındı";
                break;
            case 2:
                donus = "Hazırlanıyor";
                break;
            case 3:
                donus = "Hazır";
                break;
            case 4:
                donus = "Teslim Edildi";
                break;
        }
        return donus;
    }

    public static String YetkiToString(String yetki) {
        String donus = "";
        switch (yetki) {
            case "0":
                donus = "Normal";
                break;
            case "1":
                donus = "Garson";
                break;
            case "2":
                donus = "Mutfak";
                break;
            case "3":
                donus = "Kasa";
                break;
            case "4":
                donus = "Restoran Yönetim";
                break;
            case "5":
                donus = "Yönetim";
                break;
        }
        return donus;
    }

    public static int YetkiBul(String yetki) {
        int donus = 1;
        switch (yetki) {
            case "Normal":
                donus = 0;
                break;
            case "Garson":
                donus = 1;
                break;
            case "Mutfak":
                donus = 2;
                break;
            case "Kasa":
                donus = 3;
                break;
            case "Restoran Yönetim":
                donus = 4;
                break;
            case "Yönetim":
                donus = 5;
                break;
        }
        return donus;
    }

    public static int menu_Tip(String yetki) {
        int donus = 0;
        switch (yetki) {
            case "Başlangıç":
                donus = 0;
                break;
            case "Ana Yemek":
                donus = 1;
                break;
            case "Tatlı":
                donus = 2;
                break;
            case "İçecek":
                donus = 3;
                break;
        }
        return donus;
    }

    public static String menu_Tipstring(String yetki) {
        String donus = "0";
        switch (yetki) {
            case "Başlangıç":
                donus = "0";
                break;
            case "Ana Yemek":
                donus = "1";
                break;
            case "Tatlı":
                donus = "2";
                break;
            case "İçecek":
                donus = "3";
                break;
        }
        return donus;
    }

    public static String menu_TipBul(String yetki) {
        String donus = "";
        switch (yetki) {
            case "0":
                donus = "Başlangıç";
                break;
            case "1":
                donus = "Ana Yemek";
                break;
            case "2":
                donus = "Tatlı";
                break;
            case "3":
                donus = "İçecek";
                break;
        }
        return donus;
    }

    public static double ToplamUcretAl() {
        Restoran_Islem restoran_i = new Restoran_Islem();
        double toplamUcret = 0;
        int adet = 0;
        double ucret = 0;
        try {
            restoran_i.veritabani_cek("SELECT * FROM Siparisler AS S INNER JOIN urunler AS U ON S.urun_id = U.urun_id WHERE S.masa_id>0 AND S.siparis_odenme=0");
            while (restoran_i.rs.next()) {
                ucret = restoran_i.rs.getDouble("urunucret");
                adet = restoran_i.rs.getInt("siparisadet");
                toplamUcret += (ucret * adet);

            }

        } catch (SQLException ex) {
            Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toplamUcret;
    }
    private static Statement statement = null;

    public static class Siparisler {

        public static void SiparisEkle(int urun_id, int siparisAdet, int masa_id, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            restoran_i.veritabani_islem(String.format("INSERT INTO siparisler (urun_id, siparisadet, masa_id,restoran_id) VALUES (%d, %d,%d,%d)", urun_id, siparisAdet, masa_id, restoran_id));
        }

        public static ArrayList<Siparis> SiparisleriListele(int masaNo, int restoran_id, int masaDurum) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Siparis> siparisler = new ArrayList<Siparis>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.masa_id= %d AND S.siparis_odenme=0 AND S.Siparis_Durum= %d AND S.restoran_id=%d", masaNo, restoran_id, masaDurum));
                while (restoran_i.rs.next()) {
                    Date zaman = new Date();
                    zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    int siparis_durum = restoran_i.rs.getInt("siparis_durum");
                    int siparis_odenme = restoran_i.rs.getInt("siparis_odenme");
                    int ID = restoran_i.rs.getInt("ID");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int siparisadet = restoran_i.rs.getInt("siparisadet");
                    int siparis_id = restoran_i.rs.getInt("siparis_id");
                    Urun urun = new Urun(0, "", "", 0, "", 0, null);
                    urun.setUrunId(restoran_i.rs.getInt("urun_id"));
                    urun.setUrunAdi(restoran_i.rs.getString("urunadi"));
                    urun.setUrunAyrinti(restoran_i.rs.getString("urunayrinti"));
                    urun.setUrunUcret(restoran_i.rs.getDouble("urunucret"));
                    urun.setUrun_resim(restoran_i.rs.getString("urun_resim"));
                    urun.setRestoran_id(restoran_id);
                    Siparis siparis = new Siparis(siparis_id, urun, siparisadet, zaman, siparis_durum, siparis_odenme, masa_id, masaNo, masaDurum, ID, 0, "", "", "", "", null, 0, 0, 0);
                    siparisler.add(siparis);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return siparisler;
        }

        public static ArrayList<Siparis> SiparisleriListele(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Siparis> siparisler = new ArrayList<Siparis>();
            try {
                if (restoran_id == 0) {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id INNER JOIN masalar AS M ON S.masa_id=M.masa_id INNER JOIN restoranlar AS R ON S.restoran_id=R.restoran_id"));
                } else {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id INNER JOIN masalar AS M ON S.masa_id=M.masa_id INNER JOIN restoranlar AS R ON S.restoran_id=R.restoran_id  WHERE R.restoran_id=%d", restoran_id));
                }
                while (restoran_i.rs.next()) {
                    Date zaman = new Date();
                    zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    int siparis_durum = restoran_i.rs.getInt("siparis_durum");
                    int siparis_odenme = restoran_i.rs.getInt("siparis_odenme");
                    int ID = restoran_i.rs.getInt("ID");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int masa_durum = restoran_i.rs.getInt("Masa_durum");
                    int siparisadet = restoran_i.rs.getInt("siparisadet");
                    int siparis_id = restoran_i.rs.getInt("siparis_id");
                    int res_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cari = new Cari(0, 0, 0, restoran_id);
                    Urun urun = new Urun(0, "", "", 0, "", 0, null);
                    urun.setUrunId(restoran_i.rs.getInt("urun_id"));
                    urun.setUrunAdi(restoran_i.rs.getString("urunadi"));
                    urun.setUrunAyrinti(restoran_i.rs.getString("urunayrinti"));
                    urun.setUrunUcret(restoran_i.rs.getDouble("urunucret"));
                    urun.setUrun_resim(restoran_i.rs.getString("urun_resim"));
                    urun.setRestoran_id(res_id);
                    Siparis siparis = new Siparis(siparis_id, urun, siparisadet, zaman, siparis_durum, siparis_odenme, masa_id, masa_id, masa_durum, ID, res_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    siparisler.add(siparis);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return siparisler;
        }

        public static void SiparisIptal(ArrayList<Integer> ids) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            if (ids.size() > 0) {
                for (int id : ids) {
                    Scanner scan = new Scanner(System.in, "iso-8859-9");
                    restoran_i.veritabani_islem(String.format("DELETE FROM siparisler WHERE siparis_id=%d", id));
                }
            }
        }

        public static ArrayList<Siparis> SiparisleriYerlestir(int siparisDurum, int res_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Siparis> siparisler = new ArrayList<Siparis>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id = U.urun_id INNER JOIN restoranlar AS R ON R.restoran_id = S.restoran_id WHERE S.siparis_odenme=0 AND S.siparis_durum=%d AND S.restoran_id=%d ORDER BY S.sipariszaman DESC", siparisDurum, res_id));
                while (restoran_i.rs.next()) {
                    Date zaman = new Date();
                    zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    int siparis_durum = restoran_i.rs.getInt("siparis_durum");
                    int siparis_odenme = restoran_i.rs.getInt("siparis_odenme");
                    int masa_id = restoran_i.rs.getInt("masa_id");
                    int siparisadet = restoran_i.rs.getInt("siparisadet");
                    int urun_id = restoran_i.rs.getInt("urun_id");
                    int siparis_id = restoran_i.rs.getInt("siparis_id");
                    Urun urun = new Urun(0, "", "", 0, "", 0, null);
                    urun.setUrunId(urun_id);
                    urun.setUrunAdi(restoran_i.rs.getString("urunadi"));
                    urun.setUrunAyrinti(restoran_i.rs.getString("urunayrinti"));
                    urun.setUrunUcret(restoran_i.rs.getDouble("urunucret"));
                    urun.setUrun_resim(restoran_i.rs.getString("urun_resim"));
                    Siparis siparis = new Siparis(siparis_id, urun, siparisadet, zaman, siparis_durum, siparis_odenme, masa_id, 0, 0, 0, 0, "", "", "", "", null, 0, 0, 0);
                    siparisler.add(siparis);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return siparisler;
        }

        public static ArrayList<Siparis> SiparisleriYerlestirrestoran(int res_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Siparis> siparisler = new ArrayList<Siparis>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id = U.urun_id INNER JOIN restoranlar AS R ON R.restoran_id = S.restoran_id WHERE S.siparis_odenme=0 and S.restoran_id=%d ORDER BY S.sipariszaman DESC", res_id));
                while (restoran_i.rs.next()) {
                    Date zaman = new Date();
                    zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    int siparis_durum = restoran_i.rs.getInt("siparis_durum");
                    int siparis_odenme = restoran_i.rs.getInt("siparis_odenme");
                    int masa_id = restoran_i.rs.getInt("masa_id");
                    int siparisadet = restoran_i.rs.getInt("siparisadet");
                    int urun_id = restoran_i.rs.getInt("urun_id");
                    int siparis_id = restoran_i.rs.getInt("siparis_id");
                    Urun urun = new Urun(0, "", "", 0, "", 0, null);
                    urun.setUrunId(urun_id);
                    urun.setUrunAdi(restoran_i.rs.getString("urunadi"));
                    urun.setUrunAyrinti(restoran_i.rs.getString("urunayrinti"));
                    urun.setUrunUcret(restoran_i.rs.getDouble("urunucret"));
                    urun.setUrun_resim(restoran_i.rs.getString("urun_resim"));
                    Siparis siparis = new Siparis(siparis_id, urun, siparisadet, zaman, siparis_durum, siparis_odenme, masa_id, 0, 0, 0, 0, "", "", "", "", null, 0, 0, 0);
                    siparisler.add(siparis);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return siparisler;
        }

        public static boolean SiparisDegisiklikOlduMu(int eskiSiparisSayisi) {
            int adet = 0;
            boolean degisiklik = false;
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(String.format("SELECT COUNT(*) AS adet FROM Siparisler WHERE Siparis_Odenme=0 AND Siparis_Durum<4"));
                while (restoran_i.rs.next()) {
                    adet = restoran_i.rs.getInt("adet");
                }
                if (adet != eskiSiparisSayisi) {
                    degisiklik = true;
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return degisiklik;

        }

        public static void SiparisDurumDegistir(int siparisNo, int durum) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE siparisler SET siparis_durum=%d WHERE siparis_id=%d", durum, siparisNo));
        }
    }

    public static class Masalar {

        public static int MasalariYerlestir(boolean sadeceBosMasalar, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int adet = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT Count(*) AS adet FROM masalar WHERE Masa_durum=0 AND restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    adet = restoran_i.rs.getInt("adet");
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return adet;
        }

        public static ArrayList<Masa> MasalariGuncelle(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Masa> masalar = new ArrayList<Masa>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM masalar WHERE restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    int ID = restoran_i.rs.getInt("ID");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int masa_durum = restoran_i.rs.getInt("Masa_durum");
                    Masa masa = new Masa(masa_id, masa_durum, ID, 0, "", "", "", "", null, 0, 0, 0);
                    masalar.add(masa);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }

            return masalar;
        }

        public static double MasaBorcHesapla(int masaNo, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double toplamUcret = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id = U.urun_id WHERE S.siparis_odenme=0 AND S.masa_id=%d AND S.restoran_id=%d", masaNo, restoran_id));
                while (restoran_i.rs.next()) {
                    int urun_id = restoran_i.rs.getInt("urun_id");
                    int adet = restoran_i.rs.getInt("siparisadet");
                    double ucret = restoran_i.rs.getDouble("urunucret");
                    double toplam = ucret * adet;
                    toplamUcret += toplam;
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return toplamUcret;
        }

        public static void MasaDurumDegistir(int masaNo, int yeniDurum, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            restoran_i.veritabani_islem(String.format("UPDATE masalar SET Masa_durum=%d WHERE Masa_id=%d AND restoran_id=%d", yeniDurum, masaNo, restoran_id));
        }

        public static Masa MasaAl(int masaNo, int restoran_id) {
            Masa masa = new Masa(0, 0, 0, 0, "", "", "", "", null, 0, 0, 0);
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM masalar WHERE Masa_id=%d AND restoran_id=%d", masaNo, restoran_id));
                while (restoran_i.rs.next()) {
                    masa.setMasaNo(restoran_i.rs.getInt("Masa_id"));
                    masa.setMasaDurum(restoran_i.rs.getInt("Masa_durum"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return masa;
        }

        public static void MasaBorcOde(int masaNo, int restoran_id, double borc_miktari) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            restoran_i.veritabani_islem(String.format("UPDATE siparisler SET siparis_odenme=1 WHERE masa_id=%d AND restoran_id=%d", masaNo, restoran_id));
            restoran_i.veritabani_islem(String.format("UPDATE masalar SET Masa_durum=0 WHERE masa_id=%d AND restoran_id=%d", masaNo, restoran_id));
            Cariler.CariEkle(borc_miktari, 0, 2);
        }

        public static ArrayList<Siparis> MasaOzetiCikar(int masaNo, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Siparis> siparisler = new ArrayList<Siparis>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id = U.urun_id WHERE S.masa_id=%d AND S.siparis_odenme=0 AND S.restoran_id=%d ORDER BY S.sipariszaman", masaNo, restoran_id));
                while (restoran_i.rs.next()) {
                    Date zaman = new Date();
                    zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    int siparis_durum = restoran_i.rs.getInt("siparis_durum");
                    int siparis_odenme = restoran_i.rs.getInt("siparis_odenme");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int siparis_id = restoran_i.rs.getInt("siparis_id");
                    int siparisadet = restoran_i.rs.getInt("siparisadet");
                    Urun urun = new Urun(0, "", "", 0, "", 0, null);
                    urun.setUrunId(restoran_i.rs.getInt("urun_id"));
                    urun.setUrunAdi(restoran_i.rs.getString("urunadi"));
                    urun.setUrunAyrinti(restoran_i.rs.getString("urunayrinti"));
                    urun.setUrunUcret(restoran_i.rs.getDouble("urunucret"));
                    urun.setUrun_resim(restoran_i.rs.getString("urun_resim"));
                    Siparis siparis = new Siparis(siparis_id, urun, siparisadet, zaman, siparis_durum, siparis_odenme, masa_id, 0, 0, 0, 0, "", "", "", "", null, 0, 0, 0);
                    siparisler.add(siparis);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return siparisler;
        }

        public static void MasayiBosalt(int masaNo, int restoran_id) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM siparisler WHERE masa_id=%d AND restoran_id=%d", masaNo, restoran_id));
            restoran_i.veritabani_islem(String.format("UPDATE masalar SET Masa_durum=0 WHERE masa_id=%d AND restoran_id=%d", masaNo, restoran_id));
        }

        public static ArrayList<Masa> BosMasaListele(int res_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Masa> bosMasalar = new ArrayList<Masa>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM masalar AS M INNER JOIN restoranlar AS R ON M.restoran_id = R.restoran_id WHERE Masa_durum=0 and M.restoran_id=%d", res_id));
                while (restoran_i.rs.next()) {
                    int ID = restoran_i.rs.getInt("ID");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int masadurum = restoran_i.rs.getInt("Masa_durum");
                    int restoran_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Masa masa = new Masa(masa_id, masadurum, ID, restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    bosMasalar.add(masa);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return bosMasalar;
        }

        public static ArrayList<Masa> TümMasaListele() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Masa> bosMasalar = new ArrayList<Masa>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM masalar AS M INNER JOIN restoranlar AS R ON M.restoran_id = R.restoran_id"));
                while (restoran_i.rs.next()) {
                    int ID = restoran_i.rs.getInt("ID");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int masadurum = restoran_i.rs.getInt("Masa_durum");
                    int restoran_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Masa masa = new Masa(masa_id, masadurum, ID, restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    bosMasalar.add(masa);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return bosMasalar;
        }

        public static ArrayList<Masa> TümMasaListele(int r_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Masa> bosMasalar = new ArrayList<Masa>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM masalar AS M INNER JOIN restoranlar AS R ON M.restoran_id = R.restoran_id WHERE R.restoran_id=%d", r_id));
                while (restoran_i.rs.next()) {
                    int ID = restoran_i.rs.getInt("ID");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int masadurum = restoran_i.rs.getInt("Masa_durum");
                    int restoran_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Masa masa = new Masa(masa_id, masadurum, ID, restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    bosMasalar.add(masa);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return bosMasalar;
        }

        public static ArrayList<Masa> DoluMasaListele(int res_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Masa> bosMasalar = new ArrayList<Masa>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM masalar AS M INNER JOIN restoranlar AS R ON M.restoran_id = R.restoran_id WHERE Masa_durum=1 and M.restoran_id=%d", res_id));
                while (restoran_i.rs.next()) {
                    int ID = restoran_i.rs.getInt("ID");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int masadurum = restoran_i.rs.getInt("Masa_durum");
                    int restoran_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Masa masa = new Masa(masa_id, masadurum, ID, restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    bosMasalar.add(masa);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return bosMasalar;
        }

        public static ArrayList<Masa> BosMasaListelegenel(int res_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Masa> bosMasalar = new ArrayList<Masa>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM masalar AS M INNER JOIN restoranlar AS R ON M.restoran_id = R.restoran_id WHERE M.restoran_id=%d", res_id));
                while (restoran_i.rs.next()) {
                    int ID = restoran_i.rs.getInt("ID");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int masadurum = restoran_i.rs.getInt("Masa_durum");
                    int restoran_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Masa masa = new Masa(masa_id, masadurum, ID, restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    bosMasalar.add(masa);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return bosMasalar;
        }

        public static void MasaTasi(int eskiMasaNo, int yeniMasaNo, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Integer> siparisler = new ArrayList<Integer>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler WHERE masa_id=%d AND restoran_id=%d", eskiMasaNo, restoran_id));
                while (restoran_i.rs.next()) {
                    int siparis_id = restoran_i.rs.getInt("siparis_id");
                    siparisler.add(siparis_id);
                }
                for (int siparis : siparisler) {
                    restoran_i.veritabani_islem(String.format("UPDATE siparisler SET masa_id=%d WHERE siparis_id=%d AND restoran_id=%d", yeniMasaNo, siparis, restoran_id));
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public static ArrayList<Masa> MasaAra(String keyword, int r_id) {
            ArrayList<Masa> masalar = new ArrayList<Masa>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                if (r_id > 0) {
                    restoran_i.veritabani_cek("SELECT * FROM masalar AS M INNER JOIN restoranlar AS R ON M.restoran_id = R.restoran_id WHERE R.restoran_id=" + r_id + " AND M.Masa_id like '%" + keyword + "%'");
                } else {
                    restoran_i.veritabani_cek(("SELECT * FROM masalar AS M INNER JOIN restoranlar AS R ON M.restoran_id = R.restoran_id WHERE M.Masa_id like '%" + keyword + "%'"));
                }
                while (restoran_i.rs.next()) {
                    int ID = restoran_i.rs.getInt("ID");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int masadurum = restoran_i.rs.getInt("Masa_durum");
                    int restoran_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Masa masa = new Masa(masa_id, masadurum, ID, restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    masalar.add(masa);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return masalar;
        }

        public static void MasaEkle(int masaNo, int restoran_id) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("INSERT INTO masalar (Masa_id,restoran_id) VALUES (%d,%d)", masaNo, restoran_id));
        }

        public static void MasaDuzenle(int ID, int restoran_id, int masa) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE masalar SET masa_id=%d,restoran_id=%d WHERE ID=%d", masa, restoran_id, ID));
        }

        public static void MasaSil(int masaNo) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM masalar WHERE ID=%d", masaNo));
        }
    }

    public static class Urunler {

        public enum UrunAlan {
            UrunAdi,
            UrunUcret
        }

        public static ArrayList<Urun> UrunListe(int urun_id, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Urun> urunler = new ArrayList<Urun>();
            try {
                if (urun_id == -1) {
                    restoran_i.veritabani_cek(String.format("SELECT urunler.*,restoranlar.* FROM urunler INNER JOIN restoranlar ON restoranlar.Restoran_id=urunler.restoran_id WHERE restoranlar.restoran_id=%d", restoran_id));
                } else {
                    restoran_i.veritabani_cek(String.format("SELECT urunler.*,restoranlar.* FROM urunler INNER JOIN restoranlar ON restoranlar.Restoran_id=urunler.restoran_id WHERE urun_id=%d AND restoranlar.restoran_id=%d", urun_id, restoran_id));
                }
                while (restoran_i.rs.next()) {
                    int urunid = restoran_i.rs.getInt("urun_id");
                    String urunadi = restoran_i.rs.getString("urunadi");
                    String urunayrinti = restoran_i.rs.getString("urunayrinti");
                    int urunucret = restoran_i.rs.getInt("urunucret");
                    String urun_resim = restoran_i.rs.getString("urun_resim");
                    int res_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(res_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Urun urun = new Urun(urunid, urunadi, urunayrinti, urunucret, urun_resim, res_id, restoran);
                    urunler.add(urun);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return urunler;
        }

        public static ArrayList<Urun> UrunListe(String urun_adi, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Urun> urunler = new ArrayList<Urun>();
            try {

                restoran_i.veritabani_cek(String.format("SELECT urunler.*,restoranlar.* FROM urunler INNER JOIN restoranlar ON restoranlar.Restoran_id=urunler.restoran_id  WHERE urunler.urunadi='%s' AND restoranlar.restoran_id=%d", urun_adi, restoran_id));
                while (restoran_i.rs.next()) {
                    int urunid = restoran_i.rs.getInt("urun_id");
                    String urunadi = restoran_i.rs.getString("urunadi");
                    String urunayrinti = restoran_i.rs.getString("urunayrinti");
                    int urunucret = restoran_i.rs.getInt("urunucret");
                    String urun_resim = restoran_i.rs.getString("urun_resim");
                    int res_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(res_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Urun urun = new Urun(urunid, urunadi, urunayrinti, urunucret, urun_resim, res_id, restoran);
                    urunler.add(urun);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return urunler;
        }

        public static ArrayList<Urun> UrunListelead(String urun_ad, int restoran_id) {
            ArrayList<Urun> urunler = new ArrayList<Urun>();
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT urunler.*,restoranlar.* FROM urunler INNER JOIN restoranlar ON restoranlar.Restoran_id=urunler.restoran_id where urunler.urunadi='%s' AND restoranlar.restoran_id=%d", urun_ad, restoran_id));
                while (restoran_i.rs.next()) {
                    int urunid = restoran_i.rs.getInt("urun_id");
                    String urunadi = restoran_i.rs.getString("urunadi");
                    String urunayrinti = restoran_i.rs.getString("urunayrinti");
                    int urunucret = restoran_i.rs.getInt("urunucret");
                    String urun_resim = restoran_i.rs.getString("urun_resim");
                    int res_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(res_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Urun urun = new Urun(urunid, urunadi, urunayrinti, urunucret, urun_resim, res_id, restoran);
                    urunler.add(urun);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return urunler;
        }

        public static ArrayList<Urun> UrunListeRestoran(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Urun> urunler = new ArrayList<Urun>();
            try {

                restoran_i.veritabani_cek(String.format("SELECT urunler.*,restoranlar.* FROM urunler INNER JOIN restoranlar ON restoranlar.Restoran_id=urunler.restoran_id WHERE restoranlar.Restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    int urunid = restoran_i.rs.getInt("urun_id");
                    String urunadi = restoran_i.rs.getString("urunadi");
                    String urunayrinti = restoran_i.rs.getString("urunayrinti");
                    int urunucret = restoran_i.rs.getInt("urunucret");
                    String urun_resim = restoran_i.rs.getString("urun_resim");
                    int res_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(res_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Urun urun = new Urun(urunid, urunadi, urunayrinti, urunucret, urun_resim, res_id, restoran);
                    urunler.add(urun);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return urunler;
        }

        public static ArrayList<Urun> UrunListe() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Urun> urunler = new ArrayList<Urun>();
            try {

                restoran_i.veritabani_cek(String.format("SELECT urunler.*,restoranlar.* FROM urunler INNER JOIN restoranlar ON restoranlar.Restoran_id=urunler.restoran_id"));
                while (restoran_i.rs.next()) {
                    int urunid = restoran_i.rs.getInt("urun_id");
                    String urunadi = restoran_i.rs.getString("urunadi");
                    String urunayrinti = restoran_i.rs.getString("urunayrinti");
                    int urunucret = restoran_i.rs.getInt("urunucret");
                    String urun_resim = restoran_i.rs.getString("urun_resim");
                    int res_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(res_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Urun urun = new Urun(urunid, urunadi, urunayrinti, urunucret, urun_resim, res_id, restoran);
                    urunler.add(urun);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return urunler;
        }

        public static ArrayList<Urun> UrunListeAra(String keyword, int r_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Urun> urunler = new ArrayList<Urun>();
            try {
                if (r_id > 0) {
                    restoran_i.veritabani_cek("SELECT * FROM urunler AS U INNER JOIN restoranlar AS R ON R.restoran_id=U.restoran_id WHERE R.restoran_id=" + r_id + " AND U.urunadi like '%" + keyword + "%'");
                } else {
                    restoran_i.veritabani_cek("SELECT * FROM urunler Where urunadi like '%" + keyword + "%'");
                }
                while (restoran_i.rs.next()) {
                    int urunid = restoran_i.rs.getInt("urun_id");
                    String urunadi = restoran_i.rs.getString("urunadi");
                    String urunayrinti = restoran_i.rs.getString("urunayrinti");
                    int urunucret = restoran_i.rs.getInt("urunucret");
                    String urun_resim = restoran_i.rs.getString("urun_resim");
                    int res_id = restoran_i.rs.getInt("Restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(res_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Urun urun = new Urun(urunid, urunadi, urunayrinti, urunucret, urun_resim, res_id, restoran);
                    urunler.add(urun);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return urunler;
        }

        public static void UrunEkle(String urunAdi, double urunUcret, String urunayrinti, String urun_resim, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            String sorgu1 = String.format("INSERT INTO urunler (urunadi,urunucret,urunayrinti,urun_resim,restoran_id) VALUES ('%s',", urunAdi);
            String sorgu2 = String.format("%.2f", urunUcret);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format(",'%s','%s',%d)", urunayrinti, urun_resim, restoran_id);
            String sorgu = sorgu1 + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }

        public static void UrunSil(int urun_id) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM urunler WHERE urun_id=%d", urun_id));
        }

        public static void UrunGuncelle(int urunId, String yeniAdi, String urunayrinti, int restoran_id) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE urunler SET urunadi='%s',urunayrinti='%s',restoran_id=%d WHERE urun_id=%d", yeniAdi, urunayrinti, restoran_id, urunId));
        }

        public static void UrunGuncelle(int urunId, String urun_resim) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE urunler SET urun_resim='%s' WHERE urun_id=%d", urun_resim, urunId));
        }

        public static void UrunGuncelle(int urunId, double urunUcret) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu = String.format("UPDATE Urunler SET urunucret=%.2f WHERE urun_id=%d", urunUcret, urunId);
            sorgu = sorgu.replace(',', '.');
            restoran_i.veritabani_islem(sorgu);
        }
    }

    public static class Kullanicilar {

        public static GirisVeri Giris(String kullanici, String sifre) {
            GirisVeri giris = new GirisVeri();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM kullanicilar WHERE kullaniciadi='%s' AND kullanicisifre='%s'", kullanici, sifre));
                if (restoran_i.rs.next()) {

                    giris.Yetki = restoran_i.rs.getInt("kullanici_yetki");
                    giris.ID = restoran_i.rs.getInt("kullanici_id");

                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return giris;
        }

        public static void KullaniciGuncelle(int id, String kullaniciadi, String sifre) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE kullanicilar SET kullaniciadi='%s',kullanicisifre='%s' WHERE kullanici_id=%d", kullaniciadi, sifre, id));
        }

        public static void SifreDegistir(int id, String sifre) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE kullanicilar SET kullanicisifre='%s' WHERE kullanici_id=%d", sifre, id));
        }

        public static void KullaniciResimGuncelle(int id, String kullanici_resim) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE kullanicilar SET kullanici_resim='%s' WHERE kullanici_id=%d", kullanici_resim, id));
        }

        public static void KullaniciGuncelle(int id, String yetki) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE kullanicilar SET kullanici_yetki='%s' WHERE kullanici_id=%d", yetki, id));
        }

        public static void KullaniciEkle(String kullaniciadi, String sifre, String yetki, String kullanici_resim) {
            Scanner scan = new Scanner(System.in, "iso-8859-9");
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("INSERT INTO kullanicilar (kullaniciadi, kullanicisifre, kullanici_yetki,kullanici_resim) VALUES ('%s', '%s', '%s','%s')", kullaniciadi, sifre, yetki, kullanici_resim));
        }

        public static ArrayList<Kullanici> KullanicilariListele() {
            ArrayList<Kullanici> kullanicilar = new ArrayList<Kullanici>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM kullanicilar WHERE kullanici_yetki<=5 ORDER BY kullanici_yetki DESC"));
                while (restoran_i.rs.next()) {
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullanici_adi = restoran_i.rs.getString("kullaniciadi");
                    String kullanici_sifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    Kullanici kullanici = new Kullanici(kullanici_id, kullanici_adi, kullanici_sifre, kullanici_yetki, kullanici_resim);
                    kullanicilar.add(kullanici);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return kullanicilar;
        }

        public static ArrayList<Kullanici> KullaniciBilgiListele(int k_id) {
            ArrayList<Kullanici> kullanicilar = new ArrayList<Kullanici>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM kullanicilar WHERE kullanici_yetki<=5 AND kullanici_id=%d ORDER BY kullanici_yetki DESC", k_id));
                while (restoran_i.rs.next()) {
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullanici_adi = restoran_i.rs.getString("kullaniciadi");
                    String kullanici_sifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    Kullanici kullanici = new Kullanici(kullanici_id, kullanici_adi, kullanici_sifre, kullanici_yetki, kullanici_resim);
                    kullanicilar.add(kullanici);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return kullanicilar;
        }

        public static ArrayList<Calisanlar> KullanicilariListele(int k_id) {
            ArrayList<Calisanlar> kullanicilar = new ArrayList<Calisanlar>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM kullanicilar AS K INNER JOIN calisanlar AS C ON K.kullanici_id=C.kullanici_id WHERE K.kullanici_yetki<=5 and K.kullanici_id=%d ORDER BY K.kullanici_yetki DESC", k_id));
                while (restoran_i.rs.next()) {
                    int calisan_id = restoran_i.rs.getInt("calisan_id");
                    String calisan_adi = restoran_i.rs.getString("calisan_adi");
                    String calisan_soyadi = restoran_i.rs.getString("calisan_soyadi");
                    double calisan_maas = restoran_i.rs.getDouble("calisan_maas");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullaniciadi = restoran_i.rs.getString("kullaniciadi");
                    String kullanicisifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    String tel = restoran_i.rs.getString("calisan_tel");
                    String mail = restoran_i.rs.getString("calisan_mail");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    Calisanlar calisan = new Calisanlar(calisan_id, calisan_adi, calisan_soyadi, calisan_maas, kullanici_id, tel, mail, restoran_id, kullanici_id, kullaniciadi, kullanicisifre, kullanici_yetki, kullanici_resim);
                    kullanicilar.add(calisan);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return kullanicilar;
        }

        public static ArrayList<Calisanlar> KullanicilariListeleRestoran(int r_id) {
            ArrayList<Calisanlar> kullanicilar = new ArrayList<Calisanlar>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM kullanicilar AS K INNER JOIN calisanlar AS C ON K.kullanici_id=C.kullanici_id WHERE K.kullanici_yetki<=5 and C.restoran_id=%d ORDER BY K.kullanici_yetki DESC", r_id));
                while (restoran_i.rs.next()) {
                    int calisan_id = restoran_i.rs.getInt("calisan_id");
                    String calisan_adi = restoran_i.rs.getString("calisan_adi");
                    String calisan_soyadi = restoran_i.rs.getString("calisan_soyadi");
                    double calisan_maas = restoran_i.rs.getDouble("calisan_maas");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullaniciadi = restoran_i.rs.getString("kullaniciadi");
                    String kullanicisifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    String tel = restoran_i.rs.getString("calisan_tel");
                    String mail = restoran_i.rs.getString("calisan_mail");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    Calisanlar calisan = new Calisanlar(calisan_id, calisan_adi, calisan_soyadi, calisan_maas, kullanici_id, tel, mail, restoran_id, kullanici_id, kullaniciadi, kullanicisifre, kullanici_yetki, kullanici_resim);
                    kullanicilar.add(calisan);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return kullanicilar;
        }

        public static ArrayList<Kullanici> KullanicilariAra(String keyword, int r_id) {
            ArrayList<Kullanici> kullanicilar = new ArrayList<Kullanici>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                if (r_id > 0) {
                    restoran_i.veritabani_cek("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id INNER JOIN restoranlar AS R ON R.restoran_id=C.restoran_id WHERE R.restoran_id=" + r_id + " AND C.calisan_adi like '%" + keyword + "%' ORDER BY kullanici_yetki DESC");
                } else {
                    restoran_i.veritabani_cek("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id WHERE C.calisan_adi like '%" + keyword + "%' ORDER BY kullanici_yetki DESC");
                }
                while (restoran_i.rs.next()) {
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullanici_adi = restoran_i.rs.getString("kullaniciadi");
                    String kullanici_sifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    Kullanici kullanici = new Kullanici(kullanici_id, kullanici_adi, kullanici_sifre, kullanici_yetki, kullanici_resim);
                    kullanicilar.add(kullanici);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return kullanicilar;
        }

        public static void KullaniciSil(int kullaniciId) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM kullanicilar WHERE kullanici_id=%d", kullaniciId));
            restoran_i.veritabani_islem(String.format("DELETE FROM calisanlar WHERE kullanici_id=%d", kullaniciId));
        }
    }

    public static class Istatistik {

        public static double ToplamCiro() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double toplamCiro = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=1"));
                while (restoran_i.rs.next()) {
                    toplamCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return toplamCiro;
        }

        public static double ToplamCiro(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double toplamCiro = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=1 and S.restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    toplamCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return toplamCiro;
        }

        public static double GunlukCiro() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double gunlukCiro = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=1"));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    if (df.format(simdikiZaman).equals(df.format(zaman))) {
                        gunlukCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return gunlukCiro;
        }

        public static double GunlukCiro(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double gunlukCiro = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=1 and S.restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    if (df.format(simdikiZaman).equals(df.format(zaman))) {
                        gunlukCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return gunlukCiro;
        }

        public static double AylikCiro() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double aylikCiro = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=1"));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy");
                    DateFormat df2 = new SimpleDateFormat("MM");
                    if (df.format(simdikiZaman).equals(df.format(zaman)) && df.format(simdikiZaman).equals(df.format(zaman))) {
                        aylikCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return aylikCiro;
        }

        public static double AylikCiro(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double aylikCiro = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=1 and S.restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy");
                    DateFormat df2 = new SimpleDateFormat("MM");
                    if (df.format(simdikiZaman).equals(df.format(zaman)) && df.format(simdikiZaman).equals(df.format(zaman))) {
                        aylikCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return aylikCiro;
        }

        public static double YillikCiro() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double yillikCiro = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=1"));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy");
                    if (df.format(simdikiZaman).equals(df.format(zaman))) {
                        yillikCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return yillikCiro;
        }

        public static double YillikCiro(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double yillikCiro = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=1 and S.restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy");
                    if (df.format(simdikiZaman).equals(df.format(zaman))) {
                        yillikCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return yillikCiro;
        }

        public static int SiparisSayisi() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int sayisi = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT COUNT(*) as sayi FROM siparisler"));
                while (restoran_i.rs.next()) {
                    sayisi = restoran_i.rs.getInt("sayi");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return sayisi;
        }

        public static int GunlukSiparisSayisi() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int sayisi = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM Siparisler"));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    if (df.format(simdikiZaman).equals(df.format(zaman))) {
                        sayisi++;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return sayisi;
        }

        public static int OdenmemisSiparisSayisi() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int sayisi = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT COUNT(*) as adet FROM siparisler WHERE siparis_odenme=0"));
                while (restoran_i.rs.next()) {
                    sayisi = restoran_i.rs.getInt("adet");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return sayisi;
        }

        public static double OdenmemisUcretToplam() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double toplamCiro = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=0"));
                while (restoran_i.rs.next()) {
                    toplamCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return toplamCiro;
        }

        public static int TumMasaSayisi() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int sayisi = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT COUNT(*) as adet FROM masalar"));
                while (restoran_i.rs.next()) {
                    sayisi = restoran_i.rs.getInt("adet");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return sayisi;
        }

        public static int DoluMasaSayisi() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int sayisi = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT COUNT(*) as adet FROM masalar WHERE Masa_durum=1"));
                while (restoran_i.rs.next()) {
                    sayisi = restoran_i.rs.getInt("adet");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return sayisi;
        }

        public static Tarih_Bilgi TarihBilgiVer(Date tarih) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            Tarih_Bilgi tarihBilgi = new Tarih_Bilgi(0, 0);
            int siparisSayisi = 0;
            double gunlukCiro = 0d;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE S.siparis_odenme=1"));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
                    if (df.format(simdikiZaman).equals(df.format(zaman))) {
                        gunlukCiro += (restoran_i.rs.getInt("siparisadet")) * (restoran_i.rs.getDouble("urunucret"));
                        siparisSayisi++;
                    }
                    tarihBilgi.setGunlukCiro(gunlukCiro);
                    tarihBilgi.setSiparisSayisi(siparisSayisi);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return tarihBilgi;
        }
    }

    public static class Optimizasyon {

        public static ArrayList<Siparis> SiparisListele(int tur) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Siparis> siparisler = new ArrayList<Siparis>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE siparis_odenme=1"));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat yil = new SimpleDateFormat("yyyy");
                    DateFormat ay = new SimpleDateFormat("MM");
                    DateFormat gun = new SimpleDateFormat("dd");
                    switch (tur) {
                        case 0:
                            if (gun.format(simdikiZaman).equals(gun.format(zaman))) {
                                Date zaman2 = new Date();
                                zaman2 = restoran_i.rs.getTimestamp("sipariszaman");
                                int siparis_durum = restoran_i.rs.getInt("siparis_durum");
                                int siparis_odenme = restoran_i.rs.getInt("siparis_odenme");
                                int masa_id = restoran_i.rs.getInt("Masa_id");
                                int siparisadet = restoran_i.rs.getInt("siparisadet");
                                int urun_id = restoran_i.rs.getInt("urun_id");
                                int siparis_id = restoran_i.rs.getInt("siparis_id");
                                Urun urun = new Urun(0, "", "", 0, "", 0, null);
                                urun.setUrunId(urun_id);
                                urun.setUrunAdi(restoran_i.rs.getString("urunadi"));
                                urun.setUrunAyrinti(restoran_i.rs.getString("urunayrinti"));
                                urun.setUrunUcret(restoran_i.rs.getDouble("urunucret"));
                                urun.setUrun_resim(restoran_i.rs.getString("urun_resim"));
                                Siparis siparis = new Siparis(siparis_id, urun, siparisadet, zaman2, siparis_durum, siparis_odenme, masa_id, 0, 0, 0, 0, "", "", "", "", null, 0, 0, 0);
                                siparisler.add(siparis);
                            }
                            break;
                        case 1:
                            if (ay.format(simdikiZaman).equals(ay.format(zaman))) {
                                Date zaman2 = new Date();
                                zaman2 = restoran_i.rs.getTimestamp("sipariszaman");
                                int siparis_durum = restoran_i.rs.getInt("siparis_durum");
                                int siparis_odenme = restoran_i.rs.getInt("siparis_odenme");
                                int masa_id = restoran_i.rs.getInt("Masa_id");
                                int siparisadet = restoran_i.rs.getInt("siparisadet");
                                int urun_id = restoran_i.rs.getInt("urun_id");
                                int siparis_id = restoran_i.rs.getInt("siparis_id");
                                Urun urun = new Urun(0, "", "", 0, "", 0, null);
                                urun.setUrunId(urun_id);
                                urun.setUrunAdi(restoran_i.rs.getString("urunadi"));
                                urun.setUrunAyrinti(restoran_i.rs.getString("urunayrinti"));
                                urun.setUrunUcret(restoran_i.rs.getDouble("urunucret"));
                                urun.setUrun_resim(restoran_i.rs.getString("urun_resim"));
                                Siparis siparis = new Siparis(siparis_id, urun, siparisadet, zaman2, siparis_durum, siparis_odenme, masa_id, 0, 0, 0, 0, "", "", "", "", null, 0, 0, 0);
                                siparisler.add(siparis);
                            }
                            break;
                        case 2:
                            if (yil.format(simdikiZaman).equals(yil.format(zaman))) {
                                Date zaman2 = new Date();
                                zaman2 = restoran_i.rs.getTimestamp("sipariszaman");
                                int siparis_durum = restoran_i.rs.getInt("siparis_durum");
                                int siparis_odenme = restoran_i.rs.getInt("siparis_odenme");
                                int masa_id = restoran_i.rs.getInt("Masa_id");
                                int siparisadet = restoran_i.rs.getInt("siparisadet");
                                int urun_id = restoran_i.rs.getInt("urun_id");
                                int siparis_id = restoran_i.rs.getInt("siparis_id");
                                Urun urun = new Urun(0, "", "", 0, "", 0, null);
                                urun.setUrunId(urun_id);
                                urun.setUrunAdi(restoran_i.rs.getString("urunadi"));
                                urun.setUrunAyrinti(restoran_i.rs.getString("urunayrinti"));
                                urun.setUrunUcret(restoran_i.rs.getDouble("urunucret"));
                                urun.setUrun_resim(restoran_i.rs.getString("urun_resim"));
                                Siparis siparis = new Siparis(siparis_id, urun, siparisadet, zaman2, siparis_durum, siparis_odenme, masa_id, 0, 0, 0, 0, "", "", "", "", null, 0, 0, 0);
                                siparisler.add(siparis);
                            }
                            break;
                    }
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return siparisler;

        }

        public static ArrayList<Siparis> SiparisListele() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Siparis> siparisler = new ArrayList<Siparis>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler AS S INNER JOIN urunler AS U ON S.urun_id=U.urun_id WHERE siparis_odenme=1"));
                while (restoran_i.rs.next()) {
                    Date zaman2 = new Date();
                    zaman2 = restoran_i.rs.getTimestamp("sipariszaman");
                    int siparis_durum = restoran_i.rs.getInt("siparis_durum");
                    int siparis_odenme = restoran_i.rs.getInt("siparis_odenme");
                    int masa_id = restoran_i.rs.getInt("Masa_id");
                    int siparisadet = restoran_i.rs.getInt("siparisadet");
                    int urun_id = restoran_i.rs.getInt("urun_id");
                    int siparis_id = restoran_i.rs.getInt("siparis_id");
                    Urun urun = new Urun(0, "", "", 0, "", 0, null);
                    urun.setUrunId(urun_id);
                    urun.setUrunAdi(restoran_i.rs.getString("urunadi"));
                    urun.setUrunAyrinti(restoran_i.rs.getString("urunayrinti"));
                    urun.setUrunUcret(restoran_i.rs.getDouble("urunucret"));
                    urun.setUrun_resim(restoran_i.rs.getString("urun_resim"));
                    Siparis siparis = new Siparis(siparis_id, urun, siparisadet, zaman2, siparis_durum, siparis_odenme, masa_id, 0, 0, 0, 0, "", "", "", "", null, 0, 0, 0);
                    siparisler.add(siparis);

                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return siparisler;
        }

        public static void SiparisTemizle(int tur) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler WHERE siparis_odenme=1"));
                while (restoran_i.rs.next()) {
                    Date zaman = restoran_i.rs.getTimestamp("sipariszaman");
                    Date simdikiZaman = new Date();
                    DateFormat yil = new SimpleDateFormat("yyyy");
                    DateFormat ay = new SimpleDateFormat("MM");
                    DateFormat gun = new SimpleDateFormat("dd");
                    switch (tur) {
                        case 0:
                            if (gun.format(simdikiZaman).equals(gun.format(zaman))) {
                                int siparis_id = restoran_i.rs.getInt("siparis_id");
                                ids.add(siparis_id);
                            }
                            break;
                        case 1:
                            if (ay.format(simdikiZaman).equals(ay.format(zaman))) {
                                int siparis_id = restoran_i.rs.getInt("siparis_id");
                                ids.add(siparis_id);
                            }
                            break;
                        case 2:
                            if (yil.format(simdikiZaman).equals(yil.format(zaman))) {
                                int siparis_id = restoran_i.rs.getInt("siparis_id");
                                ids.add(siparis_id);
                            }
                            break;
                    }
                    for (int item : ids) {
                        restoran_i.veritabani_islem(String.format("DELETE FROM siparisler WHERE siparis_id=%d", item));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public static void SiparisTemizle() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Integer> ids = new ArrayList<Integer>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM siparisler WHERE siparis_odenme=1"));
                while (restoran_i.rs.next()) {
                    int siparis_id = restoran_i.rs.getInt("siparis_id");
                    ids.add(siparis_id);
                    for (int item : ids) {
                        restoran_i.veritabani_islem(String.format("DELETE FROM siparisler WHERE siparis_id=%d", item));
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static class Log {

        public static boolean Log_Kontrol(int kullaniciId) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            boolean donus = false;
            try {
                restoran_i.veritabani_cek(String.format("SELECT COUNT(*) as sayi FROM kayitlar WHERE Log_id=%d", kullaniciId));
                while (restoran_i.rs.next()) {
                    int sonuc = restoran_i.rs.getInt("sayi");
                    if (sonuc == 1) {
                        donus = true;
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return donus;
        }

        public static void LogÜstüneEkle(int kullaniciId, Date zaman, String islem) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            boolean kontrol = Log_Kontrol(kullaniciId);
            if (kontrol) {
                restoran_i.veritabani_islem(String.format("UPDATE kayitlar SET Log_zaman=NOW(), Log_islem='%s' WHERE Log_id=%d", islem, kullaniciId));
            } else {
                restoran_i.veritabani_islem(String.format("INSERT INTO kayitlar (Log_id, Log_zaman, Log_islem) VALUES(%d, NOW(),'%s')", kullaniciId, islem));
            }
        }

        public static void LogEkle(int kullaniciId, Date zaman, String islem) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("INSERT INTO kayitlar (Log_id, Log_zaman, Log_islem) VALUES(%d, NOW(),'%s')", kullaniciId, islem));
        }

        public static ArrayList<Rapor> LogListe() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Rapor> loglar = new ArrayList<Rapor>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM kayitlar AS KA INNER JOIN kullanicilar AS KU ON KA.Log_id=KU.kullanici_id"));
                while (restoran_i.rs.next()) {
                    Kayit log = new Kayit(restoran_i.rs.getInt("Log_id"), restoran_i.rs.getTimestamp("Log_zaman"), restoran_i.rs.getString("Log_islem"));
                    Rapor rapor = new Rapor(log, restoran_i.rs.getInt("kullanici_id"), restoran_i.rs.getString("kullaniciadi"), restoran_i.rs.getString("kullanicisifre"), restoran_i.rs.getInt("kullanici_yetki"), restoran_i.rs.getString("kullanici_resim"));
                    loglar.add(rapor);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return loglar;
        }
    }

    public static class Cariler {

        public static ArrayList<Cari> CariListele(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Cari> cariler = new ArrayList<Cari>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM cari WHERE restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    int cari_id = restoran_i.rs.getInt("cari_id");
                    double cari_gelir = restoran_i.rs.getDouble("cari_gelir");
                    double cari_gider = restoran_i.rs.getDouble("cari_gider");
                    int restoran_Id = restoran_i.rs.getInt("restoran_id");
                    Cari cari = new Cari(cari_id, cari_gelir, cari_gider, restoran_Id);
                    cariler.add(cari);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return cariler;
        }

        public static ArrayList<Cari> CariListele() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Cari> cariler = new ArrayList<Cari>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM cari"));
                while (restoran_i.rs.next()) {
                    int cari_id = restoran_i.rs.getInt("cari_id");
                    double cari_gelir = restoran_i.rs.getDouble("cari_gelir");
                    double cari_gider = restoran_i.rs.getDouble("cari_gider");
                    int restoran_Id = restoran_i.rs.getInt("restoran_id");
                    Cari cari = new Cari(cari_id, cari_gelir, cari_gider, restoran_Id);
                    cariler.add(cari);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return cariler;
        }

        public static void CariEkle(double cari_gelir, double cari_gider, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("INSERT INTO cari (cari_gelir,cari_gider,restoran_id) VALUES (");
            String sorgu2 = String.format("%.2f", cari_gelir);
            sorgu2 = sorgu2.replace(',', '.');
            String virgül = String.format(",");
            String sorgu3 = String.format("%.2f", cari_gider);
            sorgu3 = sorgu3.replace(',', '.');
            String sorgu4 = String.format(",%d)", restoran_id);
            String sorgu = sorgu1 + sorgu2 + virgül + sorgu3 + sorgu4;
            restoran_i.veritabani_islem(sorgu);
        }

        public static double karHesapla(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double kar = 0, cari_gelir = 0, cari_gider = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM cari WHERE restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    cari_gelir += restoran_i.rs.getDouble("cari_gelir");
                    cari_gider += restoran_i.rs.getDouble("cari_gider");
                    kar = cari_gelir - cari_gider - (cari_gelir * 0.18);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return kar;
        }

        public static double TümRestoranlarkarHesapla() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double kar = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT SUM(cari_gelir-cari_gider) as kar FROM cari"));
                while (restoran_i.rs.next()) {
                    kar = restoran_i.rs.getDouble("kar");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return kar;
        }

        public static void Cari_Guncelle(double cari_gelir, double cari_gider, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("UPDATE cari SET cari_gelir=%.2f", cari_gelir);
            sorgu1 = sorgu1.replace(',', '.');
            String virgül = ",";
            String sorgu2 = String.format("cari_gider=%.2f", cari_gider);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format("where restoran_id=%d", restoran_id);
            String sorgu = sorgu1 + virgül + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }

        public static void GelirEkle(double miktar, int restoran_id) {
            double gelir = 0, cari_gelir = 0;
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM cari WHERE restoran_id= %d", restoran_id));
                while (restoran_i.rs.next()) {
                    cari_gelir = restoran_i.rs.getDouble("cari_gelir");
                }
                gelir = cari_gelir + miktar;
                String sorgu1 = String.format("UPDATE cari SET cari_gelir=");
                String sorgu2 = String.format("%.2f", gelir);
                sorgu2 = sorgu2.replace(',', '.');
                String sorgu3 = String.format("WHERE restoran_id= %d", restoran_id);
                String sorgu = sorgu1 + sorgu2 + sorgu3;
                restoran_i.veritabani_islem(sorgu);
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public static void GiderEkle(double miktar, int restoran_id) {
            double gelir = 0, cari_gider = 0;
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM cari WHERE restoran_id= %d", restoran_id));
                while (restoran_i.rs.next()) {
                    cari_gider = restoran_i.rs.getDouble("cari_gider");
                }
                gelir = cari_gider + miktar;
                String sorgu1 = String.format("UPDATE cari SET cari_gider=");
                String sorgu2 = String.format("%.2f", gelir);
                sorgu2 = sorgu2.replace(',', '.');
                String sorgu3 = String.format("WHERE restoran_id= %d", restoran_id);
                String sorgu = sorgu1 + sorgu2 + sorgu3;
                restoran_i.veritabani_islem(sorgu);
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public static void CariSil(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM cari WHERE restoran_id=%d", restoran_id));
        }
    }

    public static class Kapanan_Restoranlar {

        public static ArrayList<Kapanan_Restoran> TümKapananRestoranlariListele() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Kapanan_Restoran> restoranlarim = new ArrayList<Kapanan_Restoran>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM kapanan_restoranlar"));
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("kapanan_restoran_id");
                    String restoran_adi = restoran_i.rs.getString("kapanan_restoran_ad");
                    String Restoran_adres = restoran_i.rs.getString("kapanan_restoran_adres");
                    String kapanan_restoran_tel = restoran_i.rs.getString("kapanan_restoran_tel");
                    String kapanan_restoran_vd = restoran_i.rs.getString("kapanan_restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Date zaman = new Date();
                    zaman = restoran_i.rs.getTimestamp("kapanis_tarih");
                    double gelir = restoran_i.rs.getDouble("gelir");
                    double gider = restoran_i.rs.getDouble("gider");
                    Kapanan_Restoran restoranlar = new Kapanan_Restoran(restoran_id, restoran_adi, Restoran_adres, kapanan_restoran_tel, kapanan_restoran_vd, zaman, gelir, gider, ulke_id, il_id, ilce_id);
                    restoranlarim.add(restoranlar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return restoranlarim;
        }

        public static double karHesapla(int kapanan_restoran_id) {
            double kar = 0;
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM kapanan_restoranlar WHERE kapanan_restoran_id= %d", kapanan_restoran_id));
                while (restoran_i.rs.next()) {
                    double cari_gelir = restoran_i.rs.getDouble("gelir");
                    double cari_gider = restoran_i.rs.getDouble("gider");
                    kar = cari_gelir - cari_gider - cari_gelir * 0.18;
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return kar;
        }

        public static double TümRestoranlarkarHesapla() {
            double kar = 0;
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT SUM(gelir-gider) as kar FROM kapanan_restoranlar"));
                while (restoran_i.rs.next()) {
                    kar = restoran_i.rs.getDouble("kar");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return kar;
        }

        public static ArrayList<Kapanan_Restoran> KapananRestoranlariListelead(String restoran_ad) {
            ArrayList<Kapanan_Restoran> restoranlarim = new ArrayList<Kapanan_Restoran>();
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM kapanan_restoranlar where kapanan_restoran_ad='%s'", restoran_ad));
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("kapanan_restoran_id");
                    String restoran_adi = restoran_i.rs.getString("kapanan_restoran_ad");
                    String Restoran_adres = restoran_i.rs.getString("kapanan_restoran_adres");
                    String kapanan_restoran_tel = restoran_i.rs.getString("kapanan_restoran_tel");
                    String kapanan_restoran_vd = restoran_i.rs.getString("kapanan_restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Date zaman = new Date();
                    zaman = restoran_i.rs.getTimestamp("kapanis_tarih");
                    double gelir = restoran_i.rs.getDouble("gelir");
                    double gider = restoran_i.rs.getDouble("gider");
                    Kapanan_Restoran restoranlar = new Kapanan_Restoran(restoran_id, restoran_adi, Restoran_adres, kapanan_restoran_tel, kapanan_restoran_vd, zaman, gelir, gider, ulke_id, il_id, ilce_id);
                    restoranlarim.add(restoranlar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return restoranlarim;
        }

        public static void KapananRestoranSil(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM kapanan_restoranlar WHERE kapanan_restoran_id=%d", restoran_id));
        }

        public static void Restoran_Kapa(String restoran_ad, String restoran_adres, String kapanan_restoran_tel, String kapanan_restoran_vd, double gelir, double gider, int ulke_id, int il_id, int ilce_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("INSERT INTO kapanan_restoranlar (kapanan_restoran_ad,kapanan_restoran_adres,kapanan_restoran_tel,kapanan_restoran_vd,ulke_id,il_id,ilce_id,kapanis_tarih,gelir,gider) VALUES ('%s','%s','%s','%s',%d,%d,%d,NOW(),", restoran_ad, restoran_adres, kapanan_restoran_tel, kapanan_restoran_vd, ulke_id, il_id, ilce_id);
            String sorgu2 = String.format("%.2f", gelir);
            sorgu2 = sorgu2.replace(',', '.');
            String virgül = String.format(",");
            String sorgu3 = String.format("%.2f)", gider);
            sorgu3 = sorgu3.replace(',', '.');
            String sorgu = sorgu1 + sorgu2 + virgül + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }

        public static void Restoran_Ac(String restoran_ad, String restoran_adres, String restoran_tel, String restoran_vd, double gelir, double gider, int ulke_id, int il_id, int ilce_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int restoran_id = 0;
            String sorgu = String.format("INSERT INTO restoranlar (Restoran_adi,Restoran_adres,restoran_tel,restoran_vd,ulke_id,il_id,ilce_id) VALUES ('%s','%s','%s','%s',%d,%d,%d)", restoran_ad, restoran_adres, restoran_tel, restoran_vd, ulke_id, il_id, ilce_id);
            restoran_i.veritabani_islem(sorgu);
            try {

                restoran_i.veritabani_cek(String.format("SELECT * FROM restoranlar ORDER BY Restoran_id DESC LIMIT 1"));
                while (restoran_i.rs.next()) {
                    restoran_id = restoran_i.rs.getInt("Restoran_id");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            Cariler.CariEkle(gelir, gider, restoran_id);
        }
    }

    public static class Restoranlarim {

        public static ArrayList<Restoranlar> TümRestoranlariListele() {
            ArrayList<Restoranlar> restoranlarim = new ArrayList<Restoranlar>();
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM restoranlar AS R INNER JOIN cari AS C ON R.restoran_id=C.restoran_id"));
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cari = new Cari(0, 0, 0, restoran_id);
                    cari.setCari_Id(restoran_i.rs.getInt("cari_id"));
                    cari.setCari_gelir(restoran_i.rs.getDouble("cari_gelir"));
                    cari.setCari_gider(restoran_i.rs.getDouble("cari_gider"));
                    Restoranlar restoranlar = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    restoranlarim.add(restoranlar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return restoranlarim;
        }

        public static ArrayList<Restoranlar> TümRestoranlaricarisizListele() {
            ArrayList<Restoranlar> restoranlarim = new ArrayList<Restoranlar>();
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM restoranlar"));
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cari = new Cari(0, 0, 0, restoran_id);
                    Restoranlar restoranlar = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    restoranlarim.add(restoranlar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return restoranlarim;
        }

        public static ArrayList<Restoranlar> RestoranlaricarisizListele(int res_id) {
            ArrayList<Restoranlar> restoranlarim = new ArrayList<Restoranlar>();
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM restoranlar WHERE restoran_id=%d", res_id));
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cari = new Cari(0, 0, 0, restoran_id);
                    Restoranlar restoranlar = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    restoranlarim.add(restoranlar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return restoranlarim;
        }

        public static ArrayList<Restoranlar> RestoranlariListele(int restoran_Id) {
            ArrayList<Restoranlar> restoranlarim = new ArrayList<Restoranlar>();
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM restoranlar AS R INNER JOIN cari AS C ON R.restoran_id=C.restoran_id where R.restoran_id=%d", restoran_Id));
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cari = new Cari(0, 0.0, 0.0, restoran_id);
                    cari.setCari_Id(restoran_i.rs.getInt("cari_id"));
                    cari.setCari_gelir(restoran_i.rs.getDouble("cari_gelir"));
                    cari.setCari_gider(restoran_i.rs.getDouble("cari_gider"));
                    Restoranlar restoranlar = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    restoranlarim.add(restoranlar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return restoranlarim;
        }

        public static ArrayList<Restoranlar> RestoranlariListelead(String restoran_ad) {
            ArrayList<Restoranlar> restoranlarim = new ArrayList<Restoranlar>();
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM restoranlar where Restoran_adi='%s'", restoran_ad));
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");

                    Restoranlar restoranlar = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, null, ulke_id, il_id, ilce_id);
                    restoranlarim.add(restoranlar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return restoranlarim;
        }

        public static double RestoranHesaplama(int restoran_id, String durum) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double sonuc = 0;
            if (durum == "gelir") {
                try {
                    restoran_i.veritabani_cek(String.format("SELECT SUM(cari_gelir) as gelir FROM cari Where restoran_id=%d", restoran_id));
                    while (restoran_i.rs.next()) {
                        sonuc = restoran_i.rs.getDouble("gelir");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (durum == "gider") {
                try {
                    restoran_i.veritabani_cek(String.format("SELECT SUM(cari_gider) as gider FROM cari Where restoran_id=%d", restoran_id));
                    while (restoran_i.rs.next()) {
                        sonuc = restoran_i.rs.getDouble("gider");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    restoran_i.veritabani_cek(String.format("SELECT SUM(cari_gelir-cari_gider) as kar FROM cari Where restoran_id=%d", restoran_id));
                    while (restoran_i.rs.next()) {
                        sonuc = restoran_i.rs.getDouble("kar");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            return sonuc;
        }

        public static void RestoranEkle(String restoran_adi, String restoran_adres, String restoran_tel, String restoran_vd, int ulke_id, int il_id, int ilce_id) {
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_islem(String.format("INSERT INTO restoranlar (Restoran_adi,Restoran_adres,restoran_tel,restoran_vd,ulke_id,il_id,ilce_id) VALUES ('%s','%s','%s','%s',%d,%d,%d)", restoran_adi, restoran_adres, restoran_tel, restoran_vd, ulke_id, il_id, ilce_id));
                int restoran_id = 0;
                restoran_i.veritabani_cek(String.format("SELECT * FROM restoranlar ORDER BY Restoran_id DESC LIMIT 1"));
                while (restoran_i.rs.next()) {
                    restoran_id = restoran_i.rs.getInt("Restoran_id");
                }
                Cariler.CariEkle(0, 0, restoran_id);
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public static void RestoranGüncelle(String restoran_adi, String restoran_adres, String restoran_tel, String restoran_vd, int ulke_id, int il_id, int ilce_id, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE restoranlar SET Restoran_adi='%s', Restoran_adres='%s',restoran_tel='%s',restoran_vd='%s',ulke_id=%d,il_id=%d,ilce_id=%d WHERE Restoran_id=%d", restoran_adi, restoran_adres, restoran_tel, restoran_vd, ulke_id, il_id, ilce_id, restoran_id));
        }

        public static void RestoranSil(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM restoranlar WHERE Restoran_id=%d", restoran_id));
            Cariler.CariSil(restoran_id);
        }
    }

    public static class Stoklarim {

        public static ArrayList<Stok> TümRestoranlarinStokListele(int res_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Stok> stoklarim = new ArrayList<Stok>();
            try {
                if (res_id == 0) {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM stok AS S INNER JOIN restoranlar AS R ON S.restoran_id=R.restoran_id INNER JOIN malzeme AS M ON M.malzeme_id=S.malzeme_id"));
                } else {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM stok AS S INNER JOIN restoranlar AS R ON S.restoran_id=R.restoran_id INNER JOIN malzeme AS M ON M.malzeme_id=S.malzeme_id WHERE R.restoran_id=%d", res_id));
                }
                while (restoran_i.rs.next()) {
                    int stok_id = restoran_i.rs.getInt("stok_id");
                    double stok_miktar = restoran_i.rs.getDouble("stok_miktar");
                    int malzeme_Id = restoran_i.rs.getInt("malzeme_id");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    Cari cari = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    Malzeme_Kayit malzeme = new Malzeme_Kayit(malzeme_Id, malzeme_adi);
                    Stok stoklar = new Stok(stok_id, stok_miktar, malzeme_Id, restoran_id, restoran, malzeme);
                    stoklarim.add(stoklar);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return stoklarim;
        }

        public static ArrayList<Stok> TümRestoranlarinToplamStokListele(int res_id, int malzeme_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Stok> stoklarim = new ArrayList<Stok>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT *,SUM(stok_miktar) AS stok FROM stok AS S INNER JOIN restoranlar AS R ON S.restoran_id=R.restoran_id INNER JOIN malzeme AS M ON M.malzeme_id=S.malzeme_id WHERE R.restoran_id=%d AND M.malzeme_id=%d", res_id, malzeme_id));
                while (restoran_i.rs.next()) {
                    int stok_id = restoran_i.rs.getInt("stok_id");
                    double stok_miktar = restoran_i.rs.getDouble("stok");
                    int malzeme_Id = restoran_i.rs.getInt("malzeme_id");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    Cari cari = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    Malzeme_Kayit malzeme = new Malzeme_Kayit(malzeme_Id, malzeme_adi);
                    Stok stoklar = new Stok(stok_id, stok_miktar, malzeme_Id, restoran_id, restoran, malzeme);
                    stoklarim.add(stoklar);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return stoklarim;
        }

        public static ArrayList<Stok> TümRestoranlarinStokListeleAra(int res_id, String mal_adi) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Stok> stoklarim = new ArrayList<Stok>();
            try {
                if (res_id == 0) {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM stok AS S INNER JOIN restoranlar AS R ON S.restoran_id=R.restoran_id INNER JOIN malzeme AS M ON M.malzeme_id=S.malzeme_id WHERE M.malzeme_adi like '%s%s%s'", "%", mal_adi, "%"));
                } else {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM stok AS S INNER JOIN restoranlar AS R ON S.restoran_id=R.restoran_id INNER JOIN malzeme AS M ON M.malzeme_id=S.malzeme_id WHERE R.restoran_id=%d", res_id));
                }
                while (restoran_i.rs.next()) {
                    int stok_id = restoran_i.rs.getInt("stok_id");
                    double stok_miktar = restoran_i.rs.getDouble("stok_miktar");
                    int malzeme_Id = restoran_i.rs.getInt("malzeme_id");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    Cari cari = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    Malzeme_Kayit malzeme = new Malzeme_Kayit(malzeme_Id, malzeme_adi);
                    Stok stoklar = new Stok(stok_id, stok_miktar, malzeme_Id, restoran_id, restoran, malzeme);
                    stoklarim.add(stoklar);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return stoklarim;
        }

        public static ArrayList<Stok> TümRestoranlarinToplamStokListeleAra(int res_id, int malzeme_id, String res_adi) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Stok> stoklarim = new ArrayList<Stok>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT *,SUM(stok_miktar) AS stok FROM stok AS S INNER JOIN restoranlar AS R ON S.restoran_id=R.restoran_id INNER JOIN malzeme AS M ON M.malzeme_id=S.malzeme_id WHERE R.restoran_id=%d AND M.malzeme_id=%d AND R.Restoran_adi like '%s%s%s'", res_id, malzeme_id, "%", res_adi, "%"));
                while (restoran_i.rs.next()) {
                    int stok_id = restoran_i.rs.getInt("stok_id");
                    double stok_miktar = restoran_i.rs.getDouble("stok");
                    int malzeme_Id = restoran_i.rs.getInt("malzeme_id");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    Cari cari = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    Malzeme_Kayit malzeme = new Malzeme_Kayit(malzeme_Id, malzeme_adi);
                    Stok stoklar = new Stok(stok_id, stok_miktar, malzeme_Id, restoran_id, restoran, malzeme);
                    stoklarim.add(stoklar);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return stoklarim;
        }

        public static ArrayList<Stok> RestoranStokListele(int malzeme_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Stok> stoklarim = new ArrayList<Stok>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM stok AS S INNER JOIN restoranlar AS R ON S.restoran_id=R.restoran_id INNER JOIN malzeme AS M ON M.malzeme_id=S.malzeme_id where M.malzeme_id=%d", malzeme_id));
                while (restoran_i.rs.next()) {
                    int stok_id = restoran_i.rs.getInt("stok_id");
                    double stok_miktar = restoran_i.rs.getDouble("stok_miktar");
                    int malzeme_Id = restoran_i.rs.getInt("malzeme_id");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String Restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    Cari cari = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, Restoran_adres, restoran_tel, restoran_vd, cari, ulke_id, il_id, ilce_id);
                    Malzeme_Kayit malzeme = new Malzeme_Kayit(malzeme_Id, malzeme_adi);
                    Stok stoklar = new Stok(stok_id, stok_miktar, malzeme_Id, restoran_id, restoran, malzeme);
                    stoklarim.add(stoklar);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return stoklarim;
        }

        public static void Stok_Kayit(double stok_miktar, int malzeme_id, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("INSERT INTO stok (stok_miktar, malzeme_id,restoran_id) VALUES (");
            String sorgu2 = String.format("%.2f", stok_miktar);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format(",%d,%d)", malzeme_id, restoran_id);
            String sorgu = sorgu1 + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }

        public static void Stok_Degistir(double stok_miktar, int malzeme_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("UPDATE stok SET stok_miktar=");
            String sorgu2 = String.format("%.2f", stok_miktar);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format("WHERE malzeme_id=%d", malzeme_id);
            String sorgu = sorgu1 + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }

        public static void Stok_Sil(int malzeme_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM stok WHERE malzeme_id=%d", malzeme_id));
        }

        public static void Stok_Sil_Secili(int stok_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM stok WHERE stok_id=%d", stok_id));
        }

        public static void StokGüncelle(double stok_miktar, int malzeme_id, int stok_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("UPDATE stok SET stok_miktar=");
            String sorgu2 = String.format("%.2f", stok_miktar);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format(",malzeme_id=%d WHERE stok_id=%d", malzeme_id, stok_id);
            String sorgu = sorgu1 + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }

        public static void StokEkle(double eklenecek_miktar, int stok_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double miktar = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM stok where stok_id=%d", stok_id));
                while (restoran_i.rs.next()) {
                    miktar = restoran_i.rs.getDouble("stok_miktar");

                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            miktar += eklenecek_miktar;
            String sorgu1 = String.format("UPDATE stok SET stok_miktar=");
            String sorgu2 = String.format("%.2f", miktar);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format("WHERE stok_id=%d", stok_id);
            String sorgu = sorgu1 + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }

        public static void StokDus(double stok_miktar, int malzeme_id, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("INSERT INTO stok (stok_miktar, malzeme_id,restoran_id) VALUES (");
            String sorgu2 = String.format("%.2f", -stok_miktar);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format(",%d,%d)", malzeme_id, restoran_id);
            String sorgu = sorgu1 + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }
    }

    public static class Malzemeler {

        public static ArrayList<Malzeme> TümMalzemeleriListele() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Malzeme> malzemelerim = new ArrayList<Malzeme>();
            try {
                restoran_i.veritabani_cek("SELECT * FROM malzemeler AS M INNER JOIN malzeme AS S ON M.malzeme_adi_id=S.malzeme_id");
                while (restoran_i.rs.next()) {
                    int malzeme_id = restoran_i.rs.getInt("malzeme_id");
                    int malzeme_adi_id = restoran_i.rs.getInt("malzeme_adi_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    double malzeme_miktar = restoran_i.rs.getDouble("malzeme_miktar");
                    double malzeme_fiyat = restoran_i.rs.getDouble("malzeme_fiyat");
                    double malzeme_birim_fiyat = restoran_i.rs.getDouble("malzeme_birim_fiyat");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    int fatura_durum = restoran_i.rs.getInt("fatura_durum");
                    Date tarih = restoran_i.rs.getDate("tarih");
                    Malzeme_Kayit malzemelist = new Malzeme_Kayit(malzeme_adi_id, malzeme_adi);
                    malzemelist.setMalzeme_id(restoran_i.rs.getInt("malzeme_id"));
                    malzemelist.setMalzeme_adi(restoran_i.rs.getString("malzeme_adi"));
                    Malzeme malzemeler = new Malzeme(malzeme_id, malzeme_adi_id, malzeme_miktar, restoran_id, malzeme_fiyat, malzeme_birim_fiyat, fatura_durum, tarih, malzemelist);
                    malzemelerim.add(malzemeler);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return malzemelerim;
        }

        public static ArrayList<Malzeme> RestoranMalzemeleriListele(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Malzeme> malzemelerim = new ArrayList<Malzeme>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM malzemeler AS M INNER JOIN malzeme AS S ON M.malzeme_adi_id=S.malzeme_id where M.restoran_id=%d", restoran_id));
                while (restoran_i.rs.next()) {
                    int malzeme_id = restoran_i.rs.getInt("malzeme_id");
                    int malzeme_adi_id = restoran_i.rs.getInt("malzeme_adi_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    double malzeme_miktar = restoran_i.rs.getDouble("malzeme_miktar");
                    double malzeme_fiyat = restoran_i.rs.getDouble("malzeme_fiyat");
                    double malzeme_birim_fiyat = restoran_i.rs.getDouble("malzeme_birim_fiyat");
                    int restoran_Id = restoran_i.rs.getInt("restoran_id");
                    int fatura_durum = restoran_i.rs.getInt("fatura_durum");
                    Date tarih = restoran_i.rs.getDate("tarih");
                    Malzeme_Kayit malzemelist = new Malzeme_Kayit(malzeme_adi_id, malzeme_adi);
                    malzemelist.setMalzeme_id(restoran_i.rs.getInt("malzeme_id"));
                    malzemelist.setMalzeme_adi(restoran_i.rs.getString("malzeme_adi"));
                    Malzeme malzemeler = new Malzeme(malzeme_id, malzeme_adi_id, malzeme_miktar, restoran_Id, malzeme_fiyat, malzeme_birim_fiyat, fatura_durum, tarih, malzemelist);
                    malzemelerim.add(malzemeler);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return malzemelerim;
        }

        public static ArrayList<Malzeme> MalzemeListele(int malzeme_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Malzeme> malzemelerim = new ArrayList<Malzeme>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM malzemeler AS M INNER JOIN malzeme AS S ON M.malzeme_adi_id=S.malzeme_id WHERE M.malzeme_id=%d", malzeme_id));
                while (restoran_i.rs.next()) {
                    int malzeme_Id = restoran_i.rs.getInt("malzeme_id");
                    int malzeme_adi_id = restoran_i.rs.getInt("malzeme_adi_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    double malzeme_miktar = restoran_i.rs.getDouble("malzeme_miktar");
                    double malzeme_fiyat = restoran_i.rs.getDouble("malzeme_fiyat");
                    double malzeme_birim_fiyat = restoran_i.rs.getDouble("malzeme_birim_fiyat");
                    int restoran_Id = restoran_i.rs.getInt("restoran_id");
                    int fatura_durum = restoran_i.rs.getInt("fatura_durum");
                    Date tarih = restoran_i.rs.getDate("tarih");
                    Malzeme_Kayit malzemelist = new Malzeme_Kayit(malzeme_adi_id, malzeme_adi);
                    malzemelist.setMalzeme_id(restoran_i.rs.getInt("malzeme_id"));
                    malzemelist.setMalzeme_adi(restoran_i.rs.getString("malzeme_adi"));
                    Malzeme malzemeler = new Malzeme(malzeme_id, malzeme_adi_id, malzeme_miktar, restoran_Id, malzeme_fiyat, malzeme_birim_fiyat, fatura_durum, tarih, malzemelist);
                    malzemelerim.add(malzemeler);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return malzemelerim;
        }

        public static ArrayList<Malzeme> MalzemeListeleGenel() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Malzeme> malzemelerim = new ArrayList<Malzeme>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM malzemeler AS M INNER JOIN malzeme AS S ON M.malzeme_adi_id=S.malzeme_id"));
                while (restoran_i.rs.next()) {
                    int malzeme_Id = restoran_i.rs.getInt("malzeme_id");
                    int malzeme_adi_id = restoran_i.rs.getInt("malzeme_adi_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    double malzeme_miktar = restoran_i.rs.getDouble("malzeme_miktar");
                    double malzeme_fiyat = restoran_i.rs.getDouble("malzeme_fiyat");
                    double malzeme_birim_fiyat = restoran_i.rs.getDouble("malzeme_birim_fiyat");
                    int restoran_Id = restoran_i.rs.getInt("restoran_id");
                    int fatura_durum = restoran_i.rs.getInt("fatura_durum");
                    Date tarih = restoran_i.rs.getDate("tarih");
                    Malzeme_Kayit malzemelist = new Malzeme_Kayit(malzeme_adi_id, malzeme_adi);
                    malzemelist.setMalzeme_id(restoran_i.rs.getInt("malzeme_id"));
                    malzemelist.setMalzeme_adi(restoran_i.rs.getString("malzeme_adi"));
                    Malzeme malzemeler = new Malzeme(malzeme_Id, malzeme_adi_id, malzeme_miktar, restoran_Id, malzeme_fiyat, malzeme_birim_fiyat, fatura_durum, tarih, malzemelist);
                    malzemelerim.add(malzemeler);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return malzemelerim;
        }

        public static void Malzeme_Ekle(int malzeme_adi, double malzeme_miktar, int restoran_id, double malzeme_birim_fiyat) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("INSERT INTO malzemeler (malzeme_adi_id, malzeme_miktar,malzeme_birim_fiyat,malzeme_fiyat,restoran_id) VALUES (%d,", malzeme_adi);
            String sorgu2 = String.format("%.2f", malzeme_miktar);
            sorgu2 = sorgu2.replace(',', '.');
            String virgül1 = String.format(",");
            String sorgu3 = String.format("%.2f", malzeme_birim_fiyat);
            sorgu3 = sorgu3.replace(',', '.');
            String virgül2 = String.format(",");
            String sorgu4 = String.format("%.2f", malzeme_miktar * malzeme_birim_fiyat);
            sorgu4 = sorgu4.replace(',', '.');
            String sorgu5 = String.format(",%d)", restoran_id);
            String sorgu = sorgu1 + sorgu2 + virgül1 + sorgu3 + virgül2 + sorgu4 + sorgu5;
            restoran_i.veritabani_islem(sorgu);
            int malzeme_id = 0;
            try {
                String sql = String.format("SELECT * FROM malzemeler ORDER BY malzeme_id DESC LIMIT 1");
                restoran_i.veritabani_cek(sql);
                while (restoran_i.rs.next()) {
                    malzeme_id = restoran_i.rs.getInt("malzeme_id");
                }

                Stoklarim.Stok_Kayit(malzeme_miktar, malzeme_id, restoran_id);
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public static void MalzemeGüncelle(int malzeme_adi, double malzeme_miktar, double malzeme_birim_fiyat, int restoran_id, int malzeme_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("UPDATE malzemeler SET malzeme_adi_id=%d,malzeme_miktar=", malzeme_adi);
            String sorgu2 = String.format("%.2f", malzeme_miktar);
            sorgu2 = sorgu2.replace(',', '.');
            String virgül = String.format(",");
            String sorgu3 = String.format("malzeme_birim_fiyat=%.2f", malzeme_birim_fiyat);
            sorgu3 = sorgu3.replace(',', '.');
            String sorgu4 = String.format("malzeme_fiyat=%.2f", malzeme_miktar * malzeme_birim_fiyat);
            sorgu4 = sorgu4.replace(',', '.');
            String sorgu5 = String.format(",restoran_id=%d WHERE malzeme_id=%d", restoran_id, malzeme_id);
            String sorgu = sorgu1 + sorgu2 + virgül + sorgu3 + virgül + sorgu4 + sorgu5;
            restoran_i.veritabani_islem(sorgu);
            Stoklarim.Stok_Degistir(malzeme_miktar, malzeme_id);
        }

        public static void Malzemedurumdegistir(int durum, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu = String.format("UPDATE malzemeler SET fatura_durum=%d WHERE restoran_id=%d", durum, restoran_id);
            restoran_i.veritabani_islem(sorgu);
        }

        public static void Malzeme_Sil(int malzeme_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM malzemeler WHERE malzeme_id=%d", malzeme_id));
            Stoklarim.Stok_Sil(malzeme_id);
        }
    }

    public static class Malzeme_Kayitlari {

        public static ArrayList<Malzeme_Kayit> MalzemeleriListele() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Malzeme_Kayit> malzemelerim = new ArrayList<Malzeme_Kayit>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM malzeme"));
                while (restoran_i.rs.next()) {
                    int malzeme_id = restoran_i.rs.getInt("malzeme_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    Malzeme_Kayit malzemeler = new Malzeme_Kayit(malzeme_id, malzeme_adi);
                    malzemelerim.add(malzemeler);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return malzemelerim;
        }

        public static ArrayList<Malzeme_Kayit> MalzemeAra(String keyword) {
            ArrayList<Malzeme_Kayit> malzemelerim = new ArrayList<Malzeme_Kayit>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(("SELECT * FROM malzeme WHERE malzeme_adi like '%" + keyword + "%'"));
                while (restoran_i.rs.next()) {
                    int malzeme_id = restoran_i.rs.getInt("malzeme_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    Malzeme_Kayit malzemeler = new Malzeme_Kayit(malzeme_id, malzeme_adi);
                    malzemelerim.add(malzemeler);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return malzemelerim;
        }

        public static ArrayList<Malzeme_Kayit> MalzemeListelead(String malzeme_ad) {
            ArrayList<Malzeme_Kayit> malzemelerim = new ArrayList<Malzeme_Kayit>();
            try {
                Restoran_Islem restoran_i = new Restoran_Islem();
                restoran_i.veritabani_cek(String.format("SELECT * FROM malzeme where malzeme_adi='%s'", malzeme_ad));
                while (restoran_i.rs.next()) {
                    int malzeme_id = restoran_i.rs.getInt("malzeme_id");
                    String malzeme_adi = restoran_i.rs.getString("malzeme_adi");
                    Malzeme_Kayit malzemeler = new Malzeme_Kayit(malzeme_id, malzeme_adi);
                    malzemelerim.add(malzemeler);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return malzemelerim;
        }

        public static void MalzemeEkle(String malzeme_adi) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("INSERT INTO malzeme (malzeme_adi) VALUES ('%s')", malzeme_adi));
        }

        public static void MalzemeGuncelle(String malzeme_adi, int malzeme_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE malzeme SET malzeme_adi='%s' WHERE malzeme_id=%d", malzeme_adi, malzeme_id));
        }

        public static void MalzemeSil(int malzeme_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM malzeme WHERE malzeme_id=%d", malzeme_id));
        }
    }

    public static class Calisanlarim {

        public static ArrayList<Calisanlar> CalisanListele() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Calisanlar> calisanlarim = new ArrayList<Calisanlar>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id"));
                while (restoran_i.rs.next()) {
                    int calisan_id = restoran_i.rs.getInt("calisan_id");
                    String calisan_adi = restoran_i.rs.getString("calisan_adi");
                    String calisan_soyadi = restoran_i.rs.getString("calisan_soyadi");
                    double calisan_maas = restoran_i.rs.getDouble("calisan_maas");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullaniciadi = restoran_i.rs.getString("kullaniciadi");
                    String kullanicisifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    String tel = restoran_i.rs.getString("calisan_tel");
                    String mail = restoran_i.rs.getString("calisan_mail");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    Calisanlar calisan = new Calisanlar(calisan_id, calisan_adi, calisan_soyadi, calisan_maas, kullanici_id, tel, mail, restoran_id, kullanici_id, kullaniciadi, kullanicisifre, kullanici_yetki, kullanici_resim);
                    calisanlarim.add(calisan);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return calisanlarim;
        }

        public static ArrayList<Calisanlar> CalisanListele(int calisanid) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Calisanlar> calisanlarim = new ArrayList<Calisanlar>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id WHERE C.calisan_id=%d", calisanid));
                while (restoran_i.rs.next()) {
                    int calisan_id = restoran_i.rs.getInt("calisan_id");
                    String calisan_adi = restoran_i.rs.getString("calisan_adi");
                    String calisan_soyadi = restoran_i.rs.getString("calisan_soyadi");
                    double calisan_maas = restoran_i.rs.getDouble("calisan_maas");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullaniciadi = restoran_i.rs.getString("kullaniciadi");
                    String kullanicisifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    String tel = restoran_i.rs.getString("calisan_tel");
                    String mail = restoran_i.rs.getString("calisan_mail");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    Calisanlar calisan = new Calisanlar(calisan_id, calisan_adi, calisan_soyadi, calisan_maas, kullanici_id, tel, mail, restoran_id, kullanici_id, kullaniciadi, kullanicisifre, kullanici_yetki, kullanici_resim);
                    calisanlarim.add(calisan);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return calisanlarim;
        }

        public static ArrayList<Calisanlar> CalisanListeleRestoran(int r_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Calisanlar> calisanlarim = new ArrayList<Calisanlar>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id INNER JOIN restoranlar AS R ON R.restoran_id=C.restoran_id WHERE R.restoran_id=%d", r_id));
                while (restoran_i.rs.next()) {
                    int calisan_id = restoran_i.rs.getInt("calisan_id");
                    String calisan_adi = restoran_i.rs.getString("calisan_adi");
                    String calisan_soyadi = restoran_i.rs.getString("calisan_soyadi");
                    double calisan_maas = restoran_i.rs.getDouble("calisan_maas");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullaniciadi = restoran_i.rs.getString("kullaniciadi");
                    String kullanicisifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    String tel = restoran_i.rs.getString("calisan_tel");
                    String mail = restoran_i.rs.getString("calisan_mail");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    Calisanlar calisan = new Calisanlar(calisan_id, calisan_adi, calisan_soyadi, calisan_maas, kullanici_id, tel, mail, restoran_id, kullanici_id, kullaniciadi, kullanicisifre, kullanici_yetki, kullanici_resim);
                    calisanlarim.add(calisan);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return calisanlarim;
        }

        public static ArrayList<Calisanlar> CalisanAra(String keyword, int r_id) {
            ArrayList<Calisanlar> calisanlarim = new ArrayList<Calisanlar>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                if (r_id > 0) {
                    restoran_i.veritabani_cek("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id INNER JOIN restoranlar AS R ON R.restoran_id=C.restoran_id WHERE R.restoran_id=" + r_id + " AND K.kullaniciadi like '%" + keyword + "%'");
                } else {
                    restoran_i.veritabani_cek("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id WHERE K.kullaniciadi like '%" + keyword + "%'");
                }
                while (restoran_i.rs.next()) {
                    int calisan_id = restoran_i.rs.getInt("calisan_id");
                    String calisan_adi = restoran_i.rs.getString("calisan_adi");
                    String calisan_soyadi = restoran_i.rs.getString("calisan_soyadi");
                    double calisan_maas = restoran_i.rs.getDouble("calisan_maas");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullaniciadi = restoran_i.rs.getString("kullaniciadi");
                    String kullanicisifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    String tel = restoran_i.rs.getString("calisan_tel");
                    String mail = restoran_i.rs.getString("calisan_mail");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    Calisanlar calisan = new Calisanlar(calisan_id, calisan_adi, calisan_soyadi, calisan_maas, kullanici_id, tel, mail, restoran_id, kullanici_id, kullaniciadi, kullanicisifre, kullanici_yetki, kullanici_resim);
                    calisanlarim.add(calisan);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return calisanlarim;
        }

        public static ArrayList<Calisanlar> CalisanMaasAra(String keyword, int r_id) {
            ArrayList<Calisanlar> calisanlarim = new ArrayList<Calisanlar>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                if (r_id > 0) {
                    restoran_i.veritabani_cek("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id INNER JOIN restoranlar AS R ON R.restoran_id=C.restoran_id WHERE R.restoran_id=" + r_id + " AND C.calisan_adi like '%" + keyword + "%'");
                } else {
                    restoran_i.veritabani_cek("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id WHERE C.calisan_adi like '%" + keyword + "%'");
                }
                while (restoran_i.rs.next()) {
                    int calisan_id = restoran_i.rs.getInt("calisan_id");
                    String calisan_adi = restoran_i.rs.getString("calisan_adi");
                    String calisan_soyadi = restoran_i.rs.getString("calisan_soyadi");
                    double calisan_maas = restoran_i.rs.getDouble("calisan_maas");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullaniciadi = restoran_i.rs.getString("kullaniciadi");
                    String kullanicisifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    String tel = restoran_i.rs.getString("calisan_tel");
                    String mail = restoran_i.rs.getString("calisan_mail");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    Calisanlar calisan = new Calisanlar(calisan_id, calisan_adi, calisan_soyadi, calisan_maas, kullanici_id, tel, mail, restoran_id, kullanici_id, kullaniciadi, kullanicisifre, kullanici_yetki, kullanici_resim);
                    calisanlarim.add(calisan);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return calisanlarim;
        }

        public static ArrayList<Calisanlar> CalisanYetkiyegoreListele(String kullanici_yetkisi) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Calisanlar> calisanlarim = new ArrayList<Calisanlar>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id where K.kullanici_yetki='%s'", kullanici_yetkisi));
                while (restoran_i.rs.next()) {
                    int calisan_id = restoran_i.rs.getInt("calisan_id");
                    String calisan_adi = restoran_i.rs.getString("calisan_adi");
                    String calisan_soyadi = restoran_i.rs.getString("calisan_soyadi");
                    double calisan_maas = restoran_i.rs.getDouble("calisan_maas");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String kullaniciadi = restoran_i.rs.getString("kullaniciadi");
                    String kullanicisifre = restoran_i.rs.getString("kullanicisifre");
                    int kullanici_yetki = restoran_i.rs.getInt("kullanici_yetki");
                    String kullanici_resim = restoran_i.rs.getString("kullanici_resim");
                    String tel = restoran_i.rs.getString("calisan_tel");
                    String mail = restoran_i.rs.getString("calisan_mail");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    Calisanlar calisan = new Calisanlar(calisan_id, calisan_adi, calisan_soyadi, calisan_maas, kullanici_id, tel, mail, restoran_id, kullanici_id, kullaniciadi, kullanicisifre, kullanici_yetki, kullanici_resim);
                    calisanlarim.add(calisan);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return calisanlarim;
        }

        public static void CalisanEkle(String calisan_adi, String calisan_soyad, double maas, int kullanici_id, String tel, String mail, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("INSERT INTO calisanlar (calisan_adi,calisan_soyadi,calisan_tel,calisan_mail,calisan_maas,kullanici_id,restoran_id) VALUES ('%s','%s','%s','%s',", calisan_adi, calisan_soyad, tel, mail);
            String sorgu2 = String.format("%.2f", maas);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format(",%d,%d)", kullanici_id, restoran_id);
            String sorgu = sorgu1 + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }

        public static void CalisanGüncelle(String calisan_adi, String calisan_soyad, double maas, int kullanici_id, String tel, String mail, int restoran_id, int calisan_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu1 = String.format("UPDATE calisanlar SET calisan_adi='%s',calisan_soyadi='%s',calisan_maas=", calisan_adi, calisan_soyad);
            String sorgu2 = String.format("%.2f", maas);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format(",kullanici_id=%d,calisan_tel='%s',calisan_mail='%s',restoran_id=%d WHERE calisan_id=%d", kullanici_id, tel, mail, restoran_id, calisan_id);
            String sorgu = sorgu1 + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);
        }

        public static void CalisanSil(int calisan_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int kullanici_id = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM calisanlar AS C INNER JOIN kullanicilar AS K ON C.kullanici_id=K.kullanici_id WHERE C.calisan_id=%d", calisan_id));
                while (restoran_i.rs.next()) {
                    kullanici_id = restoran_i.rs.getInt("kullanici_id");
                }
                Kullanicilar.KullaniciSil(kullanici_id);

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            restoran_i.veritabani_islem(String.format("DELETE FROM calisanlar WHERE calisan_id=%d", calisan_id));
        }

        public static void calisanMaasOde(int calisan_id) {
            int restoran_id = 0;
            double maas = 0;
            ArrayList<Calisanlar> calisan = RestoranDB.Calisanlarim.CalisanListele(calisan_id);
            for (Calisanlar item : calisan) {
                maas = item.getCalisan_maas();
                restoran_id = item.getRestoran_id();
            }
            Cariler.CariEkle(0, maas, restoran_id);
        }

        public static void Zamyap(double zam_miktar, int calisan_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            double miktar = 0;
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM calisanlar where calisan_id=%d", calisan_id));
                while (restoran_i.rs.next()) {
                    miktar = restoran_i.rs.getDouble("calisan_maas");

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            miktar += (miktar * zam_miktar / 100);
            String sorgu1 = String.format("UPDATE calisanlar SET calisan_maas=");
            String sorgu2 = String.format("%.2f", miktar);
            sorgu2 = sorgu2.replace(',', '.');
            String sorgu3 = String.format("WHERE calisan_id=%d", calisan_id);
            String sorgu = sorgu1 + sorgu2 + sorgu3;
            restoran_i.veritabani_islem(sorgu);

        }
    }

    public static class Ulkeler {

        public static ArrayList<Ulke> Ulkeleri_Listele() {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Ulke> ulkelerim = new ArrayList<Ulke>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM countries"));
                while (restoran_i.rs.next()) {
                    int ulke_id = restoran_i.rs.getInt("id");
                    String ulkeadi = restoran_i.rs.getString("name");
                    String kod = restoran_i.rs.getString("sortname");
                    String phonekod = restoran_i.rs.getString("phonecode");
                    Ulke ulkeler = new Ulke(ulkeadi, ulke_id, kod, phonekod);
                    ulkelerim.add(ulkeler);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return ulkelerim;
        }

        public static ArrayList<Ulke> UlkeAra(String keyword) {
            ArrayList<Ulke> ulkelerim = new ArrayList<Ulke>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(("SELECT * FROM countries WHERE name like '%" + keyword + "%'"));
                while (restoran_i.rs.next()) {
                    int ulke_id = restoran_i.rs.getInt("id");
                    String ulkeadi = restoran_i.rs.getString("name");
                    String kod = restoran_i.rs.getString("sortname");
                    String phonekod = restoran_i.rs.getString("phonecode");
                    Ulke ulkeler = new Ulke(ulkeadi, ulke_id, kod, phonekod);
                    ulkelerim.add(ulkeler);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return ulkelerim;
        }

        public static String Ulke_Listele(int id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String ulkeadi = "";
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM countries Where id=%d", id));
                while (restoran_i.rs.next()) {
                    ulkeadi = restoran_i.rs.getString("name");

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return ulkeadi;
        }

        public static ArrayList<Ulke> UlkeListelead(String ulke_adi) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Ulke> ulkeler = new ArrayList<Ulke>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM countries where name='%s'", ulke_adi));
                while (restoran_i.rs.next()) {
                    int ulke_id = restoran_i.rs.getInt("id");
                    String ulkeg = restoran_i.rs.getString("name");
                    String kod = restoran_i.rs.getString("sortname");
                    String phonekod = restoran_i.rs.getString("phonecode");
                    Ulke ulke = new Ulke(ulkeg, ulke_id, kod, phonekod);
                    ulkeler.add(ulke);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return ulkeler;
        }

        public static void Ulke_ekle(String ulke_adi, String kod, String phonekod) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("INSERT INTO countries (name,sortname,phonecode) VALUES ('%s','%s','%s')", ulke_adi, kod, phonekod));
        }

        public static void Ulke_Duzenle(String ulke_adi, String kod, String phonekod, int country_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE countries SET name='%s',sortname='%s',phonecode='%s' WHERE id=%d", ulke_adi, kod, phonekod, country_id));
        }

        public static void Ulke_Sil(int country_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM countries WHERE id=%d", country_id));
        }
    }

    public static class Iller {

        public static ArrayList<Il> IlleriListele(int country_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Il> illerim = new ArrayList<Il>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM states where country_id=%d", country_id));
                while (restoran_i.rs.next()) {
                    int il_id = restoran_i.rs.getInt("id");
                    String iladi = restoran_i.rs.getString("name");
                    int country_Id = restoran_i.rs.getInt("country_id");
                    Il iller = new Il(il_id, iladi, country_Id);
                    illerim.add(iller);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return illerim;
        }

        public static ArrayList<Il> IlAra(String keyword, int country_id) {
            ArrayList<Il> illerim = new ArrayList<Il>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(("SELECT * FROM states WHERE country_id=" + country_id + " and name like '%" + keyword + "%'"));
                while (restoran_i.rs.next()) {
                    int il_id = restoran_i.rs.getInt("id");
                    String iladi = restoran_i.rs.getString("name");
                    int country_Id = restoran_i.rs.getInt("country_id");
                    Il iller = new Il(il_id, iladi, country_Id);
                    illerim.add(iller);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return illerim;
        }

        public static String Il_Listele(int id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String iladi = "";
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM states Where id=%d", id));
                while (restoran_i.rs.next()) {
                    iladi = restoran_i.rs.getString("name");

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return iladi;
        }

        public static ArrayList<Il> IlListelead(String il_adi) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Il> illerim = new ArrayList<Il>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM states where name='%s'", il_adi));
                while (restoran_i.rs.next()) {
                    int il_id = restoran_i.rs.getInt("id");
                    String iladi = restoran_i.rs.getString("name");
                    int country_Id = restoran_i.rs.getInt("country_id");
                    Il iller = new Il(il_id, iladi, country_Id);
                    illerim.add(iller);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return illerim;
        }

        public static void Il_ekle(String il_adi, int country_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("INSERT INTO states (name,country_id) VALUES ('%s',%d)", il_adi, country_id));
        }

        public static void Il_Duzenle(String il_adi, int states_id, int country_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE states SET name='%s', country_id=%d WHERE id=%d", il_adi, country_id, states_id));
        }

        public static void Il_Sil(int states_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM states WHERE id=%d", states_id));
        }
    }

    public static class Ilceler {

        public static ArrayList<Ilce> IlceListele(int state_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Ilce> ilcelerim = new ArrayList<Ilce>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM cities where state_id=%d", state_id));
                while (restoran_i.rs.next()) {
                    int ilce_id = restoran_i.rs.getInt("id");
                    String ilceadi = restoran_i.rs.getString("name");
                    int state_Id = restoran_i.rs.getInt("state_id");
                    Ilce ilceler = new Ilce(ilce_id, ilceadi, state_Id);
                    ilcelerim.add(ilceler);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return ilcelerim;
        }

        public static ArrayList<Ilce> IlceAra(String keyword, int state_id) {
            ArrayList<Ilce> ilcelerim = new ArrayList<Ilce>();
            Restoran_Islem restoran_i = new Restoran_Islem();
            try {
                restoran_i.veritabani_cek(("SELECT * FROM cities WHERE state_id=" + state_id + " and name like '%" + keyword + "%'"));
                while (restoran_i.rs.next()) {
                    int ilce_id = restoran_i.rs.getInt("id");
                    String ilceadi = restoran_i.rs.getString("name");
                    int state_Id = restoran_i.rs.getInt("state_id");
                    Ilce ilceler = new Ilce(ilce_id, ilceadi, state_Id);
                    ilcelerim.add(ilceler);
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return ilcelerim;
        }

        public static ArrayList<Ilce> IlceListelead(String ilce_adi) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Ilce> ilcelerim = new ArrayList<Ilce>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM cities where name='%s'", ilce_adi));
                while (restoran_i.rs.next()) {
                    int ilce_id = restoran_i.rs.getInt("id");
                    String ilceadi = restoran_i.rs.getString("name");
                    int state_Id = restoran_i.rs.getInt("state_id");
                    Ilce ilceler = new Ilce(ilce_id, ilceadi, state_Id);
                    ilcelerim.add(ilceler);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return ilcelerim;
        }

        public static String Ilce_Listele(int id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String ilceadi = "";
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM cities Where id=%d", id));
                while (restoran_i.rs.next()) {
                    ilceadi = restoran_i.rs.getString("name");

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return ilceadi;
        }

        public static void Ilce_ekle(String ilce_adi, int state_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("INSERT INTO cities (name,state_id) VALUES ('%s',%d)", ilce_adi, state_id));
        }

        public static void Ilce_Duzenle(String ilce_adi, int cities_id, int state_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("UPDATE cities SET name='%s', state_id=%d WHERE id=%d", ilce_adi, state_id, cities_id));
        }

        public static void Ilce_Sil(int cities_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM cities WHERE id=%d", cities_id));
        }
    }

    public static class Faturalar {

        public static ArrayList<Fatura> Tedarikci(String firma_adi, String firma_adresi, String firma_tel, String firma_vd) {
            ArrayList<Fatura> faturalarim = new ArrayList<Fatura>();
            Restoranlar restoran = new Restoranlar(0, "", "", "", "", null, 0, 0, 0);
            Fatura fatura = new Fatura(0, "", null, firma_adi, firma_adresi, firma_tel, firma_vd, 0, 0, restoran);
            faturalarim.add(fatura);
            return faturalarim;
        }

        public static ArrayList<Fatura> FaturaListele(int res_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Fatura> faturalarim = new ArrayList<Fatura>();
            try {
                if (res_id == 0) {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM fatura AS F INNER JOIN restoranlar AS R ON F.restoran_id=R.restoran_id"));
                } else {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM fatura AS F INNER JOIN restoranlar AS R ON F.restoran_id=R.restoran_id WHERE R.restoran_id=%d", res_id));
                }
                while (restoran_i.rs.next()) {
                    int fatura_id = restoran_i.rs.getInt("fatura_id");
                    String fatura_no = restoran_i.rs.getString("fatura_no");
                    Date fatura_tarih = restoran_i.rs.getDate("fatura_tarih");
                    String firma_adi = restoran_i.rs.getString("firma_adi");
                    String firma_adresi = restoran_i.rs.getString("firma_adresi");
                    String firma_tel = restoran_i.rs.getString("firma_tel");
                    String firma_vd = restoran_i.rs.getString("firma_vd");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Fatura fatura = new Fatura(fatura_id, fatura_no, fatura_tarih, firma_adi, firma_adresi, firma_tel, firma_vd, restoran_id, kullanici_id, restoran);
                    faturalarim.add(fatura);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return faturalarim;
        }

        public static ArrayList<Fatura> FaturaAra(int res_id, String Fatura_no) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Fatura> faturalarim = new ArrayList<Fatura>();
            try {
                if (res_id == 0) {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM fatura AS F INNER JOIN restoranlar AS R ON F.restoran_id=R.restoran_id WHERE F.fatura_no like '%s%s%s'", "%", Fatura_no, "%"));
                } else {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM fatura AS F INNER JOIN restoranlar AS R ON F.restoran_id=R.restoran_id WHERE R.restoran_id=%d AND F.fatura_no like '%s%s%s'", res_id, "%", Fatura_no, "%"));
                }
                while (restoran_i.rs.next()) {
                    int fatura_id = restoran_i.rs.getInt("fatura_id");
                    String fatura_no = restoran_i.rs.getString("fatura_no");
                    Date fatura_tarih = restoran_i.rs.getDate("fatura_tarih");
                    String firma_adi = restoran_i.rs.getString("firma_adi");
                    String firma_adresi = restoran_i.rs.getString("firma_adresi");
                    String firma_tel = restoran_i.rs.getString("firma_tel");
                    String firma_vd = restoran_i.rs.getString("firma_vd");
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    int kullanici_id = restoran_i.rs.getInt("kullanici_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Fatura fatura = new Fatura(fatura_id, fatura_no, fatura_tarih, firma_adi, firma_adresi, firma_tel, firma_vd, restoran_id, kullanici_id, restoran);
                    faturalarim.add(fatura);

                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return faturalarim;
        }

        public static Date KayitTarihi(String fatura_no) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            Date tarih = null;
            restoran_i.veritabani_cek(String.format("SELECT fatura_tarih FROM fatura WHERE fatura_no='%s'", fatura_no));
            try {
                while (restoran_i.rs.next()) {
                    tarih = restoran_i.rs.getDate("fatura_tarih");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return tarih;
        }

        public static int KayitSayisi(int restoran_id) {
            int adet = 0;
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_cek(String.format("SELECT COUNT(fatura_id) as adet FROM fatura"));
            try {
                while (restoran_i.rs.next()) {
                    adet = restoran_i.rs.getInt("adet");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return adet;
        }

        public static String SonKayit(int restoran_id) {
            String fatura_no = null;
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_cek(String.format("SELECT * FROM fatura ORDER BY fatura_no DESC LIMIT 1"));
            try {
                while (restoran_i.rs.next()) {
                    fatura_no = restoran_i.rs.getString("fatura_no");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            fatura_no = fatura_no.substring(1, fatura_no.length());
            long Fatura_no = Long.parseLong(fatura_no);
            Fatura_no++;
            fatura_no = "F" + String.valueOf(Fatura_no);
            return fatura_no;
        }

        public static String FaturaNoUret(int n) {

            // rastgele harf seç
            String metin = "0123456789";

            //  StringBuffer olutşurun 
            StringBuilder sb = new StringBuilder(n);

            for (int i = 0; i < n; i++) {

                // metinden rastgele bir tane değer oku
                int index
                        = (int) (metin.length()
                        * Math.random());

                // sb değişkenine harfleri ekle
                sb.append(metin
                        .charAt(index));
            }

            return "F" + sb.toString();
        }

        public static void Fatura_ekle(String fatura_no, String firma_adi, String firma_adresi, String firma_tel, String firma_vd, int restoran_id, int kullanici_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("INSERT INTO fatura (fatura_no,firma_adi,firma_adresi,firma_tel,firma_vd,restoran_id,kullanici_id) VALUES ('%s','%s','%s','%s','%s',%d,%d)", fatura_no, firma_adi, firma_adresi, firma_tel, firma_vd, restoran_id, kullanici_id));
        }

        public static void Fatura_Sil(String fatura_no) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM fatura WHERE fatura_no='%s'", fatura_no));
        }
    }

    public static class Menuler {

        public static ArrayList<Menu> MenuListele(int res_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Menu> menulerim = new ArrayList<Menu>();
            try {
                if (res_id == 0) {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM menu AS M INNER JOIN restoranlar AS R ON M.restoran_id=R.restoran_id INNER JOIN urunler AS U ON U.urun_id=M.urun_id"));
                } else {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM menu AS M INNER JOIN restoranlar AS R ON M.restoran_id=R.restoran_id INNER JOIN urunler AS U ON U.urun_id=M.urun_id WHERE R.restoran_id=%d", res_id));
                }
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    int urunid = restoran_i.rs.getInt("urun_id");
                    String urunadi = restoran_i.rs.getString("urunadi");
                    String urunayrinti = restoran_i.rs.getString("urunayrinti");
                    int urunucret = restoran_i.rs.getInt("urunucret");
                    String urun_resim = restoran_i.rs.getString("urun_resim");
                    int menu_id = restoran_i.rs.getInt("menu_id");
                    String menu_adi = restoran_i.rs.getString("menu_adi");
                    String urun_tipi = restoran_i.rs.getString("urun_tipi");
                    Date menu_tarih = restoran_i.rs.getDate("menu_tarih");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Urun urun = new Urun(urunid, urunadi, urunayrinti, urunucret, urun_resim, restoran_id, restoran);
                    Menu menu = new Menu(menu_id, menu_adi, menu_tarih, urun_tipi, urunid, restoran_id, urun, restoran);
                    menulerim.add(menu);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return menulerim;
        }

        public static ArrayList<Menu> MenuListele(String urun_tip, int res_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Menu> menulerim = new ArrayList<Menu>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM menu AS M INNER JOIN restoranlar AS R ON M.restoran_id=R.restoran_id INNER JOIN urunler AS U ON U.urun_id=M.urun_id WHERE R.restoran_id=%d AND M.urun_tipi='%s'", res_id, urun_tip));
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    int urunid = restoran_i.rs.getInt("urun_id");
                    String urunadi = restoran_i.rs.getString("urunadi");
                    String urunayrinti = restoran_i.rs.getString("urunayrinti");
                    int urunucret = restoran_i.rs.getInt("urunucret");
                    String urun_resim = restoran_i.rs.getString("urun_resim");
                    int menu_id = restoran_i.rs.getInt("menu_id");
                    String menu_adi = restoran_i.rs.getString("menu_adi");
                    String urun_tipi = restoran_i.rs.getString("urun_tipi");
                    Date menu_tarih = restoran_i.rs.getDate("menu_tarih");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Urun urun = new Urun(urunid, urunadi, urunayrinti, urunucret, urun_resim, restoran_id, restoran);
                    Menu menu = new Menu(menu_id, menu_adi, menu_tarih, urun_tipi, urunid, restoran_id, urun, restoran);
                    menulerim.add(menu);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return menulerim;
        }

        public static ArrayList<Menu> MenuListeleAra(int res_id, String urunaranan) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Menu> menulerim = new ArrayList<Menu>();
            try {
                if (res_id == 0) {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM menu AS M INNER JOIN restoranlar AS R ON M.restoran_id=R.restoran_id INNER JOIN urunler AS U ON U.urun_id=M.urun_id WHERE U.urunadi like '%s%s%s'", "%", urunaranan, "%"));
                } else {
                    restoran_i.veritabani_cek(String.format("SELECT * FROM menu AS M INNER JOIN restoranlar AS R ON M.restoran_id=R.restoran_id INNER JOIN urunler AS U ON U.urun_id=M.urun_id WHERE R.restoran_id=%d AND U.urunadi like '%s%s%s'", res_id, "%", urunaranan, "%"));
                }
                while (restoran_i.rs.next()) {
                    int restoran_id = restoran_i.rs.getInt("restoran_id");
                    String restoran_adi = restoran_i.rs.getString("Restoran_adi");
                    String restoran_adres = restoran_i.rs.getString("Restoran_adres");
                    String restoran_tel = restoran_i.rs.getString("restoran_tel");
                    String restoran_vd = restoran_i.rs.getString("restoran_vd");
                    int ulke_id = restoran_i.rs.getInt("ulke_id");
                    int il_id = restoran_i.rs.getInt("il_id");
                    int ilce_id = restoran_i.rs.getInt("ilce_id");
                    int urunid = restoran_i.rs.getInt("urun_id");
                    String urunadi = restoran_i.rs.getString("urunadi");
                    String urunayrinti = restoran_i.rs.getString("urunayrinti");
                    int urunucret = restoran_i.rs.getInt("urunucret");
                    String urun_resim = restoran_i.rs.getString("urun_resim");
                    int menu_id = restoran_i.rs.getInt("menu_id");
                    String menu_adi = restoran_i.rs.getString("menu_adi");
                    String urun_tipi = restoran_i.rs.getString("urun_tipi");
                    Date menu_tarih = restoran_i.rs.getDate("menu_tarih");
                    Cari cariler = new Cari(0, 0, 0, 0);
                    Restoranlar restoran = new Restoranlar(restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cariler, ulke_id, il_id, ilce_id);
                    Urun urun = new Urun(urunid, urunadi, urunayrinti, urunucret, urun_resim, restoran_id, restoran);
                    Menu menu = new Menu(menu_id, menu_adi, menu_tarih, urun_tipi, urunid, restoran_id, urun, restoran);
                    menulerim.add(menu);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return menulerim;
        }

        public static int MenuAd(int res_id) {
            int adet = 0;
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_cek(String.format("SELECT *,COUNT(M.menu_id) as adet FROM menu AS M INNER JOIN restoranlar AS R ON M.restoran_id=R.restoran_id INNER JOIN urunler AS U ON U.urun_id=M.urun_id WHERE R.restoran_id=%d", res_id));
            try {
                while (restoran_i.rs.next()) {
                    adet = restoran_i.rs.getInt("adet");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return adet;
        }

        public static void MenuyeUrunEkle(String menu_adi, String urun_tipi, int urun_id, int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("INSERT INTO menu (menu_adi,urun_tipi,urun_id,restoran_id) VALUES ('%s','%s',%d,%d)", menu_adi, urun_tipi, urun_id, restoran_id));
        }

        public static void MenuyeUrunGuncelle(String menu_adi, String urun_tipi, int urun_id, int restoran_id, int menu_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            String sorgu = String.format("UPDATE menu SET menu_adi='%s',urun_tipi='%s',urun_id=%d,restoran_id=%d WHERE menu_id=%d", menu_adi, urun_tipi, urun_id, restoran_id, menu_id);
            restoran_i.veritabani_islem(sorgu);
        }

        public static void MenuUrunSil(int menu_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM menu WHERE menu_id=%d", menu_id));
        }

        public static void MenuSil(int restoran_id) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM menu WHERE restoran_id=%d", restoran_id));
        }
    }

    public static class BeniHatirla {

        public static void bilgiDoldur(String mac, String kullanici_adi, String sifre) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int adet = 0;
            restoran_i.veritabani_cek(String.format("SELECT *,COUNT(benihatirla_id) as adet FROM benihatirla WHERE MAC='%s'", mac));
            try {
                while (restoran_i.rs.next()) {
                    adet = restoran_i.rs.getInt("adet");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (adet == 0) {
                restoran_i.veritabani_islem(String.format("INSERT INTO benihatirla (kullanici_adi,kullanici_sifre,MAC) VALUES ('%s','%s','%s')", kullanici_adi, sifre, mac));
            }
        }

        public static int Adetbul(String mac) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            int adet = 0;
            restoran_i.veritabani_cek(String.format("SELECT *,COUNT(MAC) as adet FROM benihatirla WHERE MAC='%s'", mac));
            try {
                while (restoran_i.rs.next()) {
                    adet = restoran_i.rs.getInt("adet");
                }
            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return adet;
        }

        public static void bilgiSil(String mac) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            restoran_i.veritabani_islem(String.format("DELETE FROM benihatirla WHERE MAC='%s'", mac));
        }

        public static ArrayList<Hatirla> BilgiListele(String Mac) {
            Restoran_Islem restoran_i = new Restoran_Islem();
            ArrayList<Hatirla> giris_bilgiler = new ArrayList<Hatirla>();
            try {
                restoran_i.veritabani_cek(String.format("SELECT * FROM benihatirla WHERE MAC='%s'", Mac));
                while (restoran_i.rs.next()) {
                    int benihatirla_id = restoran_i.rs.getInt("benihatirla_id");
                    String kullanici_adi = restoran_i.rs.getString("kullanici_adi");
                    String kullanici_sifre = restoran_i.rs.getString("kullanici_sifre");
                    String MAC = restoran_i.rs.getString("MAC");
                    Hatirla bilgi = new Hatirla(benihatirla_id, kullanici_adi, kullanici_sifre, MAC);
                    giris_bilgiler.add(bilgi);
                }

            } catch (SQLException ex) {
                Logger.getLogger(RestoranDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return giris_bilgiler;
        }
    }
}
