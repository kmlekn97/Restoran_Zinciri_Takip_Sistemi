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
public class Calisanlar extends Kullanici {

    private int calisan_id;
    private String calisan_adi;
    private String calisan_soyadi;
    private double calisan_maas;
    private int kullanici_id;
    private String tel;
    private String mail;
    private int restoran_id;

    public Calisanlar(int calisan_id, String calisan_adi, String calisan_soyadi, double calisan_maas, int kullanici_id, String tel, String mail, int restoran_id, int kullaniciId, String kullaniciAdi, String sifre, int yetki, String kullanici_resim) {
        super(kullaniciId, kullaniciAdi, sifre, yetki, kullanici_resim);
        this.calisan_id = calisan_id;
        this.calisan_adi = calisan_adi;
        this.calisan_soyadi = calisan_soyadi;
        this.calisan_maas = calisan_maas;
        this.kullanici_id = kullanici_id;
        this.tel = tel;
        this.mail = mail;
        this.restoran_id = restoran_id;
    }

    public int getCalisan_id() {
        return calisan_id;
    }

    public String getCalisan_adi() {
        return calisan_adi;
    }

    public String getCalisan_soyadi() {
        return calisan_soyadi;
    }

    public double getCalisan_maas() {
        return calisan_maas;
    }

    public int getKullanici_id() {
        return kullanici_id;
    }

    public String getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public int getRestoran_id() {
        return restoran_id;
    }

    public void setCalisan_id(int calisan_id) {
        this.calisan_id = calisan_id;
    }

    public void setCalisan_adi(String calisan_adi) {
        this.calisan_adi = calisan_adi;
    }

    public void setCalisan_soyadi(String calisan_soyadi) {
        this.calisan_soyadi = calisan_soyadi;
    }

    public void setCalisan_maas(double calisan_maas) {
        this.calisan_maas = calisan_maas;
    }

    public void setKullanici_id(int kullanici_id) {
        this.kullanici_id = kullanici_id;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setRestoran_id(int restoran_id) {
        this.restoran_id = restoran_id;
    }

   
    
}
