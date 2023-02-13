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
public class Kullanici {
    
    private int kullaniciId;
    private String kullaniciAdi;
    private String sifre;
    private int yetki;
    private String kullanici_resim;

    public Kullanici(int kullaniciId, String kullaniciAdi, String sifre, int yetki, String kullanici_resim) {
        this.kullaniciId = kullaniciId;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.yetki = yetki;
        this.kullanici_resim = kullanici_resim;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public int getYetki() {
        return yetki;
    }

    public String getKullanici_resim() {
        return kullanici_resim;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public void setYetki(int yetki) {
        this.yetki = yetki;
    }

    public void setKullanici_resim(String kullanici_resim) {
        this.kullanici_resim = kullanici_resim;
    }


    
}
