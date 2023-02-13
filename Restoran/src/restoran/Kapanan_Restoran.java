/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

import java.util.Date;

/**
 *
 * @author Kemal
 */
public class Kapanan_Restoran {

    private int kapanan_restoran_idBirinci;
    private String kapanan_restoran_ad;
    private String kapanan_restoran_adres;
    private String kapanan_restoran_tel;
    private String kapanan_restoran_vd;
    private Date kapanis_tarih;
    private double gelir;
    private double gider;
    private int ulke_id;
    private int il_id;
    private int ilce_id;

    public Kapanan_Restoran(int kapanan_restoran_idBirinci, String kapanan_restoran_ad, String kapanan_restoran_adres, String kapanan_restoran_tel, String kapanan_restoran_vd, Date kapanis_tarih, double gelir, double gider, int ulke_id, int il_id, int ilce_id) {
        this.kapanan_restoran_idBirinci = kapanan_restoran_idBirinci;
        this.kapanan_restoran_ad = kapanan_restoran_ad;
        this.kapanan_restoran_adres = kapanan_restoran_adres;
        this.kapanan_restoran_tel = kapanan_restoran_tel;
        this.kapanan_restoran_vd = kapanan_restoran_vd;
        this.kapanis_tarih = kapanis_tarih;
        this.gelir = gelir;
        this.gider = gider;
        this.ulke_id = ulke_id;
        this.il_id = il_id;
        this.ilce_id = ilce_id;
    }

    public int getKapanan_restoran_idBirinci() {
        return kapanan_restoran_idBirinci;
    }

    public String getKapanan_restoran_ad() {
        return kapanan_restoran_ad;
    }

    public String getKapanan_restoran_adres() {
        return kapanan_restoran_adres;
    }

    public String getKapanan_restoran_tel() {
        return kapanan_restoran_tel;
    }

    public String getKapanan_restoran_vd() {
        return kapanan_restoran_vd;
    }

    public Date getKapanis_tarih() {
        return kapanis_tarih;
    }

    public double getGelir() {
        return gelir;
    }

    public double getGider() {
        return gider;
    }

    public int getUlke_id() {
        return ulke_id;
    }

    public int getIl_id() {
        return il_id;
    }

    public int getIlce_id() {
        return ilce_id;
    }

    public void setKapanan_restoran_idBirinci(int kapanan_restoran_idBirinci) {
        this.kapanan_restoran_idBirinci = kapanan_restoran_idBirinci;
    }

    public void setKapanan_restoran_ad(String kapanan_restoran_ad) {
        this.kapanan_restoran_ad = kapanan_restoran_ad;
    }

    public void setKapanan_restoran_adres(String kapanan_restoran_adres) {
        this.kapanan_restoran_adres = kapanan_restoran_adres;
    }

    public void setKapanan_restoran_tel(String kapanan_restoran_tel) {
        this.kapanan_restoran_tel = kapanan_restoran_tel;
    }

    public void setKapanan_restoran_vd(String kapanan_restoran_vd) {
        this.kapanan_restoran_vd = kapanan_restoran_vd;
    }

    public void setKapanis_tarih(Date kapanis_tarih) {
        this.kapanis_tarih = kapanis_tarih;
    }

    public void setGelir(double gelir) {
        this.gelir = gelir;
    }

    public void setGider(double gider) {
        this.gider = gider;
    }

    public void setUlke_id(int ulke_id) {
        this.ulke_id = ulke_id;
    }

    public void setIl_id(int il_id) {
        this.il_id = il_id;
    }

    public void setIlce_id(int ilce_id) {
        this.ilce_id = ilce_id;
    }



    

}
