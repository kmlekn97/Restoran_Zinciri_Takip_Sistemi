/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

/**
 *
 * @author Kemal
 */
public class Restoranlar {
    private int restoran_id;
    private String restoran_adi;
    private String restoran_adres;
    private String restoran_tel;
    private String restoran_vd;
    private Cari cari_kayit;
    private int ulke_id;
    private int il_id;
    private int ilce_id;

    public Restoranlar(int restoran_id, String restoran_adi, String restoran_adres, String restoran_tel, String restoran_vd, Cari cari_kayit, int ulke_id, int il_id, int ilce_id) {
        this.restoran_id = restoran_id;
        this.restoran_adi = restoran_adi;
        this.restoran_adres = restoran_adres;
        this.restoran_tel = restoran_tel;
        this.restoran_vd = restoran_vd;
        this.cari_kayit = cari_kayit;
        this.ulke_id = ulke_id;
        this.il_id = il_id;
        this.ilce_id = ilce_id;
    }

    public int getRestoran_id() {
        return restoran_id;
    }

    public String getRestoran_adi() {
        return restoran_adi;
    }

    public String getRestoran_adres() {
        return restoran_adres;
    }

    public String getRestoran_tel() {
        return restoran_tel;
    }

    public String getRestoran_vd() {
        return restoran_vd;
    }

    public Cari getCari_kayit() {
        return cari_kayit;
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

    public void setRestoran_id(int restoran_id) {
        this.restoran_id = restoran_id;
    }

    public void setRestoran_adi(String restoran_adi) {
        this.restoran_adi = restoran_adi;
    }

    public void setRestoran_adres(String restoran_adres) {
        this.restoran_adres = restoran_adres;
    }

    public void setRestoran_tel(String restoran_tel) {
        this.restoran_tel = restoran_tel;
    }

    public void setRestoran_vd(String restoran_vd) {
        this.restoran_vd = restoran_vd;
    }

    public void setCari_kayit(Cari cari_kayit) {
        this.cari_kayit = cari_kayit;
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
